// CocheFamiliarBuilder.java
    public class FamiliarBuilder {
        private Coche.Builder builder;

        public FamiliarBuilder() {
            this.builder = new Coche.Builder();
        }

        public FamiliarBuilder baseFamiliar(String marca, String modelo) {
            builder.withMarca(marca)
                    .withModelo(modelo)
                    .withPuertas(5)
                    .withTransmision("Automática");
            return this;
        }

        public FamiliarBuilder conMotorEficiente() {
            builder.withMotor("2.0L Híbrido");
            return this;
        }

        public FamiliarBuilder conExtrasFamiliares() {
            builder.withExtraGPS()
                    .withExtraAireAcondicionado();
            return this;
        }

        public Coche build() {
            return builder.build();
        }
    }
