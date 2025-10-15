package org.example.tarea.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Usuario {
    private int id;
    private String username;
    private String correo;
    private String passwordHash;
    private LocalDateTime fechaRegistro;
    private String nombreCompleto;
    private LocalDate fechaNacimiento;


    // Constructor vacío (este es necesario para DAO porque así se puede llevar a cabo la compatibilidad entre frameworks y bibliotecas)
    public Usuario() {}

    // Constructor para nuevo usuario
    public Usuario(String username, String correo, String passwordHash,
                   String nombreCompleto, LocalDate fechaNacimiento) {
        this.username = username;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = LocalDateTime.now();
    }

    // Constructor completo
    public Usuario(int id, String username, String correo, String passwordHash,
                   LocalDateTime fechaRegistro, String nombreCompleto, LocalDate fechaNacimiento) {
        this.id = id;
        this.username = username;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.fechaRegistro = fechaRegistro;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Patrón Builder
    public static class UsuarioBuilder {
        private String username;
        private String correo;
        private String passwordHash;
        private int id = 0;
        private LocalDateTime fechaRegistro;
        private String nombreCompleto;
        private LocalDate fechaNacimiento;

        // Métodos de construcción (fluent interface)
        public UsuarioBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UsuarioBuilder setCorreo(String correo) {
            this.correo = correo;
            return this;
        }

        public UsuarioBuilder setPasswordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public UsuarioBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public UsuarioBuilder setFechaRegistro(LocalDateTime fechaRegistro) {
            this.fechaRegistro = fechaRegistro;
            return this;
        }

        public UsuarioBuilder setNombreCompleto(String nombreCompleto) {
            this.nombreCompleto = nombreCompleto;
            return this;
        }

        public UsuarioBuilder setFechaNacimiento(LocalDate fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
            return this;
        }

        // Metodo build con validaciones
        public Usuario build() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username no puede ser nulo o vacío");
            }
            if (correo == null || correo.trim().isEmpty()) {
                throw new IllegalArgumentException("Correo no puede ser nulo o vacío");
            }
            if (passwordHash == null || passwordHash.trim().isEmpty()) {
                throw new IllegalArgumentException("PasswordHash no puede ser nulo o vacío");
            }

            // Usar constructor apropiado
            if (id > 0 && fechaRegistro != null) {
                return new Usuario(id, username, correo, passwordHash, fechaRegistro, nombreCompleto, fechaNacimiento);
            } else {
                Usuario usuario = new Usuario(username, correo, passwordHash, nombreCompleto, fechaNacimiento);
                if (fechaRegistro != null) {
                    usuario.setFechaRegistro(fechaRegistro);
                }
                return usuario;
            }
        }
    }

    // Metodo estático para obtener un builder
    public static UsuarioBuilder builder(String username, String correo, String passwordHash) {
        return new UsuarioBuilder();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPasswordHash() {
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

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    // Metodo para crear usando Builder
    public static Usuario crearUsuario(String username, String correo, String passwordHash,
                                       String nombreCompleto, LocalDate fechaNacimiento) {
        return Usuario.builder(username, correo, passwordHash)
                .setUsername(username)
                .setCorreo(correo)
                .setPasswordHash(passwordHash)
                .setNombreCompleto(nombreCompleto)
                .setFechaNacimiento(fechaNacimiento)
                .build();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}