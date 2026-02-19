/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DominioUs;

/**
 *
 * @author angel
 */
public class Cancion {

    private String titulo;
    private String duracion;
    private String genero;
    private String artista;

    public Cancion(String titulo, String duracion, String genero, String artista) {
        this.titulo = titulo;
        this.duracion = duracion;
        this.genero = genero;
        this.artista = artista;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    @Override
    public String toString() {
        return "Cancion{" + "titulo=" + titulo + ", duracion=" + duracion + ", genero=" + genero + ", artista=" + artista + '}';
    }

}
