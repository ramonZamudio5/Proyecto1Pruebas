/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.tune.persistencia;

import itson.tune.dominio.Playlist;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @payde
 */
public class PlaylistDAO implements IPlaylistDAO{
     private final Map<String, Playlist> store = new LinkedHashMap<>();
     
    
    @Override
    public void guardar(Playlist playlist) {
        if (playlist == null) throw new IllegalArgumentException("Playlist no puede ser nula");
        store.put(playlist.getId(), playlist);
    }

    @Override
    public Optional<Playlist> encontrarPorId(String id) {
        return Optional.ofNullable(store.get(id));

    }

    @Override
    public List<Playlist> encontrarTodos() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean eliminarPorId(String id) {
        return store.remove(id) != null;
    }

    @Override
    public boolean existePorNombre(String nombre) {
        if (nombre == null) return false; 
        return store.values().stream()
                .anyMatch(p -> p.getNombre().equalsIgnoreCase(nombre));
        
    }
    
}
