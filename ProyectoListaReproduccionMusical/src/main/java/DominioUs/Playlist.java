/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DominioUs;

import java.util.List;

/**
 *
 * @author angel
 */
public class Playlist {

    private String nombre;
    private List<Cancion> canciones;

    public Playlist(String nombre, List<Cancion> canciones) {
        this.nombre = nombre;
        this.canciones = canciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    @Override
    public String toString() {
        return "Playlist{" + "nombre=" + nombre + ", canciones=" + canciones + '}';
    }
    
    
}
