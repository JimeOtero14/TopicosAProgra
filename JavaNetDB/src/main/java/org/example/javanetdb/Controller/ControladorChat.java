package org.example.javanetdb.Controller;

import org.example.javanetdb.Model.ModeloChat;
import org.example.javanetdb.View.VistaChat;

import java.io.IOException;

/**
 * Controlador - Maneja la comunicación entre el Modelo y la Vista
 * Patrón MVC (Compatible con Swing)
 */
public class ControladorChat {
    private ModeloChat modelo;
    private VistaChat vista;
    private Thread hiloEscucha;
    private volatile boolean escuchando;

    public ControladorChat(ModeloChat modelo, VistaChat vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.escuchando = false;
    }

    /**
     * Conecta al servidor
     */
    public void conectar() {
        if (modelo.conectar()) {
            vista.mostrarMensaje("Sistema: Conectado al servidor\n");
            vista.habilitarEnvio(true);
            iniciarEscucha();
        } else {
            vista.mostrarMensaje("Sistema: Error al conectar con el servidor\n");
            vista.habilitarEnvio(false);
        }
    }

    /**
     * Envía un mensaje al servidor
     */
    public void enviarMensaje(String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            return;
        }

        try {
            modelo.enviarMensaje(mensaje);
            vista.mostrarMensaje("Tú: " + mensaje + "\n");
            vista.limpiarCampoTexto();

            // Si el usuario escribe "salir", desconectar
            if (mensaje.equalsIgnoreCase("salir")) {
                desconectar();
            }
        } catch (IOException e) {
            vista.mostrarMensaje("Sistema: Error al enviar mensaje - " +
                    e.getMessage() + "\n");
        }
    }

    /**
     * Inicia un hilo para escuchar mensajes del servidor
     */
    private void iniciarEscucha() {
        escuchando = true;
        hiloEscucha = new Thread(() -> {
            try {
                while (escuchando && modelo.estaConectado()) {
                    String mensajeServidor = modelo.recibirMensaje();

                    if (mensajeServidor != null) {
                        vista.mostrarMensaje(mensajeServidor + "\n");
                    } else {
                        break; // Conexión cerrada
                    }
                }
            } catch (IOException e) {
                if (escuchando) {
                    vista.mostrarMensaje("Sistema: Conexión perdida\n");
                }
            } finally {
                vista.habilitarEnvio(false);
            }
        });

        hiloEscucha.setDaemon(true);
        hiloEscucha.start();
    }

    /**
     * Desconecta del servidor
     */
    public void desconectar() {
        escuchando = false;
        modelo.desconectar();
        vista.mostrarMensaje("Sistema: Desconectado del servidor\n");
        vista.habilitarEnvio(false);

        if (hiloEscucha != null) {
            hiloEscucha.interrupt();
        }
    }

    /**
     * Consulta el historial de mensajes
     */
    public void consultarHistorial() {
        try {
            modelo.enviarMensaje("HISTORIAL");
            vista.mostrarMensaje("\n--- Solicitando historial al servidor ---\n");
        } catch (IOException e) {
            vista.mostrarMensaje("Sistema: Error al solicitar historial - " +
                    e.getMessage() + "\n");
        }
    }
}