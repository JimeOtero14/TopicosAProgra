public class DeportivoBuilder {
        private Coche.Builder builder;

        public DeportivoBuilder() {
            this.builder = new Coche.Builder();
        }

        public DeportivoBuilder baseDeportiva(String marca, String modelo) {
            builder.withMarca(marca)
                    .withModelo(modelo)
                    .withPuertas(2)
                    .withTransmision("Automática");
            return this;
        }

        public DeportivoBuilder conMotorPotente() {
            builder.withMotor("3.0L V6 Turbo");
            return this;
        }

        public DeportivoBuilder conExtrasDeportivos() {
            builder.withExtraGPS()
                    .withExtraAireAcondicionado();
            return this;
        }

        public Coche build() {
            return builder.build();
        }
    }