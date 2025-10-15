package org.example.tarea.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Usuario {
    private int id;
    private static String username;
    private static String correo;
    private static String passwordHash;
    private LocalDateTime fechaRegistro;
    private static String nombreCompleto;
    private static LocalDate fechaNacimiento;

    // Constructor vacío
    public Usuario() {}

    // Constructor completo
    public Usuario(String username, String correo, String passwordHash,
                   String nombreCompleto, LocalDate fechaNacimiento) {
        this.username = username;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public static String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public static String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public static LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
