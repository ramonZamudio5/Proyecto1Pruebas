/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.tune.negocio;

import itson.tune.dominio.Cancion;
import itson.tune.util.ReproductorAudio;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author angel
 */
public class PlayerBO {

    private final ReproductorAudio reproductorAudio;

    private List<Cancion> cola;       
    private int currentIndex = -1;

    public PlayerBO(ReproductorAudio reproductorAudio) {
        this.reproductorAudio = reproductorAudio;
    }

    public void cargarYReproducir(List<Cancion> canciones, int startIndex) {
        if (canciones == null || canciones.isEmpty())
            throw new IllegalArgumentException("La lista de canciones no puede estar vacía.");
        if (startIndex < 0 || startIndex >= canciones.size())
            throw new IndexOutOfBoundsException("Índice de inicio inválido: " + startIndex);

        this.cola = canciones;
        this.currentIndex = startIndex;
        reproducirActual();
    }

    public void cambiarEstadoPausa() {
        if (reproductorAudio.enReproduccion()) {
            reproductorAudio.pausa();
        } else {
            reproductorAudio.resume();
        }
    }

    public void parar() {
        reproductorAudio.parar();
    }

    public void siguiente() {
        if (cola == null || cola.isEmpty()) return;
        currentIndex = (currentIndex + 1) % cola.size();
       reproducirActual();
    }

    public void previo() {
        if (cola == null || cola.isEmpty()) return;
        currentIndex = (currentIndex - 1 + cola.size()) % cola.size();
        reproducirActual();
    }

    public Optional<Cancion> obtenerCancionActual() {
        if (cola == null || currentIndex < 0 || currentIndex >= cola.size()) return Optional.empty();
        return Optional.of(cola.get(currentIndex));
    }

    public ReproductorAudio.Estado getEstado() { return reproductorAudio.getEstado(); }
    public int obtenerPosicionActualSegundos() { return reproductorAudio.obtenerPosicionActualSegundos(); }
    public int obtenerDuracionTotalSegundos() { return reproductorAudio.obtenerDuracionTotalSegundos(); }

    private void reproducirActual() {
        Cancion cancion = cola.get(currentIndex);
        try {
            reproductorAudio.reproducir(cancion.getArchivo());
        } catch (UnsupportedAudioFileException e) {
            throw new PlayerException("Formato de audio no soportado: " + cancion.getArchivo().getName(), e);
        } catch (IOException e) {
            throw new PlayerException("Error leyendo el archivo: " + cancion.getArchivo().getAbsolutePath(), e);
        } catch (LineUnavailableException e) {
            throw new PlayerException("No hay dispositivo de audio disponible.", e);
        }
    }

    public static class PlayerException extends RuntimeException {
        public PlayerException(String message, Throwable cause) { super(message, cause); }
    }
    
}
