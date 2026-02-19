/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DominioUs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author angel
 */

public class Playlist {

    private final String id;
    private String nombre;
    private final List<Cancion> canciones;

    public Playlist(String id, String nombre) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID no puede ser nulo o vacío.");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío.");
        }

        this.id = id;
        this.nombre = nombre;
        this.canciones = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    /**
     * Agrega una canción a la playlist.
     *
     * @param cancion canción a agregar
     * @return true si se agregó, false si ya existía
     */
    public boolean agregarCancion(Cancion cancion) {
        if (cancion == null) {
            throw new IllegalArgumentException("La canción no puede ser nula.");
        }

        if (canciones.contains(cancion)) {
            return false;
        }

        canciones.add(cancion);
        return true;
    }

    /**
     * Elimina una canción por su ID. Solo elimina la referencia.
     *
     * @param idCancion ID de la canción
     * @return true si se eliminó, false si no existía
     */
    public boolean eliminarCancion(String idCancion) {
        return canciones.removeIf(c -> c.getId().equals(idCancion));
    }

    /**
     * Busca una canción por su ID.
     */
    public Optional<Cancion> obtenerCancionPorId(String idCancion) {
        return canciones.stream()
                .filter(c -> c.getId().equals(idCancion))
                .findFirst();
    }

    /**
     * Retorna una vista inmutable de las canciones.
     */
    public List<Cancion> getCanciones() {
        return Collections.unmodifiableList(canciones);
    }

    public int tamaño() {
        return canciones.size();
    }

    public boolean estaVacia() {
        return canciones.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Playlist)) {
            return false;
        }
        Playlist playlist = (Playlist) o;
        return Objects.equals(id, playlist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return nombre + " (" + canciones.size() + " canciones)";
    }
}
