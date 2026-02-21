/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package itson.tune.presentacion;

import itson.tune.dominio.Cancion;
import itson.tune.dominio.Playlist;
import itson.tune.negocio.PlaylistBO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author Pride Factor Black
 */
public class FmrPlaylist extends javax.swing.JFrame {

    private final PlaylistBO playlistBO;
    private Playlist playlist;
    private final Navegador navegador;

    public FmrPlaylist(PlaylistBO playlistBO, Playlist playlist, Navegador navegador) {
        this.playlistBO = playlistBO;
        this.playlist = playlist;
        this.navegador = navegador;
        initComponents();
        setLocationRelativeTo(null);
        configurarAcciones();
        refrescar();
    }

    private void refrescar() {
        playlistBO.encontrarPlaylistPorId(playlist.getId()).ifPresent(p -> this.playlist = p);
        jLabelNombrePlaylist.setText(playlist.getNombre());
        if (playlist.getImagen() != null) {
            mostrarImagenEnPanel(playlist.getImagen());
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        List<Cancion> canciones = playlist.getCanciones();
        if (canciones.isEmpty()) {
            JLabel vacio = new JLabel("No hay canciones. ¡Agrega una!", SwingConstants.CENTER);
            vacio.setForeground(new Color(0xA0, 0xC0, 0xB8));
            vacio.setFont(new Font("SansSerif", Font.PLAIN, 14));
            vacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(Box.createVerticalStrut(20));
            panel.add(vacio);
        }

        for (int i = 0; i < canciones.size(); i++) {
            panel.add(crearFila(i + 1, canciones.get(i)));
            panel.add(Box.createVerticalStrut(4));
        }

        jScrollPaneContenedorCanciones.setViewportView(panel);
        jScrollPaneContenedorCanciones.getViewport().setOpaque(false);
        jScrollPaneContenedorCanciones.setOpaque(false);
        jScrollPaneContenedorCanciones.setBorder(null);
        jScrollPaneContenedorCanciones.revalidate();
        jScrollPaneContenedorCanciones.repaint();
    }

    private void mostrarImagenEnPanel(java.awt.Image img) {
        jPanel1.removeAll();
        jPanel1.setLayout(new java.awt.BorderLayout());
        javax.swing.JLabel lblImg = new javax.swing.JLabel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                        java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                g2.dispose();
            }
        };
        jPanel1.add(lblImg, java.awt.BorderLayout.CENTER);
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    private JPanel crearFila(int numero, Cancion cancion) {
        JPanel fila = new JPanel(new BorderLayout(8, 0));
        fila.setOpaque(false);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        fila.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        fila.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel num = new JLabel(numero + ".");
        num.setForeground(new Color(0xA0, 0xC0, 0xB8));
        num.setFont(new Font("SansSerif", Font.PLAIN, 12));
        num.setPreferredSize(new Dimension(24, 20));

        JLabel titulo = new JLabel(cancion.getTitulo());
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel artista = new JLabel(cancion.getArtista());
        artista.setForeground(new Color(0xA0, 0xC0, 0xB8));
        artista.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 2));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(artista);

        JButton btnEliminar = new JButton("🗑");
        btnEliminar.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setForeground(new Color(0xA0, 0xC0, 0xB8));
        btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEliminar.addActionListener(e -> confirmarEliminar(cancion));

        fila.add(num, BorderLayout.WEST);
        fila.add(textos, BorderLayout.CENTER);
        fila.add(btnEliminar, BorderLayout.EAST);

        fila.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (!SwingUtilities.isDescendingFrom(e.getComponent(), btnEliminar)) {
                    navegador.irAReproductor(playlist, cancion);
                }
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                fila.setOpaque(true);
                fila.setBackground(new Color(0x2A, 0x5A, 0x52));
                textos.setOpaque(true);
                textos.setBackground(new Color(0x2A, 0x5A, 0x52));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                fila.setOpaque(false);
                textos.setOpaque(false);
                fila.repaint();
            }
        });
        return fila;
    }

    private void confirmarEliminar(Cancion cancion) {
        int op = JOptionPane.showConfirmDialog(this,
                "¿Eliminar \"" + cancion.getTitulo() + "\"?\n(El archivo en tu PC no se borrará)",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            playlistBO.eliminarCancionPlaylist(playlist.getId(), cancion.getId());
            refrescar();
        }
    }

    private void configurarAcciones() {
        btnVolver.addActionListener(e -> navegador.irASeleccionarPlaylist());

        btnPlay.addActionListener(e -> {
            if (playlist.estaVacia()) {
                JOptionPane.showMessageDialog(this,
                        "Agrega canciones antes de reproducir.", "Sin canciones", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            navegador.irAReproductor(playlist, playlist.getCanciones().get(0));
        });

        btnAgregarCancion.addActionListener(e -> agregarCancion());
    }

    private void agregarCancion() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecciona un archivo de audio");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Audio (mp3, wav, aiff)", "mp3", "wav", "aiff"));
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = chooser.getSelectedFile();
        String defTitulo = archivo.getName().replaceAll("\\.[^.]+$", "");

        String titulo = (String) JOptionPane.showInputDialog(this,
                "Título:", "Agregar canción", JOptionPane.PLAIN_MESSAGE, null, null, defTitulo);
        if (titulo == null || titulo.isBlank()) {
            return;
        }

        String artista = (String) JOptionPane.showInputDialog(this,
                "Artista:", "Agregar canción", JOptionPane.PLAIN_MESSAGE, null, null, "Desconocido");
        if (artista == null) {
            artista = "Desconocido";
        }

        String genero = (String) JOptionPane.showInputDialog(this,
                "Género (opcional):", "Agregar canción", JOptionPane.PLAIN_MESSAGE, null, null, "");
        if (genero == null) {
            genero = "";
        }

        try {
            playlistBO.agregarCancionAPlaylist(playlist.getId(), titulo.trim(), artista.trim(), genero.trim(), archivo);
            refrescar();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelNombrePlaylist = new javax.swing.JLabel();
        btnPlay = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnVolver = new javax.swing.JButton();
        btnAgregarCancion = new javax.swing.JButton();
        jScrollPaneContenedorCanciones = new javax.swing.JScrollPane();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelNombrePlaylist.setFont(new java.awt.Font("MS PGothic", 0, 42)); // NOI18N
        jLabelNombrePlaylist.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombrePlaylist.setText("jLabel1");
        getContentPane().add(jLabelNombrePlaylist, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 210, 280, 50));

        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pausar.png"))); // NOI18N
        btnPlay.setBorder(null);
        btnPlay.setBorderPainted(false);
        btnPlay.setContentAreaFilled(false);
        btnPlay.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnPlay, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, -1, 80));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 180, 180));

        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btnVolver.png"))); // NOI18N
        btnVolver.setBorderPainted(false);
        btnVolver.setContentAreaFilled(false);
        getContentPane().add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        btnAgregarCancion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btnAgregarCancion.png"))); // NOI18N
        btnAgregarCancion.setBorder(null);
        btnAgregarCancion.setBorderPainted(false);
        btnAgregarCancion.setContentAreaFilled(false);
        btnAgregarCancion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCancionActionPerformed(evt);
            }
        });
        getContentPane().add(btnAgregarCancion, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 390, -1, -1));
        getContentPane().add(jScrollPaneContenedorCanciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 450, 480, 350));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PantallaPlayList.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 830));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarCancionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCancionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarCancionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCancion;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabelNombrePlaylist;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPaneContenedorCanciones;
    private javax.swing.JLabel lblFondo;
    // End of variables declaration//GEN-END:variables
}
