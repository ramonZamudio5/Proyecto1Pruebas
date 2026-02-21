/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package itson.tune.presentacion;

import itson.tune.dominio.Cancion;
import itson.tune.dominio.Playlist;
import itson.tune.negocio.PlayerBO;
import itson.tune.negocio.PlaylistBO;
import itson.tune.util.ReproductorAudio;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Pride Factor Black
 */
public class FmrReproducirCancion extends javax.swing.JFrame {

    private final PlaylistBO playlistBO;
    private final Playlist playlist;
    private final PlayerBO playerBO;
    private final Navegador navegador;

    private Timer timerUI;

    public FmrReproducirCancion(PlaylistBO playlistBO, Playlist playlist,
            Cancion cancionInicial, Navegador navegador) {
        this.playlistBO = playlistBO;
        this.playlist = playlist;
        this.navegador = navegador;
        this.playerBO = new PlayerBO(new ReproductorAudio());
        initComponents();
        setLocationRelativeTo(null);
        agregarComponentesExtra();
        configurarAcciones();
        iniciarReproduccion(cancionInicial);
    }

    private void agregarComponentesExtra() {
        lblTitulo = new javax.swing.JLabel("...");
        lblTitulo.setFont(new java.awt.Font("MS PGothic", java.awt.Font.BOLD, 22));
        lblTitulo.setForeground(java.awt.Color.WHITE);
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 500, 480, 30));

        lblArtista = new javax.swing.JLabel("...");
        lblArtista.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 15));
        lblArtista.setForeground(new java.awt.Color(0xA0, 0xC0, 0xB8));
        lblArtista.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lblArtista, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 536, 480, 24));

        barraProgreso.setValue(0);
        barraProgreso.setForeground(new java.awt.Color(0xC0, 0x39, 0x39));
        barraProgreso.setBackground(new java.awt.Color(0x2A, 0x5A, 0x52));
        barraProgreso.setBorderPainted(false);

        lblTiempo = new javax.swing.JLabel("00:00 / 00:00");
        lblTiempo.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
        lblTiempo.setForeground(new java.awt.Color(0xA0, 0xC0, 0xB8));
        lblTiempo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lblTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 584, 480, 20));

        sliderVolumen.setOpaque(false);
        sliderVolumen.setFocusable(false);
        sliderVolumen.addChangeListener(e -> {
            float volumen = sliderVolumen.getValue() / 100f;
            playerBO.setVolumen(volumen);
        });

        getContentPane().setComponentZOrder(lblFondo, getContentPane().getComponentCount() - 1);

        getContentPane().setComponentZOrder(sliderVolumen, 0);
        getContentPane().setComponentZOrder(barraProgreso, 0);

        mostrarImagenPlaylist();
        this.revalidate();
        this.repaint();
    }

    private void iniciarReproduccion(Cancion cancion) {
        List<Cancion> canciones = playlist.getCanciones();
        int indice = canciones.indexOf(cancion);
        if (indice < 0) {
            indice = 0;
        }
        try {
            playerBO.cargarYReproducir(canciones, indice);
        } catch (PlayerBO.PlayerException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(), "Error de reproducción", JOptionPane.ERROR_MESSAGE);
        }
        actualizarInfoCancion();
        btnPausar.setIcon(iconoPausa());
        iniciarTimer();
    }

    private void iniciarTimer() {
        timerUI = new Timer(500, e -> {
            int actual = playerBO.obtenerPosicionActualSegundos();
            int total = playerBO.obtenerDuracionTotalSegundos();
            lblTiempo.setText(fmt(actual) + " / " + fmt(total));
            if (total > 0) {
                barraProgreso.setValue((int) (100.0 * actual / total));
            }
        });
        timerUI.start();
    }

    private void actualizarInfoCancion() {
        playerBO.obtenerCancionActual().ifPresent(c -> {
            lblTitulo.setText(c.getTitulo());
            lblArtista.setText(c.getArtista());
        });
    }

    private void configurarAcciones() {
        btnVolver.addActionListener(e -> {
            playerBO.parar();
            if (timerUI != null) {
                timerUI.stop();
            }
            navegador.irAPlaylist(playlist);
        });

        btnPausar.addActionListener(e -> {
            playerBO.cambiarEstadoPausa();
            boolean rep = playerBO.getEstado() == ReproductorAudio.Estado.REPRODUCIENDO;
            btnPausar.setIcon(rep ? iconoPausa() : iconoPlay());
        });

        btnPaAtras.addActionListener(e -> {
            playerBO.previo();
            actualizarInfoCancion();
            btnPausar.setIcon(iconoPausa());
        });

        btnPaLante.addActionListener(e -> {
            playerBO.siguiente();
            actualizarInfoCancion();
            btnPausar.setIcon(iconoPausa());
        });
    }

    private void mostrarImagenPlaylist() {
        if (playlist.getImagen() == null) {
            return;
        }

        jPanelContenedorFoto.removeAll();
        jPanelContenedorFoto.setLayout(new java.awt.BorderLayout());

        javax.swing.JLabel lblImg = new javax.swing.JLabel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                        java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(playlist.getImagen(), 0, 0, getWidth(), getHeight(), this);
                g2.dispose();
            }
        };

        jPanelContenedorFoto.add(lblImg, java.awt.BorderLayout.CENTER);
        jPanelContenedorFoto.setOpaque(false);
        jPanelContenedorFoto.revalidate();
        jPanelContenedorFoto.repaint();
    }

    private ImageIcon iconoPausa() {
        return new ImageIcon(getClass().getResource("/images/play.png"));
    }

    private ImageIcon iconoPlay() {
        return new ImageIcon(getClass().getResource("/images/pausar.png"));
    }

    private String fmt(int s) {
        return String.format("%02d:%02d", s / 60, s % 60);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sliderVolumen = new javax.swing.JSlider();
        lblVolumeUp = new javax.swing.JLabel();
        lblVolumeDown = new javax.swing.JLabel();
        jPanelContenedorFoto = new javax.swing.JPanel();
        barraProgreso = new javax.swing.JProgressBar();
        lblTiempo = new javax.swing.JLabel();
        lblArtista = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        btnPaAtras = new javax.swing.JButton();
        btnPaLante = new javax.swing.JButton();
        btnPausar = new javax.swing.JButton();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sliderVolumen.setOrientation(javax.swing.JSlider.VERTICAL);
        getContentPane().add(sliderVolumen, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 200, 70, 220));

        lblVolumeUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/volumeUp.png"))); // NOI18N
        getContentPane().add(lblVolumeUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 170, -1, -1));

        lblVolumeDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/volumeDown.png"))); // NOI18N
        getContentPane().add(lblVolumeDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 430, -1, -1));
        getContentPane().add(jPanelContenedorFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 320, 300));
        getContentPane().add(barraProgreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 620, 300, 10));
        getContentPane().add(lblTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 650, -1, -1));
        getContentPane().add(lblArtista, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 450, -1, -1));
        getContentPane().add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 450, 140, -1));

        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btnVolver.png"))); // NOI18N
        btnVolver.setBorderPainted(false);
        btnVolver.setContentAreaFilled(false);
        getContentPane().add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        btnPaAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/skip_next_filled.png"))); // NOI18N
        btnPaAtras.setBorderPainted(false);
        btnPaAtras.setContentAreaFilled(false);
        btnPaAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaAtrasActionPerformed(evt);
            }
        });
        getContentPane().add(btnPaAtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 670, -1, -1));

        btnPaLante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/skip_next_filled_derecha.png"))); // NOI18N
        btnPaLante.setBorderPainted(false);
        btnPaLante.setContentAreaFilled(false);
        getContentPane().add(btnPaLante, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 670, -1, -1));

        btnPausar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pausar.png"))); // NOI18N
        btnPausar.setBorderPainted(false);
        btnPausar.setContentAreaFilled(false);
        btnPausar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPausarActionPerformed(evt);
            }
        });
        getContentPane().add(btnPausar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 670, 90, -1));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PantallaReproducirCancion.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 830));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPaAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaAtrasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPaAtrasActionPerformed

    private void btnPausarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPausarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPausarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgreso;
    private javax.swing.JButton btnPaAtras;
    private javax.swing.JButton btnPaLante;
    private javax.swing.JButton btnPausar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JPanel jPanelContenedorFoto;
    private javax.swing.JLabel lblArtista;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblTiempo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblVolumeDown;
    private javax.swing.JLabel lblVolumeUp;
    private javax.swing.JSlider sliderVolumen;
    // End of variables declaration//GEN-END:variables
}
