package org.example.javanetdb.View;

import org.example.javanetdb.Controller.ControladorChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Vista - Interfaz gráfica del cliente de chat con Swing
 * Patrón MVC
 */
public class VistaChat extends JFrame {
    private JTextArea areaChat;
    private JTextField campoTexto;
    private JButton btnEnviar;
    private JButton btnConectar;
    private JButton btnDesconectar;
    private JButton btnHistorial;
    private ControladorChat controlador;

    public VistaChat() {
        inicializarUI();
    }

    /**
     * Configura el controlador
     */
    public void setControlador(ControladorChat controlador) {
        this.controlador = controlador;
    }

    /**
     * Inicializa la interfaz de usuario
     */
    private void inicializarUI() {
        setTitle("Cliente de Chat - Puerto 3306");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout principal
        setLayout(new BorderLayout(10, 10));

        // === PARTE SUPERIOR: Panel de conexión ===
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelTop.setBackground(new Color(240, 240, 240));

        btnConectar = new JButton("Conectar");
        btnConectar.setBackground(new Color(76, 175, 80));
        btnConectar.setForeground(Color.WHITE);
        btnConectar.setFont(new Font("Arial", Font.BOLD, 12));
        btnConectar.setFocusPainted(false);
        btnConectar.addActionListener(e -> {
            if (controlador != null) {
                controlador.conectar();
            }
        });

        btnDesconectar = new JButton("Desconectar");
        btnDesconectar.setBackground(new Color(244, 67, 54));
        btnDesconectar.setForeground(Color.WHITE);
        btnDesconectar.setFont(new Font("Arial", Font.BOLD, 12));
        btnDesconectar.setFocusPainted(false);
        btnDesconectar.setEnabled(false);
        btnDesconectar.addActionListener(e -> {
            if (controlador != null) {
                controlador.desconectar();
            }
        });

        JLabel labelInfo = new JLabel("Servidor: localhost:9090");
        labelInfo.setFont(new Font("Arial", Font.PLAIN, 11));
        labelInfo.setForeground(Color.GRAY);

        panelTop.add(btnConectar);
        panelTop.add(btnDesconectar);
        panelTop.add(Box.createHorizontalStrut(20));
        panelTop.add(labelInfo);

        // === CENTRO: Área de chat ===
        JPanel panelCenter = new JPanel(new BorderLayout(5, 5));
        panelCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelChat = new JLabel("Mensajes:");
        labelChat.setFont(new Font("Arial", Font.BOLD, 14));

        areaChat = new JTextArea();
        areaChat.setEditable(false);
        areaChat.setLineWrap(true);
        areaChat.setWrapStyleWord(true);
        areaChat.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaChat.setBackground(new Color(245, 245, 245));
        areaChat.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(areaChat);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panelCenter.add(labelChat, BorderLayout.NORTH);
        panelCenter.add(scrollPane, BorderLayout.CENTER);

        // === PARTE INFERIOR: Campo de texto y botón enviar ===
        JPanel panelBottom = new JPanel(new BorderLayout(10, 0));
        panelBottom.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        campoTexto = new JTextField();
        campoTexto.setFont(new Font("Arial", Font.PLAIN, 13));
        campoTexto.setEnabled(false);
        campoTexto.addActionListener(e -> enviarMensaje());

        btnEnviar = new JButton("Enviar");
        btnEnviar.setBackground(new Color(33, 150, 243));
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEnviar.setFocusPainted(false);
        btnEnviar.setEnabled(false);
        btnEnviar.setPreferredSize(new Dimension(100, 30));
        btnEnviar.addActionListener(e -> enviarMensaje());

        panelBottom.add(campoTexto, BorderLayout.CENTER);
        panelBottom.add(btnEnviar, BorderLayout.EAST);

        // === Agregar paneles al frame ===
        add(panelTop, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        // Manejar el cierre de la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (controlador != null) {
                    controlador.desconectar();
                }
            }
        });
    }

    /**
     * Envía el mensaje escrito en el campo de texto
     */
    private void enviarMensaje() {
        String mensaje = campoTexto.getText().trim();
        if (!mensaje.isEmpty() && controlador != null) {
            controlador.enviarMensaje(mensaje);
        }
    }

    /**
     * Muestra un mensaje en el área de chat
     */
    public void mostrarMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            areaChat.append(mensaje);
            areaChat.setCaretPosition(areaChat.getDocument().getLength());
        });
    }

    /**
     * Limpia el campo de texto
     */
    public void limpiarCampoTexto() {
        SwingUtilities.invokeLater(() -> campoTexto.setText(""));
    }

    /**
     * Habilita o deshabilita los controles de envío
     */
    public void habilitarEnvio(boolean habilitar) {
        SwingUtilities.invokeLater(() -> {
            campoTexto.setEnabled(habilitar);
            btnEnviar.setEnabled(habilitar);
            btnConectar.setEnabled(!habilitar);
            btnDesconectar.setEnabled(habilitar);

            if (habilitar) {
                campoTexto.requestFocus();
            }
        });
    }

    /**
     * Muestra la ventana
     */
    public void mostrar() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}