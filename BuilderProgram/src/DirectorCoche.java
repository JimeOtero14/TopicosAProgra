public class DirectorCoche {

    public Coche construirCocheDeportivo(String marca, String modelo) {
        return new Coche.Builder()
                .withMarca(marca)
                .withModelo(modelo)
                .withColor("Rojo")
                .withMotor("3.0L V6")
                .withPuertas(2)
                .withTransmision("Automática")
                .withExtraGPS()
                .withExtraAireAcondicionado()
                .build();
    }

    public Coche construirCocheFamiliar(String marca, String modelo) {
        return new Coche.Builder()
                .withMarca(marca)
                .withModelo(modelo)
                .withColor("Azul")
                .withMotor("2.0L")
                .withPuertas(5)
                .withTransmision("Automática")
                .withExtraAireAcondicionado()
                .build();
    }

    public Coche construirCocheEconomico(String marca, String modelo) {
        return new Coche.Builder()
                .withMarca(marca)
                .withModelo(modelo)
                .withColor("Blanco")
                .withMotor("1.4L")
                .withPuertas(4)
                .withTransmision("Manual")
                .build();
    }

    public Coche construirCocheLujo(String marca, String modelo) {
        return new Coche.Builder()
                .withMarca(marca)
                .withModelo(modelo)
                .withColor("Negro metálico")
                .withMotor("4.0L V8")
                .withPuertas(4)
                .withTransmision("Automática")
                .withExtraGPS()
                .withExtraAireAcondicionado()
                .build();
    }
}