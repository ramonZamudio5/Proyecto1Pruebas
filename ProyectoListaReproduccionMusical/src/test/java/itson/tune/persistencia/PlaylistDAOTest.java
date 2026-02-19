/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package itson.tune.persistencia;

import itson.tune.persistencia.PlaylistDAO;
import itson.tune.dominio.Playlist;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @payde
 */
public class PlaylistDAOTest {
    
    private PlaylistDAO playlistDAO;
    
    public PlaylistDAOTest() {
    }
    
    
    @BeforeEach
    public void setUp() {
        playlistDAO = new PlaylistDAO();
    }
    
    @Test
    @DisplayName("Guardar y recuperar playlist por ID")
    void guardarEncontrarPorId() {
        Playlist p = new Playlist("id-1", "Test");
        playlistDAO.guardar(p);

        Optional<Playlist> found = playlistDAO.encontrarPorId("id-1");
        assertTrue(found.isPresent());
        assertEquals("Test", found.get().getNombre());
    }

    @Test
    @DisplayName("findById con ID inexistente retorna empty")
    void encontrarPorId_noExiste_returnsEmpty() {
        assertTrue(playlistDAO.encontrarPorId("nope").isEmpty());
    }

    @Test
    @DisplayName("findAll retorna todas las playlists guardadas")
    void encontrarTodos_returnTodos() {
        playlistDAO.guardar(new Playlist("1", "A"));
        playlistDAO.guardar(new Playlist("2", "B"));
        List<Playlist> all = playlistDAO.encontrarTodos();
        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("deleteById elimina la playlist existente")
    void eliminarPorId_existe_returnTrue() {
        playlistDAO.guardar(new Playlist("x", "Eliminar"));
        assertTrue(playlistDAO.eliminarPorId("x"));
        assertTrue(playlistDAO.encontrarPorId("x").isEmpty());
    }

    @Test
    @DisplayName("deleteById con ID inexistente retorna false")
    void eliminarPorId_notExiste_returnFalse() {
        assertFalse(playlistDAO.eliminarPorId("ghost"));
    }

    @Test
    @DisplayName("existsByName es insensible a mayúsculas")
    void existePorNombre() {
        playlistDAO.guardar(new Playlist("1","Rock Clásico"));
        assertTrue(playlistDAO.existePorNombre("rock clásico"));
        assertTrue(playlistDAO.existePorNombre("ROCK CLÁSICO"));
        assertFalse(playlistDAO.existePorNombre("Pop"));
    }

    @Test
    @DisplayName("Guardar playlist nula lanza excepción")
    void guardar_null_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> playlistDAO.guardar(null));
    }
    
}
