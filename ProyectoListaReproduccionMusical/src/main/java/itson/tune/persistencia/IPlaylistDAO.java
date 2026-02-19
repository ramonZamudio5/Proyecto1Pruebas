/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.tune.persistencia;

import itson.tune.dominio.Playlist;
import java.util.List;
import java.util.Optional;

/**
 *
 * @payde
 */
public interface IPlaylistDAO {

    public void guardar(Playlist playlist);

    Optional<Playlist> encontrarPorId(String id);
   
    List<Playlist> encontrarTodos();
    
    public boolean eliminarPorId(String id);
 
    public boolean existePorNombre(String nombre);
}

