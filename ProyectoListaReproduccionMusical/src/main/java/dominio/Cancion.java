/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author ramonsebastianzamudioayala
 */
public class Cancion {

    private String titulo;
    private String genero;
    private String artista;
    private int duracionSegundos;

    public Cancion(String titulo, String genero, String artista, int duracionSegundos) {
        this.titulo = titulo;
        this.genero = genero;
        this.artista = artista;
        this.duracionSegundos = duracionSegundos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public int getDuracionSegundos() {
        return duracionSegundos;
    }

    public void setDuracionSegundos(int duracionSegundos) {
        this.duracionSegundos = duracionSegundos;
    }

    @Override
    public String toString() {
        return "Cancion{" + "titulo=" + titulo + ", genero=" + genero + ", artista=" + artista + ", duracionSegundos=" + duracionSegundos + '}';
    }


}
