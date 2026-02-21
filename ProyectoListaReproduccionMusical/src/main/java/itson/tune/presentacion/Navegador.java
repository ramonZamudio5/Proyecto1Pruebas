package itson.tune.presentacion;

import itson.tune.dominio.Cancion;
import itson.tune.dominio.Playlist;
import itson.tune.negocio.PlaylistBO;
import itson.tune.persistencia.PlaylistDAO;

/**
 * Controlador central de navegación.
 *
 * Las pantallas NO se conocen entre sí. Cada una recibe una referencia a este
 * Navegador y le dice qué acción ocurrió. El Navegador decide qué pantalla
 * abrir a continuación.
 */
public class Navegador {

    private final PlaylistBO playlistBO;

    // Referencias a las pantallas activas (solo una visible a la vez)
    private FmrInicio pantallaInicio;
    private FmrSeleccionarPlayList pantallaSeleccionar;
    private FmrPlaylist pantallaPlaylist;
    private FmrReproducirCancion pantallaReproductor;

    public Navegador() {
        this.playlistBO = new PlaylistBO(new PlaylistDAO());
    }

    /**
     * Arranca la aplicación mostrando la pantalla de inicio. Llamado únicamente
     * desde Main.
     */
    public void iniciar() {
        mostrarInicio();
    }

    // ─── Métodos de navegación  ───────────────────
    /**
     * Pantalla 1: Inicio
     */
    public void irAInicio() {
        cerrarTodo();
        mostrarInicio();
    }

    /**
     * Pantalla 2: Galería de playlists
     */
    public void irASeleccionarPlaylist() {
        cerrarTodo();
        pantallaSeleccionar = new FmrSeleccionarPlayList(playlistBO, this);
        pantallaSeleccionar.setVisible(true);
    }

    /**
     * Pantalla 3: Detalle de una playlist
     */
    public void irAPlaylist(Playlist playlist) {
        cerrarTodo();
        pantallaPlaylist = new FmrPlaylist(playlistBO, playlist, this);
        pantallaPlaylist.setVisible(true);
    }

    /**
     * Pantalla 4: Reproductor
     */
    public void irAReproductor(Playlist playlist, Cancion cancionInicial) {
        cerrarTodo();
        pantallaReproductor = new FmrReproducirCancion(playlistBO, playlist, cancionInicial, this);
        pantallaReproductor.setVisible(true);
    }

    // ─── Acceso al servicio (para que las pantallas no importen PlaylistBO) ───
    public PlaylistBO getPlaylistBO() {
        return playlistBO;
    }

    // ─── Helpers privados ─────────────────────────────────────────────────────
    private void mostrarInicio() {
        pantallaInicio = new FmrInicio(this);
        pantallaInicio.setVisible(true);
    }

    /**
     * Cierra y libera todas las pantallas abiertas.
     */
    private void cerrarTodo() {
        if (pantallaInicio != null) {
            pantallaInicio.dispose();
            pantallaInicio = null;
        }
        if (pantallaSeleccionar != null) {
            pantallaSeleccionar.dispose();
            pantallaSeleccionar = null;
        }
        if (pantallaPlaylist != null) {
            pantallaPlaylist.dispose();
            pantallaPlaylist = null;
        }
        if (pantallaReproductor != null) {
            pantallaReproductor.dispose();
            pantallaReproductor = null;
        }
    }
}
