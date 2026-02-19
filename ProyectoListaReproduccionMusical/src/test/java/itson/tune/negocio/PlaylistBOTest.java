/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package itson.tune.negocio;

import itson.tune.dominio.Cancion;
import itson.tune.dominio.Playlist;
import itson.tune.persistencia.PlaylistDAO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author payde
 */
public class PlaylistBOTest {
    
    private PlaylistBO playlistBO;
    private File archivo;

    @BeforeEach
    void setUp() throws IOException {
       playlistBO = new PlaylistBO(new PlaylistDAO());
       archivo = File.createTempFile("cancion_prueba", ".mp3");
       archivo.deleteOnExit();
    }

    @AfterEach
    void tearDown() {
        if (archivo!= null) archivo.delete();
    }

    @Test
    @DisplayName("TC-F-01: Crear playlist con nombre válido")
    void crearPlaylist_validarNombre_returnPlaylist() {
        Playlist p = playlistBO.crearPlaylist("Mis favoritos");

        assertNotNull(p);
        assertEquals("Mis favoritos", p.getNombre());
        assertNotNull(p.getId());
        assertFalse(p.getId().isBlank());
        assertEquals(1, playlistBO.obtenerPlaylists().size());
    }

    @Test
    @DisplayName("TC-F-01b: Crear playlist con nombre duplicado lanza excepción")
    void crearPlaylist_nombreDuplicado_throwsException() {
        playlistBO.crearPlaylist("Rock Clásico");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> playlistBO.crearPlaylist("Rock Clásico"));

        assertTrue(ex.getMessage().toLowerCase().contains("ya existe"));
    }

    @Test
    @DisplayName("TC-F-01c: Nombre vacío lanza excepción")
    void crearPlaylist_nombreVacio_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> playlistBO.crearPlaylist("   "));
    }

    @Test
    @DisplayName("TC-F-02: Agregar canción válida a playlist")
    void agregarCancion_archivoValido_CancionAgregada() {
        Playlist p    = playlistBO.crearPlaylist("Playlist Test");
        Cancion cancion = playlistBO.agregarCancionAPlaylist(p.getId(), "Mi Canción", "Artista", "indio", archivo);

        assertNotNull(cancion);
        assertEquals("Mi Canción", cancion.getTitulo());
        assertEquals("Artista",    cancion.getArtista());

        Playlist updated = playlistBO.encontrarPlaylistPorId(p.getId()).orElseThrow();
        assertEquals(1, updated.tamaño());
    }

    @Test
    @DisplayName("TC-F-02b: Agregar canción con archivo nulo lanza excepción")
    void agregarCancion_nullArchivo_throwsException() {
        Playlist p = playlistBO.crearPlaylist("P1");

        assertThrows(IllegalArgumentException.class,
                () -> playlistBO.agregarCancionAPlaylist(p.getId(), "Título", "Artista", "norteño", null));
    }

    @Test
    @DisplayName("TC-NF-01: Archivo con extensión no soportada lanza excepción")
    void agregarCancion_unsupportedExtension_throwsException() throws IOException {
        File txtFile = File.createTempFile("fake", ".txt");
        txtFile.deleteOnExit();

        Playlist p = playlistBO.crearPlaylist("P2");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> playlistBO.agregarCancionAPlaylist(p.getId(), "Fake", "Nadie", "uwu", txtFile));

        assertTrue(ex.getMessage().toLowerCase().contains("formato"));
        txtFile.delete();
    }

    @Test
    @DisplayName("TC-NF-02: Archivo que no existe en disco lanza excepción")
    void agregarCancion_archivoNoExiste_throwsException() {
        File ghost = new File("/tmp/does_not_exist_xkcd.wav");
        Playlist p = playlistBO.crearPlaylist("P3");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> playlistBO.agregarCancionAPlaylist(p.getId(), "Ghost", "Nadie", "rock", ghost));

        assertTrue(ex.getMessage().toLowerCase().contains("no existe"));
    }


    @Test
    @DisplayName("TC-F-05: Eliminar canción → referencia borrada, archivo intacto")
    void eliminarCancion_cancionExiste_eliminarDePlaylistArchivoIntacto() {
        Playlist p = playlistBO.crearPlaylist("Eliminar Test");
        Cancion cancion = playlistBO.agregarCancionAPlaylist(p.getId(), "Canción A", "X", "hola", archivo);

        boolean removed = playlistBO.eliminarCancionPlaylist(p.getId(), cancion.getId());

        assertTrue(removed);
        Playlist updated = playlistBO.encontrarPlaylistPorId(p.getId()).orElseThrow();
        assertEquals(0, updated.tamaño());
  
        assertTrue(archivo.exists(), "El archivo físico NO debe borrarse");
    }

    @Test
    @DisplayName("TC-F-05b: Eliminar canción que no existe devuelve false")
    void aliminarCncion_noExiste_returnFalse() {
        Playlist p = playlistBO.crearPlaylist("P4");
        boolean result = playlistBO.eliminarCancionPlaylist(p.getId(), "id-inexistente");
        assertFalse(result);
    }

    @Test
    @DisplayName("TC-F-06: Eliminar playlist existente")
    void eliminarPlaylist_existe_eliminaExitosamente() {
        Playlist p = playlistBO.crearPlaylist("Borrar esto");
        assertTrue(playlistBO.eliminarPlaylist(p.getId()));    
        assertEquals(0, playlistBO.obtenerPlaylists().size());
    }

    @Test
    @DisplayName("TC-F-06b: Eliminar playlist inexistente devuelve false")
    void eliminarPlaylist_noExiste_returnFalse() {
        assertFalse(playlistBO.eliminarPlaylist("id-fantasma"));
    }

    @Test
    @DisplayName("TC-F-07: Renombrar playlist con nombre válido")
    void renombrarPlaylist_nombreValido_renombrado() {
        Playlist p = playlistBO.crearPlaylist("Nombre Original");
        playlistBO.renombrarPlaylist(p.getId(), "Nombre Nuevo");

        Playlist updated = playlistBO.encontrarPlaylistPorId(p.getId()).orElseThrow();
        assertEquals("Nombre Nuevo", updated.getNombre());
    }
    
}
