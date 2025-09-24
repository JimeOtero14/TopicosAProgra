public class Coche {
        private String marca;
        private String modelo;
        private String color;
        private String motor;
        private int puertas;
        private String transmision;
        private boolean extraGPS;
        private boolean extraAireAcondicionado;

        // Constructor privado para usar con el Builder
        private Coche() {}

        // Getters
        public String getMarca() { return marca; }
        public String getModelo() { return modelo; }
        public String getColor() { return color; }
        public String getMotor() { return motor; }
        public int getPuertas() { return puertas; }
        public String getTransmision() { return transmision; }
        public boolean hasExtraGPS() { return extraGPS; }
        public boolean hasExtraAireAcondicionado() { return extraAireAcondicionado; }

        @Override
        public String toString() {
            return String.format(
                    "Marca: %s\nModelo: %s\nColor: %s\nMotor: %s\nPuertas: %d\nTransmisión: %s\nGPS: %s\nAire Acondicionado: %s",
                    marca, modelo, color, motor, puertas, transmision,
                    extraGPS ? "Sí" : "No",
                    extraAireAcondicionado ? "Sí" : "No"
            );
        }

        // Builder estático interno
        public static class Builder {
            private Coche coche;

            public Builder() {
                this.coche = new Coche();
            }

            public Builder withMarca(String marca) {
                coche.marca = marca;
                return this;
            }

            public Builder withModelo(String modelo) {
                coche.modelo = modelo;
                return this;
            }

            public Builder withColor(String color) {
                coche.color = color;
                return this;
            }

            public Builder withMotor(String motor) {
                coche.motor = motor;
                return this;
            }

            public Builder withPuertas(int puertas) {
                coche.puertas = puertas;
                return this;
            }

            public Builder withTransmision(String transmision) {
                coche.transmision = transmision;
                return this;
            }

            public Builder withExtraGPS() {
                coche.extraGPS = true;
                return this;
            }

            public Builder withExtraAireAcondicionado() {
                coche.extraAireAcondicionado = true;
                return this;
            }

            public Coche build() {
                // Validaciones básicas
                if (coche.marca == null || coche.modelo == null) {
                    throw new IllegalStateException("Marca y modelo son obligatorios");
                }

                // Valores por defecto
                if (coche.color == null) coche.color = "Blanco";
                if (coche.motor == null) coche.motor = "1.6L";
                if (coche.puertas == 0) coche.puertas = 4;
                if (coche.transmision == null) coche.transmision = "Manual";

                return coche;
            }
        }
    }