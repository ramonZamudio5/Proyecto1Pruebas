/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DominioUs;

import java.io.File;
import java.util.Objects;

/**
 *
 * @author angel
 */
public class Cancion {

    private final String id;
    private String titulo;
    private String artista;
    private String genero;
    private final File archivo;
    private int duracionSegundos;

    public Cancion(String id, String titulo, String artista, String genero, File archivo) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID no puede ser nulo o vacío.");
        }

        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede ser nulo o vacío.");
        }

        if (archivo == null) {
            throw new IllegalArgumentException("El archivo no puede ser nulo.");
        }

        this.id = id;
        this.titulo = titulo;
        this.artista = (artista != null && !artista.isBlank()) ? artista : "Desconocido";
        this.genero = genero;
        this.archivo = archivo;
        this.duracionSegundos = 0;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public String getGenero() {
        return genero;
    }

    public File getArchivo() {
        return archivo;
    }

    public int getDuracionSegundos() {
        return duracionSegundos;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        this.titulo = titulo;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDuracionSegundos(int duracionSegundos) {
        if (duracionSegundos < 0) {
            throw new IllegalArgumentException("La duración no puede ser negativa.");
        }
        this.duracionSegundos = duracionSegundos;
    }

    public String getDuracionFormateada() {
        int minutos = duracionSegundos / 60;
        int segundos = duracionSegundos % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cancion)) {
            return false;
        }
        Cancion cancion = (Cancion) o;
        return Objects.equals(id, cancion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return titulo + " - " + artista;
    }
}
