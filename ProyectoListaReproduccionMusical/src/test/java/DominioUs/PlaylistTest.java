/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DominioUs;

import java.io.File;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author angel
 */
public class PlaylistTest {

    private Playlist playlist;
    private Cancion cancion;

    @BeforeEach
    public void setUp() {
        playlist = new Playlist("1", "Favoritas");
        cancion = new Cancion("10", "Titulo", "Artista", "Pop", new File("archivo.mp3"));
    }

    @Test
    public void testGetId() {
        assertEquals("1", playlist.getId());
    }

    @Test
    public void testGetNombre() {
        assertEquals("Favoritas", playlist.getNombre());
    }

    @Test
    public void testSetNombre() {
        playlist.setNombre("Rock");
        assertEquals("Rock", playlist.getNombre());
    }

    @Test
    public void testAgregarCancion() {
        boolean resultado = playlist.agregarCancion(cancion);
        assertTrue(resultado);
        assertEquals(1, playlist.tamaño());
    }

    @Test
    public void testAgregarCancionDuplicada() {
        playlist.agregarCancion(cancion);
        boolean resultado = playlist.agregarCancion(cancion);
        assertFalse(resultado);
        assertEquals(1, playlist.tamaño());
    }

    @Test
    public void testEliminarCancion() {
        playlist.agregarCancion(cancion);
        boolean eliminado = playlist.eliminarCancion("10");
        assertTrue(eliminado);
        assertTrue(playlist.estaVacia());
    }

    @Test
    public void testObtenerCancionPorId() {
        playlist.agregarCancion(cancion);
        Optional<Cancion> resultado = playlist.obtenerCancionPorId("10");
        assertTrue(resultado.isPresent());
        assertEquals("Titulo", resultado.get().getTitulo());
    }

    @Test
    public void testGetCanciones() {
        playlist.agregarCancion(cancion);
        List<Cancion> canciones = playlist.getCanciones();
        assertEquals(1, canciones.size());
    }

    @Test
    public void testTamaño() {
        assertEquals(0, playlist.tamaño());
        playlist.agregarCancion(cancion);
        assertEquals(1, playlist.tamaño());
    }

    @Test
    public void testEstaVacia() {
        assertTrue(playlist.estaVacia());
        playlist.agregarCancion(cancion);
        assertFalse(playlist.estaVacia());
    }

    @Test
    public void testEquals() {
        Playlist otra = new Playlist("1", "Otra");
        assertEquals(playlist, otra);
    }

    @Test
    public void testHashCode() {
        Playlist otra = new Playlist("1", "Otra");
        assertEquals(playlist.hashCode(), otra.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("Favoritas (0 canciones)", playlist.toString());
    }

}
