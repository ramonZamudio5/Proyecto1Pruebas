/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramonsebastianzamudioayala
 */
public class Playlist {
    private String nombre;
    private List<Cancion> canciones;

    public Playlist(String nombre) {
        this.nombre = nombre;
        this.canciones = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public List<Cancion> getCanciones() { return canciones; }

    public void agregarCancion(Cancion cancion) {
        this.canciones.add(cancion);
    }

    public void eliminarCancion(int indice) {
        if (indice >= 0 && indice < canciones.size()) {
            this.canciones.remove(indice);
        }
    }

    @Override
    public String toString() {
        return "Playlist{" + "nombre=" + nombre + ", canciones=" + canciones + '}';
    }
    
}
