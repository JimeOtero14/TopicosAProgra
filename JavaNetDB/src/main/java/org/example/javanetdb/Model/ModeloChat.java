package org.example.javanetdb.Model;

import java.io.*;
import java.net.*;

/**
 * Modelo - Maneja la lógica de conexión con el servidor
 * Patrón MVC
 */
public class ModeloChat {
    private static final String HOST = "localhost";
    private static final int PUERTO = 9090;

    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
    private boolean conectado;

    /**
     * Conecta con el servidor
     */
    public boolean conectar() {
        try {
            socket = new Socket(HOST, PUERTO);
            entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            salida = new PrintWriter(
                    socket.getOutputStream(), true
            );
            conectado = true;
            System.out.println("Conectado al servidor en " + HOST + ":" + PUERTO);
            return true;
        } catch (IOException e) {
            System.err.println("Error al conectar: " + e.getMessage());
            conectado = false;
            return false;
        }
    }

    /**
     * Envía un mensaje al servidor
     */
    public void enviarMensaje(String mensaje) throws IOException {
        if (salida != null && conectado) {
            salida.println(mensaje);
        } else {
            throw new IOException("No conectado al servidor");
        }
    }

    /**
     * Recibe un mensaje del servidor
     */
    public String recibirMensaje() throws IOException {
        if (entrada != null && conectado) {
            return entrada.readLine();
        } else {
            throw new IOException("No conectado al servidor");
        }
    }

    /**
     * Desconecta del servidor
     */
    public void desconectar() {
        try {
            if (salida != null) salida.close();
            if (entrada != null) entrada.close();
            if (socket != null) socket.close();
            conectado = false;
            System.out.println("Desconectado del servidor");
        } catch (IOException e) {
            System.err.println("Error al desconectar: " + e.getMessage());
        }
    }

    /**
     * Verifica si está conectado
     */
    public boolean estaConectado() {
        return conectado && socket != null && !socket.isClosed();
    }
}