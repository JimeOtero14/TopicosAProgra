package org.example.tarea.Model.Builder;

import org.example.tarea.Model.User;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserBuilder {
    public int id;
    public String username;
    public String correo;
    public String passwordHash;
    public LocalDateTime fechaRegistro;
    public String nombreCompleto;
    public LocalDate fechaNacimiento;

    public UserBuilder(String username, String correo, String nombreCompleto) {
        this.username = username;
        this.correo = correo;
        this.nombreCompleto = nombreCompleto;
    }

    public UserBuilder id(int id) {
        this.id = id;
        return this;
    }

    public UserBuilder passwordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public UserBuilder fechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
        return this;
    }

    public UserBuilder fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public User build() {
        if (username == null || correo == null || nombreCompleto == null) {
            throw new IllegalStateException("Username, correo y nombre completo son obligatorios");
        }
        return new User(this);
    }
}