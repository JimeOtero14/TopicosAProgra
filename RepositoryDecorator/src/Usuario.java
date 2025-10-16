public class Usuario {
        private String id;
        private String nombre;
        private String email;
        private String telefono;
        private boolean notificacionesEmail;
        private boolean notificacionesSMS;
        private boolean notificacionesPush;

        public Usuario(String id, String nombre, String email, String telefono) {
            this.id = id;
            this.nombre = nombre;
            this.email = email;
            this.telefono = telefono;
            this.notificacionesEmail = true;
            this.notificacionesSMS = true;
            this.notificacionesPush = true;
        }

        public String getId() { return id; }
        public String getNombre() { return nombre; }
        public String getEmail() { return email; }
        public String getTelefono() { return telefono; }
        public boolean isNotificacionesEmail() { return notificacionesEmail; }
        public boolean isNotificacionesSMS() { return notificacionesSMS; }
        public boolean isNotificacionesPush() { return notificacionesPush; }

        public void setNotificacionesEmail(boolean notificacionesEmail) {
            this.notificacionesEmail = notificacionesEmail;
        }
        public void setNotificacionesSMS(boolean notificacionesSMS) {
            this.notificacionesSMS = notificacionesSMS;
        }
        public void setNotificacionesPush(boolean notificacionesPush) {
            this.notificacionesPush = notificacionesPush;
        }

        @Override
        public String toString() {
            return String.format("Usuario{id='%s', nombre='%s', email='%s'}", id, nombre, email);
        }
    }