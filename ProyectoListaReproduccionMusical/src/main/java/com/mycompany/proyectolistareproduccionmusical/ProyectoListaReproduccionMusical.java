/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectolistareproduccionmusical;

import negocio.GestorMusica;
import presentacion.MainApp;

/**
 *
 * @author ramonsebastianzamudioayala
 */
public class ProyectoListaReproduccionMusical {

    public static void main(String[] args) {
        GestorMusica gestor = new GestorMusica();
        MainApp espotifai = new MainApp(gestor);
        espotifai.setVisible(true);
    }
}
