package org.example.tarea.TerminalBuilder;

import org.example.tarea.DAO.UsuarioDAO;
import org.example.tarea.Factory.DAOFactory;
import org.example.tarea.Model.Usuario;
import org.example.tarea.Strategy.EmailValidationStrategy;
import org.example.tarea.Strategy.NotEmptyValidation;
import org.example.tarea.Strategy.PasswordValidationStrategy;
import org.example.tarea.Strategy.ValidationStrategy;
import org.example.tarea.Strategy.UsernameValidationStrategy;
import org.example.tarea.Util.PasswordUtil;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

public class terminalView {

    //Patrón implementado en toda esta clase es Builder para crear objetos de forma más legible
    private final UsuarioDAO usuarioDAO;
    private final ValidationStrategy emailValidator = new EmailValidationStrategy();
    private final ValidationStrategy passwordValidator = new PasswordValidationStrategy();
    private final ValidationStrategy usernameValidator = new UsernameValidationStrategy();
    private final ValidationStrategy nombreValidator = new NotEmptyValidation("Nombre completo");

    private final Scanner scanner = new Scanner(System.in);

    public terminalView() {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        this.usuarioDAO = factory.getUsuarioDAO();
    }

    // Punto de entrada para modo terminal
    public static void run() {
        terminalView app = new terminalView();
        app.loop();
    }

    private void loop() {
        println("Bienvenido al sistema de login");
        while (true) {
            println("");
            println("Selecciona una opción:");
            println("1) Iniciar sesión");
            println("2) Registrarse");
            print("> ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    iniciarSesion();
                    break;
                case "2":
                    registrarUsuario();
                    break;
                default:
                    println("Opción inválida. Intenta de nuevo.");
            }
        }
    }

    private void registrarUsuario() {
        try {
            print("Nombre completo: ");
            String nombreCompleto = scanner.nextLine().trim();

            print("Fecha de nacimiento (YYYY-MM-DD): ");
            String fechaStr = scanner.nextLine().trim();
            LocalDate fechaNacimiento;
            try {
                fechaNacimiento = LocalDate.parse(fechaStr);
            } catch (DateTimeParseException ex) {
                println("Formato de fecha inválido. Usa YYYY-MM-DD.");
                return;
            }

            print("Nombre de usuario: ");
            String username = scanner.nextLine().trim();

            print("Correo electrónico: ");
            String correo = scanner.nextLine().trim();

            print("Contraseña: ");
            String password = scanner.nextLine();

            print("Confirmar contraseña: ");
            String confirmPassword = scanner.nextLine();

            if (!nombreValidator.validate(nombreCompleto)) {
                println(nombreValidator.getErrorMessage());
                return;
            }

            if (fechaNacimiento.isAfter(LocalDate.now().minusYears(13))) {
                println("Debes tener al menos 13 años para registrarte");
                return;
            }

            if (!usernameValidator.validate(username)) {
                println(usernameValidator.getErrorMessage());
                return;
            }

            if (!emailValidator.validate(correo)) {
                println(emailValidator.getErrorMessage());
                return;
            }

            if (!passwordValidator.validate(password)) {
                println(passwordValidator.getErrorMessage());
                return;
            }

            if (!password.equals(confirmPassword)) {
                println("Las contraseñas no coinciden");
                return;
            }

            if (usuarioDAO.existeUsername(username)) {
                println("El nombre de usuario ya está registrado");
                return;
            }

            if (usuarioDAO.existeCorreo(correo)) {
                println("El correo electrónico ya está registrado");
                return;
            }

            String passwordHash = PasswordUtil.hashPassword(password);
            Usuario nuevoUsuario = Usuario.builder(username, correo, passwordHash)
                    .setNombreCompleto(nombreCompleto)
                    .setFechaNacimiento(fechaNacimiento)
                    .build();

            usuarioDAO.insertarUsuario(nuevoUsuario);
            println("¡Registro exitoso! Ahora puedes iniciar sesión.");
        } catch (Exception e) {
            println("Error al registrar usuario: " + e.getMessage());
        }
    }

    private void iniciarSesion() {
        try {
            print("Usuario o correo: ");
            String usernameOrEmail = scanner.nextLine().trim();

            print("Contraseña: ");
            String password = scanner.nextLine();

            if (usernameOrEmail.isEmpty() || password.isEmpty()) {
                println("Por favor, completa todos los campos");
                return;
            }

            Optional<Usuario> usuarioOpt = usuarioDAO.buscarPorUsernameOCorreo(usernameOrEmail);
            if (usuarioOpt.isEmpty()) {
                println("Usuario no encontrado");
                return;
            }

            Usuario usuario = usuarioOpt.get();
            if (!PasswordUtil.checkPassword(password, usuario.getPasswordHash())) {
                println("Contraseña incorrecta");
                return;
            }

            bienvenida(usuario);
        } catch (Exception e) {
            println("Error al iniciar sesión: " + e.getMessage());
        }
    }

    private void bienvenida(Usuario usuario) {
        println("");
        println("===== Bienvenido =====");
        println("Hola, " + usuario.getNombreCompleto());
        println("Usuario: @" + usuario.getUsername());
        if (usuario.getFechaRegistro() != null) {
            println("Miembro desde: " + usuario.getFechaRegistro());
        }
        println("======================");
    }

    private void println(String s) {
        System.out.println(s);
    }

    private void print(String s) {
        System.out.print(s);
    }
}