/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dominio.Cancion;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import negocio.GestorMusica;

/**
 *
 * @author ramonsebastianzamudioayala
 */
public class CatalogoCanciones extends JFrame {
    private String nombrePlaylist;
    private ModoVentana modo;
    public CatalogoCanciones(GestorMusica gestor, String nombrePlaylist, ModoVentana modo) {
        this.nombrePlaylist = nombrePlaylist;
        this.modo = modo;
        setTitle("Lista de canciones");
        setSize(400, 300);
        getContentPane().setBackground(new Color(25, 20,20));
        setLocationRelativeTo(null);


        // Modelo de la lista
        DefaultListModel<Cancion> modelo = new DefaultListModel<>();

        for(Cancion cancion : gestor.obtenerBiblioteca()){
            modelo.addElement(cancion);
        }

        JList<Cancion> listaCanciones = new JList<>(modelo);
        listaCanciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        listaCanciones.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Cancion seleccionada = listaCanciones.getSelectedValue();
                if (seleccionada != null && modo.equals(ModoVentana.AGREGAR)) {
                    gestor.agregarCancionAPlaylist(nombrePlaylist, listaCanciones.getSelectedIndex());
                     JOptionPane.showMessageDialog(
                        this,
                        "Canción agregada correctamente:\n" + seleccionada.getTitulo(),
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
            );
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(listaCanciones);
        scrollPane.setBackground(new Color(25, 20,20));
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        JButton salir = new JButton();
        salir.setText("Salir");
        salir.setBackground(Color.red);
        salir.addActionListener(e->{
            dispose();
        });
        add(salir,BorderLayout.SOUTH);
    }
    

}
