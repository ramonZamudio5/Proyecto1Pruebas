/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.tune.util;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author payde
 */
public class ReproductorAudio {

    public enum Estado {
        DETENIDO, REPRODUCIENDO, ENPAUSA
    }

    private Clip clip;
    private Estado estado = Estado.DETENIDO;
    private long posicionPausa = 0;

    public void reproducir(File archivo) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        parar();

        AudioInputStream sourceStream = AudioSystem.getAudioInputStream(archivo);
        AudioFormat sourceFormat = sourceStream.getFormat();

        AudioFormat targetFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                sourceFormat.getSampleRate(),
                16,
                sourceFormat.getChannels(),
                sourceFormat.getChannels() * 2,
                sourceFormat.getSampleRate(),
                false
        );

        AudioInputStream pcmStream = AudioSystem.getAudioInputStream(targetFormat, sourceStream);

        clip = AudioSystem.getClip();
        clip.open(pcmStream);
        clip.start();
        posicionPausa = 0;
        estado = estado.REPRODUCIENDO;
    }

    public void setVolumen(float volumen) {
        if (clip == null || !clip.isOpen()) {
            return;
        }
        try {
            javax.sound.sampled.FloatControl control = (javax.sound.sampled.FloatControl) clip.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log10(Math.max(volumen, 0.0001f)) * 20);
            dB = Math.max(control.getMinimum(), Math.min(control.getMaximum(), dB));
            control.setValue(dB);
        } catch (IllegalArgumentException ex) {

        }
    }

    public void pausa() {
        if (estado == Estado.REPRODUCIENDO && clip != null) {
            posicionPausa = clip.getMicrosecondPosition();
            clip.stop();
            estado = Estado.ENPAUSA;
        }
    }

    public void resume() {
        if (estado == Estado.ENPAUSA && clip != null) {
            clip.setMicrosecondPosition(posicionPausa);
            clip.start();
            estado = Estado.REPRODUCIENDO;
        }
    }

    public void parar() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }
        estado = Estado.DETENIDO;
        posicionPausa = 0;
    }

    public int obtenerPosicionActualSegundos() {
        if (clip == null) {
            return 0;
        }
        return (int) (clip.getMicrosecondPosition() / 1_000_000);
    }

    public int obtenerDuracionTotalSegundos() {
        if (clip == null) {
            return 0;
        }
        return (int) (clip.getMicrosecondLength() / 1_000_000);
    }

    public Estado getEstado() {
        return estado;
    }

    public boolean enReproduccion() {
        return estado == Estado.REPRODUCIENDO;
    }
}
