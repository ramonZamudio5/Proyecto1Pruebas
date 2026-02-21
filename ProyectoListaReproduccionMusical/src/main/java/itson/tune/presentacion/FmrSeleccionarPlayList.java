/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package itson.tune.presentacion;

import itson.tune.dominio.Playlist;
import itson.tune.negocio.PlaylistBO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Pride Factor Black
 */
public class FmrSeleccionarPlayList extends javax.swing.JFrame {

    private final PlaylistBO playlistBO;
    private final Navegador navegador;

    public FmrSeleccionarPlayList(PlaylistBO playlistBO, Navegador navegador) {
        this.playlistBO = playlistBO;
        this.navegador = navegador;
        initComponents();
        setLocationRelativeTo(null);
        configurarAcciones();
        cargarPlaylists();
    }

    private void cargarPlaylists() {
        List<Playlist> playlists = playlistBO.obtenerPlaylists();

        JPanel grid = new JPanel(new GridLayout(0, 2, 20, 20));
        grid.setOpaque(false);

        JPanel contenedorAncla = new JPanel(new BorderLayout());
        contenedorAncla.setOpaque(false);
        contenedorAncla.add(grid, BorderLayout.NORTH);

        for (Playlist p : playlists) {
            grid.add(crearTarjeta(p));
        }

        jScrollPaneContenedorPlaylists.setViewportView(contenedorAncla);
        jScrollPaneContenedorPlaylists.getViewport().setOpaque(false);
        jScrollPaneContenedorPlaylists.setOpaque(false);
        jScrollPaneContenedorPlaylists.setBorder(null);
        jScrollPaneContenedorPlaylists.revalidate();
        jScrollPaneContenedorPlaylists.repaint();
    }

    private JPanel crearTarjeta(Playlist playlist) {
        Dimension tamañoTarjeta = new Dimension(180, 220);

        JPanel tarjeta = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(15, 43, 39, 100));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };

        tarjeta.setOpaque(false);
        tarjeta.setPreferredSize(tamañoTarjeta);
        tarjeta.setMaximumSize(tamañoTarjeta);
        tarjeta.setMinimumSize(tamañoTarjeta);

        JLabel imagen = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (playlist.getImagen() != null) {
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2.drawImage(playlist.getImagen(), 5, 5,
                            getWidth() - 10, getHeight() - 10, this);
                } else {
                    g2.setColor(new Color(0x333333));
                    g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 10, 10);
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("SansSerif", Font.BOLD, 40));
                    String ini = playlist.getNombre().substring(0, 1).toUpperCase();
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(ini, (getWidth() - fm.stringWidth(ini)) / 2,
                            (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                }
                g2.dispose();
            }
        };
        imagen.setPreferredSize(new Dimension(180, 180));

        JLabel nombre = new JLabel(playlist.getNombre(), SwingConstants.CENTER);
        nombre.setForeground(Color.WHITE);
        nombre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nombre.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        tarjeta.add(imagen, BorderLayout.CENTER);
        tarjeta.add(nombre, BorderLayout.SOUTH);

        tarjeta.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                navegador.irAPlaylist(playlist);
            }
        });
        return tarjeta;
    }

    private void configurarAcciones() {
        btnAgregarPlaylist.addActionListener(e -> {
            Object[] datos = mostrarDialogoCrearPlaylist();
            if (datos == null) {
                return;
            }

            String nombre = (String) datos[0];
            java.awt.Image imagen = (java.awt.Image) datos[1];

            try {
                Playlist nueva = playlistBO.crearPlaylist(nombre);
                if (imagen != null) {
                    nueva.setImagen(imagen);
                }
                cargarPlaylists();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Muestra un diálogo personalizado que pide nombre e imagen. Retorna null
     * si el usuario cancela.
     */
    private Object[] mostrarDialogoCrearPlaylist() {
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.BorderLayout(10, 10));
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        javax.swing.JTextField txtNombre = new javax.swing.JTextField(20);
        javax.swing.JPanel panelNombre = new javax.swing.JPanel(new java.awt.BorderLayout(5, 0));
        panelNombre.add(new javax.swing.JLabel("Nombre:"), java.awt.BorderLayout.WEST);
        panelNombre.add(txtNombre, java.awt.BorderLayout.CENTER);

        javax.swing.JLabel lblPreview = new javax.swing.JLabel("Sin imagen",
                javax.swing.SwingConstants.CENTER);
        lblPreview.setPreferredSize(new java.awt.Dimension(150, 150));
        lblPreview.setBorder(javax.swing.BorderFactory.createLineBorder(
                new java.awt.Color(0x2A, 0x5A, 0x52), 2));
        lblPreview.setBackground(new java.awt.Color(0x0F, 0x2B, 0x27));
        lblPreview.setOpaque(true);
        lblPreview.setForeground(new java.awt.Color(0xA0, 0xC0, 0xB8));

        java.awt.Image[] imagenElegida = {null};

        javax.swing.JButton btnElegirImg = new javax.swing.JButton("Elegir imagen...");
        btnElegirImg.addActionListener(ev -> {
            javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
            chooser.setDialogTitle("Selecciona una imagen");
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Imágenes (jpg, png, gif)", "jpg", "jpeg", "png", "gif"));

            if (chooser.showOpenDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {
                java.awt.Image img = new javax.swing.ImageIcon(
                        chooser.getSelectedFile().getAbsolutePath()).getImage();
                imagenElegida[0] = img;

                javax.swing.ImageIcon icon = new javax.swing.ImageIcon(
                        img.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
                lblPreview.setIcon(icon);
                lblPreview.setText("");
            }
        });

        javax.swing.JPanel panelImagen = new javax.swing.JPanel(new java.awt.BorderLayout(5, 5));
        panelImagen.add(lblPreview, java.awt.BorderLayout.CENTER);
        panelImagen.add(btnElegirImg, java.awt.BorderLayout.SOUTH);

        panel.add(panelNombre, java.awt.BorderLayout.NORTH);
        panel.add(panelImagen, java.awt.BorderLayout.CENTER);

        int resultado = javax.swing.JOptionPane.showConfirmDialog(
                this, panel, "Nueva Playlist",
                javax.swing.JOptionPane.OK_CANCEL_OPTION,
                javax.swing.JOptionPane.PLAIN_MESSAGE);

        if (resultado != javax.swing.JOptionPane.OK_OPTION) {
            return null;
        }
        if (txtNombre.getText().isBlank()) {
            return null;
        }

        return new Object[]{txtNombre.getText().trim(), imagenElegida[0]};
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPaneContenedorPlaylists = new javax.swing.JScrollPane();
        btnAgregarPlaylist = new javax.swing.JButton();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jScrollPaneContenedorPlaylists, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 210, 420, 570));

        btnAgregarPlaylist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btnAgregarPlaylist.png"))); // NOI18N
        btnAgregarPlaylist.setBorderPainted(false);
        btnAgregarPlaylist.setContentAreaFilled(false);
        btnAgregarPlaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPlaylistActionPerformed(evt);
            }
        });
        getContentPane().add(btnAgregarPlaylist, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 50, 50));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PantallaSeleccionarPlayList.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 600, 830));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarPlaylistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPlaylistActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarPlaylistActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarPlaylist;
    private javax.swing.JScrollPane jScrollPaneContenedorPlaylists;
    private javax.swing.JLabel lblFondo;
    // End of variables declaration//GEN-END:variables
}
