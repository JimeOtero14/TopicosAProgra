package org.example.javanetdb;

import org.example.javanetdb.Controller.ControladorChat;
import org.example.javanetdb.Model.ModeloChat;
import org.example.javanetdb.View.VistaChat;

import javax.swing.SwingUtilities;

/**
 * Cliente de Chat - Clase Principal
 * Implementa el patrón MVC con Swing
 *
 * Componentes:
 * - Modelo: ModeloChat.java (maneja la conexión con el servidor)
 * - Vista: VistaChat.java (interfaz gráfica Swing)
 * - Controlador: ControladorChat.java (lógica de negocio)
 */
public class ClienteChatMain {

    public static void main(String[] args) {
        System.out.println("Iniciando Cliente de Chat...");
        System.out.println("Conectará al servidor en localhost:3306");

        // Crear componentes MVC en el hilo de Swing
        SwingUtilities.invokeLater(() -> {
            // Crear componentes MVC
            ModeloChat modelo = new ModeloChat();
            VistaChat vista = new VistaChat();
            ControladorChat controlador = new ControladorChat(modelo, vista);

            // Conectar Vista con Controlador
            vista.setControlador(controlador);

            // Mensaje de bienvenida
            vista.mostrarMensaje("=== CLIENTE DE CHAT CON HISTORIAL ===\n");
            vista.mostrarMensaje("Sistema: Presiona 'Conectar' para iniciar\n");
            vista.mostrarMensaje("Sistema: Usa 'Ver Historial' para consultar mensajes anteriores\n");
            vista.mostrarMensaje("Sistema: Escribe 'salir' para desconectar\n\n");

            // Mostrar la interfaz
            vista.mostrar();
        });
    }
}
