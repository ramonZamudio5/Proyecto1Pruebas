/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dominio.Cancion;
import dominio.Playlist;
import java.util.List;
import java.util.Optional;
import persistencia.RepositorioMusical;

/**
 *
 * @author ramonsebastianzamudioayala
 */
public class GestorMusica {
    private RepositorioMusical repositorio;

    public GestorMusica() {
        this.repositorio = new RepositorioMusical();
    }

    public void crearNuevaPlaylist(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (repositorio.buscarPlaylist(nombre).isPresent()) {
            throw new IllegalArgumentException("Ya existe una playlist con ese nombre");
        }
        repositorio.guardarPlaylist(new Playlist(nombre));
    }

    public void agregarCancionAPlaylist(String nombrePlaylist, int indiceCancionBiblioteca) {
        Optional<Playlist> playlistOpt = repositorio.buscarPlaylist(nombrePlaylist);
        List<Cancion> biblioteca = repositorio.obtenerTodasLasCanciones();

        if (playlistOpt.isPresent()) {
            Cancion cancion = biblioteca.get(indiceCancionBiblioteca);
            playlistOpt.get().agregarCancion(cancion);
        } else {
            throw new IllegalArgumentException("Playlist no encontrada o índice de canción inválido");
        }
    }

    public List<Cancion> obtenerBiblioteca() {
        return repositorio.obtenerTodasLasCanciones();
    }

    public List<Playlist> obtenerPlaylists() {
        return repositorio.obtenerTodasLasPlaylists();
    }

    // Lógica de simulación de reproducción
    public void reproducirPlaylist(String nombrePlaylist) {
        Optional<Playlist> playlistOpt = repositorio.buscarPlaylist(nombrePlaylist);
        
        if (playlistOpt.isPresent()) {
            Playlist p = playlistOpt.get();
            System.out.println("\n--- Reproduciendo: " + p.getNombre() + " ---");
            if (p.getCanciones().isEmpty()) {
                System.out.println("(La lista está vacía)");
            } else {
                for (Cancion c : p.getCanciones()) {
                    System.out.println("Reproduciendo: " + c.getTitulo() + " - " + c.getArtista() + "...");
                    try { Thread.sleep(1000); } catch (InterruptedException e) {} // Simula tiempo
                }
            }
            System.out.println("--- Fin de la reproducción ---\n");
        } else {
            System.out.println("Error: Playlist no encontrada.");
        }
    }
}
