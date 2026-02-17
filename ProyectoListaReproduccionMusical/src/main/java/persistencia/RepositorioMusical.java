/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dominio.Cancion;
import dominio.Playlist;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ramonsebastianzamudioayala
 */
public class RepositorioMusical {
   // "Base de datos" en memoria
    private List<Playlist> playlists = new ArrayList<>();
    private List<Cancion> bibliotecaGeneral = new ArrayList<>();

    public RepositorioMusical() {
        // Precargamos algunas canciones populares para probar
        bibliotecaGeneral.add(new Cancion("Givenchy", "Duki", 185));
        bibliotecaGeneral.add(new Cancion("Mónaco", "Lagos", 210));
        bibliotecaGeneral.add(new Cancion("LADY GAGA", "Peso Pluma", 205));
        bibliotecaGeneral.add(new Cancion("Hotel California", "Eagles", 390));
    }

    public List<Cancion> obtenerTodasLasCanciones() {
        return bibliotecaGeneral;
    }

    public void guardarPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public Optional<Playlist> buscarPlaylist(String nombre) {
        return playlists.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }

    public List<Playlist> obtenerTodasLasPlaylists() {
        return playlists;
    }
}
