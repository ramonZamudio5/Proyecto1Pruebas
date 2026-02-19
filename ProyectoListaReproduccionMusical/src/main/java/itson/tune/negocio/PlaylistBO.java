/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.tune.negocio;

import itson.tune.dominio.Cancion;
import itson.tune.dominio.Playlist;
import itson.tune.persistencia.IPlaylistDAO;
import itson.tune.util.GeneradorId;
import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 *
 * @payde
 */
public class PlaylistBO {
    
    private final IPlaylistDAO playlistDAO;

    
    public PlaylistBO(IPlaylistDAO playlistDAO) {
        if (playlistDAO == null) throw new IllegalArgumentException("Repository no puede ser nulo.");
        this.playlistDAO = playlistDAO;
    }

    public Playlist crearPlaylist(String nombre) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre de la playlist no puede estar vacío.");
        if (playlistDAO.existePorNombre(nombre))
            throw new IllegalArgumentException("Ya existe una playlist con el nombre: " + nombre);

        Playlist playlist = new Playlist(GeneradorId.generate(), nombre.trim());
        playlistDAO.guardar(playlist);
        return playlist;
    }

    public Playlist renombrarPlaylist(String playlistId, String nuevoNombre) {
        Playlist playlist = obtenerPlaylistOExcepcion(playlistId);
        if (playlistDAO.existePorNombre(nuevoNombre) && !playlist.getNombre().equalsIgnoreCase(nuevoNombre))
            throw new IllegalArgumentException("Ya existe una playlist con el nombre: " + nuevoNombre);
        playlist.setNombre(nuevoNombre.trim());
        playlistDAO.guardar(playlist);
        return playlist;
    }

    public boolean eliminarPlaylist(String playlistId) {
        return playlistDAO.eliminarPorId(playlistId);
    }

    public List<Playlist> obtenerPlaylists() {
        return playlistDAO.encontrarTodos();
    }

    public Optional<Playlist> encontrarPlaylistPorId(String id) {
        return playlistDAO.encontrarPorId(id);
    }

    public Cancion agregarCancionAPlaylist(String id, String titulo, String artista, String genero, File archivo) {
       validarAudio(archivo);
        Playlist playlist = obtenerPlaylistOExcepcion(id);

        Cancion cancion = new Cancion(GeneradorId.generate(), titulo, artista, genero, archivo);
        
        boolean added = playlist.agregarCancion(cancion);
        if (!added)
            throw new IllegalStateException("La canción ya existe en la playlist.");

        playlistDAO.guardar(playlist);
        return cancion;
    }


    public boolean eliminarCancionPlaylist(String playlistId, String songId) {
        Playlist playlist = obtenerPlaylistOExcepcion(playlistId);
        boolean removed = playlist.eliminarCancion(songId);
        if (removed) playlistDAO.guardar(playlist);
        return removed;
    }

    private Playlist obtenerPlaylistOExcepcion(String playlistId) {
        return playlistDAO.encontrarPorId(playlistId)
                .orElseThrow(() -> new NoSuchElementException("Playlist no encontrada: " + playlistId));
    }

    private void validarAudio(File archivo) {
        if (archivo == null)
            throw new IllegalArgumentException("El archivo de audio no puede ser nulo.");
        if (!archivo.exists())
            throw new IllegalArgumentException("El archivo no existe: " + archivo.getAbsolutePath());
        if (!archivo.isFile())
            throw new IllegalArgumentException("La ruta no apunta a un archivo: " + archivo.getAbsolutePath());
        String name = archivo.getName().toLowerCase();
        if (!name.endsWith(".wav") && !name.endsWith(".mp3") && !name.endsWith(".aiff"))
            throw new IllegalArgumentException("Formato de audio no soportado. Usa .wav, .mp3 o .aiff");
    }
}
