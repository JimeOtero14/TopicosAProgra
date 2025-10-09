package org.example.tarea.Model;

import org.example.tarea.Model.Builder.UserBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private int id;
    private String username;
    private String correo;
    private String passwordHash;
    private LocalDateTime fechaRegistro;
    private String nombreCompleto;
    private LocalDate fechaNacimiento;

    // Constructor privado para el Builder
    public User(UserBuilder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.correo = builder.correo;
        this.passwordHash = builder.passwordHash;
        this.fechaRegistro = builder.fechaRegistro;
        this.nombreCompleto = builder.nombreCompleto;
        this.fechaNacimiento = builder.fechaNacimiento;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getCorreo() { return correo; }
    public String getPasswordHash() { return passwordHash; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public String getNombreCompleto() { return nombreCompleto; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
}