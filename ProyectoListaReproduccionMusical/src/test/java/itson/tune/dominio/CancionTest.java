/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package itson.tune.dominio;

import itson.tune.dominio.Cancion;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

/**
 *
 * @author angel
 */
public class CancionTest {

    private Cancion cancion;
    private File archivo;

    @BeforeEach
    public void setUp() {
        archivo = new File("cancion.mp3");
        cancion = new Cancion("1", "Titulo", "Artista", "Pop", archivo);
    }

    @Test
    public void testGetId() {
        assertEquals("1", cancion.getId());
    }

    @Test
    public void testGetTitulo() {
        assertEquals("Titulo", cancion.getTitulo());
    }

    @Test
    public void testGetArtista() {
        assertEquals("Artista", cancion.getArtista());
    }

    @Test
    public void testGetGenero() {
        assertEquals("Pop", cancion.getGenero());
    }

    @Test
    public void testGetArchivo() {
        assertEquals(archivo, cancion.getArchivo());
    }

    @Test
    public void testGetDuracionSegundos() {
        assertEquals(0, cancion.getDuracionSegundos());
    }

    @Test
    public void testSetTitulo() {
        cancion.setTitulo("NuevoTitulo");
        assertEquals("NuevoTitulo", cancion.getTitulo());
    }

    @Test
    public void testSetArtista() {
        cancion.setArtista("NuevoArtista");
        assertEquals("NuevoArtista", cancion.getArtista());
    }

    @Test
    public void testSetGenero() {
        cancion.setGenero("Rock");
        assertEquals("Rock", cancion.getGenero());
    }

    @Test
    public void testSetDuracionSegundos() {
        cancion.setDuracionSegundos(125);
        assertEquals(125, cancion.getDuracionSegundos());
    }

    @Test
    public void testGetDuracionFormateada() {
        cancion.setDuracionSegundos(125); // 2 minutos 5 segundos
        assertEquals("02:05", cancion.getDuracionFormateada());
    }

    @Test
    public void testEquals() {
        Cancion otra = new Cancion("1", "OtroTitulo", "OtroArtista", "Rock", archivo);
        assertEquals(cancion, otra);
    }

    @Test
    public void testHashCode() {
        Cancion otra = new Cancion("1", "OtroTitulo", "OtroArtista", "Rock", archivo);
        assertEquals(cancion.hashCode(), otra.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("Titulo - Artista", cancion.toString());
    }
}
