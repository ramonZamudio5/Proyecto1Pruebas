/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.tune.presentacion;

/**
 *
 * @author Dana Chavez
 */
public class Main {

    public static void main(String[] args) {
        // Configurar Nimbus si está disponible
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info :
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
        }

        java.awt.EventQueue.invokeLater(() -> new Navegador().iniciar());
    }
}