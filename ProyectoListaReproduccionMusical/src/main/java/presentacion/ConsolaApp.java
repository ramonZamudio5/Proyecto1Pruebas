/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dominio.Cancion;
import dominio.Playlist;
import java.util.List;
import java.util.Scanner;
import negocio.GestorMusica;

/**
 *
 * @author ramonsebastianzamudioayala
 */
public class ConsolaApp {
    private GestorMusica gestor;
    private Scanner scanner;

    public ConsolaApp() {
        this.gestor = new GestorMusica();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = 0;
        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                procesarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingresa un número válido.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 5);
    }

    private void mostrarMenu() {
        System.out.println("\n=== SPOTIFY DE CONSOLA ===");
        System.out.println("1. Crear Playlist");
        System.out.println("2. Ver Biblioteca de Canciones");
        System.out.println("3. Agregar Canción a Playlist");
        System.out.println("4. Reproducir Playlist");
        System.out.println("5. Salir");
        System.out.print("Selecciona una opción: ");
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                System.out.print("Nombre de la nueva playlist: ");
                String nombre = scanner.nextLine();
                gestor.crearNuevaPlaylist(nombre);
                System.out.println("¡Playlist creada con éxito!");
                break;
            case 2:
                listarBiblioteca();
                break;
            case 3:
                agregarCancionUI();
                break;
            case 4:
                reproducirUI();
                break;
            case 5:
                System.out.println("¡Hasta luego!");
                break;
            default:
                System.out.println("Opción no reconocida.");
        }
    }

    private void listarBiblioteca() {
        System.out.println("\n--- Canciones Disponibles ---");
        List<Cancion> canciones = gestor.obtenerBiblioteca();
        for (int i = 0; i < canciones.size(); i++) {
            System.out.println(i + ". " + canciones.get(i));
        }
    }

    private void agregarCancionUI() {
        listarPlaylists();
        System.out.print("Escribe el nombre de la playlist destino: ");
        String nombrePl = scanner.nextLine();
        
        listarBiblioteca();
        System.out.print("Ingresa el NÚMERO de la canción a agregar: ");
        int indice = Integer.parseInt(scanner.nextLine());
        
        gestor.agregarCancionAPlaylist(nombrePl, indice);
        System.out.println("Canción agregada correctamente.");
    }

    private void reproducirUI() {
        listarPlaylists();
        System.out.print("Escribe el nombre de la playlist a reproducir: ");
        String nombre = scanner.nextLine();
        gestor.reproducirPlaylist(nombre);
    }

    private void listarPlaylists() {
        System.out.println("\n--- Tus Playlists ---");
        List<Playlist> lists = gestor.obtenerPlaylists();
        if(lists.isEmpty()) System.out.println("(No tienes playlists creadas)");
        for (Playlist p : lists) {
            System.out.println("- " + p.getNombre() + " (" + p.getCanciones().size() + " canciones)");
        }
    }

}
