/**
 * Interfaz que define el comportamiento básico de un vehículo.
 * Todos los vehículos deben poder acelerar, frenar y mostrar su información.
 */
interface Vehiculo {
    /**
     * Acción de acelerar el vehículo.
     */
    void acelerar();

    /**
     * Acción de frenar el vehículo.
     */
    void frenar();

    /**
     * Muestra la información del vehículo.
     */
    void mostrarInfo();
}

/**
 * Clase que representa un coche.
 * Implementa la interfaz Vehiculo.
 */
class Coche implements Vehiculo {
    private String marca;
    private String modelo;

    /**
     * Constructor de la clase Coche.
     * @param marca Marca del coche.
     * @param modelo Modelo del coche.
     */
    public Coche(String marca, String modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }

    @Override
    public void acelerar() {
        System.out.println("El coche " + marca + " " + modelo + " está acelerando por la carretera");
    }

    @Override
    public void frenar() {
        System.out.println("El coche está frenando con frenos de disco");
    }

    @Override
    public void mostrarInfo() {
        System.out.println("🚗 Coche: " + marca + " " + modelo + " - 4 ruedas, motor a gasolina");
    }
}

/**
 * Clase que representa una motocicleta.
 * Implementa la interfaz Vehiculo.
 */
class Motocicleta implements Vehiculo {
    private String marca;
    private int cilindrada;

    /**
     * Constructor de la clase Motocicleta.
     * @param marca Marca de la motocicleta.
     * @param cilindrada Cilindrada en cc de la motocicleta.
     */
    public Motocicleta(String marca, int cilindrada) {
        this.marca = marca;
        this.cilindrada = cilindrada;
    }

    @Override
    public void acelerar() {
        System.out.println("La motocicleta " + marca + " está acelerando con " + cilindrada + "cc");
    }

    @Override
    public void frenar() {
        System.out.println("La motocicleta está frenando con precaución");
    }

    @Override
    public void mostrarInfo() {
        System.out.println("🏍️ Motocicleta: " + marca + " " + cilindrada + "cc - 2 ruedas, ágil y rápida");
    }
}

/**
 * Clase que representa un camión.
 * Implementa la interfaz Vehiculo.
 */
class Camion implements Vehiculo {
    private String marca;
    private double capacidadCarga;

    /**
     * Constructor de la clase Camion.
     * @param marca Marca del camión.
     * @param capacidadCarga Capacidad de carga en toneladas.
     */
    public Camion(String marca, double capacidadCarga) {
        this.marca = marca;
        this.capacidadCarga = capacidadCarga;
    }

    @Override
    public void acelerar() {
        System.out.println("El camión " + marca + " está acelerando lentamente con " + capacidadCarga + " toneladas");
    }

    @Override
    public void frenar() {
        System.out.println("El camión está frenando con sistema de frenos neumático");
    }

    @Override
    public void mostrarInfo() {
        System.out.println("🚛 Camión: " + marca + " - Capacidad: " + capacidadCarga + " toneladas");
    }
}

/**
 * Enumeración que define los tipos de vehículos disponibles en la fábrica.
 */
enum TipoVehiculo {
    COCHE, MOTOCICLETA, CAMION
}

/**
 * Clase que implementa el patrón Factory Method.
 * Se encarga de crear objetos de tipo Vehiculo según el tipo solicitado.
 */
class VehiculoFactory {

    /**
     * Crea un vehículo en base al tipo solicitado y parámetros opcionales.
     * @param tipo Tipo de vehículo a crear.
     * @param parametros Parámetros adicionales (marca, modelo, cilindrada, capacidad).
     * @return Un objeto que implementa la interfaz Vehiculo.
     */
    public static Vehiculo crearVehiculo(TipoVehiculo tipo, String... parametros) {
        switch (tipo) {
            case COCHE:
                if (parametros.length >= 2) {
                    return new Coche(parametros[0], parametros[1]);
                } else {
                    return new Coche("Toyota", "Corolla");
                }

            case MOTOCICLETA:
                if (parametros.length >= 2) {
                    try {
                        int cilindrada = Integer.parseInt(parametros[1]);
                        return new Motocicleta(parametros[0], cilindrada);
                    } catch (NumberFormatException e) {
                        return new Motocicleta(parametros[0], 250);
                    }
                } else {
                    return new Motocicleta("Yamaha", 250);
                }

            case CAMION:
                if (parametros.length >= 2) {
                    try {
                        double capacidad = Double.parseDouble(parametros[1]);
                        return new Camion(parametros[0], capacidad);
                    } catch (NumberFormatException e) {
                        return new Camion(parametros[0], 10.0);
                    }
                } else {
                    return new Camion("Volvo", 15.0);
                }

            default:
                throw new IllegalArgumentException("Tipo de vehículo no soportado: " + tipo);
        }
    }

    /**
     * Crea un vehículo con valores por defecto según el tipo.
     * @param tipo Tipo de vehículo.
     * @return Un objeto Vehiculo con valores por defecto.
     */
    public static Vehiculo crearVehiculoPorDefecto(TipoVehiculo tipo) {
        return crearVehiculo(tipo);
    }
}

/**
 * Clase principal que demuestra el uso del patrón Factory
 * para crear y manejar diferentes tipos de vehículos.
 */
public class FactoryPatternDemo {
    /**
     * Método principal de ejecución.
     * Crea vehículos, muestra información, acelera y frena.
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        System.out.println("=== DEMOSTRACIÓN DEL PATRÓN FACTORY ===\n");

        Vehiculo coche1 = VehiculoFactory.crearVehiculo(TipoVehiculo.COCHE, "Honda", "Civic");
        Vehiculo moto1 = VehiculoFactory.crearVehiculo(TipoVehiculo.MOTOCICLETA, "Kawasaki", "600");
        Vehiculo camion1 = VehiculoFactory.crearVehiculo(TipoVehiculo.CAMION, "Mercedes", "12.5");

        Vehiculo coche2 = VehiculoFactory.crearVehiculoPorDefecto(TipoVehiculo.COCHE);
        Vehiculo moto2 = VehiculoFactory.crearVehiculoPorDefecto(TipoVehiculo.MOTOCICLETA);

        Vehiculo[] vehiculos = {coche1, moto1, camion1, coche2, moto2};

        System.out.println("1. INFORMACIÓN DE VEHÍCULOS:");
        System.out.println("----------------------------");
        for (Vehiculo vehiculo : vehiculos) {
            vehiculo.mostrarInfo();
        }

        System.out.println("\n2. ACELERANDO TODOS LOS VEHÍCULOS:");
        System.out.println("----------------------------------");
        for (Vehiculo vehiculo : vehiculos) {
            vehiculo.acelerar();
        }

        System.out.println("\n3. FRENANDO TODOS LOS VEHÍCULOS:");
        System.out.println("--------------------------------");
        for (Vehiculo vehiculo : vehiculos) {
            vehiculo.frenar();
        }

        System.out.println("\n4. CREACIÓN DINÁMICA BASADA EN INPUT:");
        System.out.println("-------------------------------------");

        String[] tiposInput = {"COCHE", "MOTOCICLETA", "CAMION"};

        for (String tipoStr : tiposInput) {
            try {
                TipoVehiculo tipo = TipoVehiculo.valueOf(tipoStr);
                Vehiculo vehiculo = VehiculoFactory.crearVehiculoPorDefecto(tipo);
                System.out.print("Creado dinámicamente: ");
                vehiculo.mostrarInfo();
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo de vehículo no válido: " + tipoStr);
            }
        }

        System.out.println("\n=== FIN DE LA DEMOSTRACIÓN ===");
    }
}

/**
 * Clase que representa una bicicleta.
 * Implementa la interfaz Vehiculo.
 */
class Bicicleta implements Vehiculo {
    private String tipo;
    private int numVelocidades;

    /**
     * Constructor de la clase Bicicleta.
     * @param tipo Tipo de bicicleta (ejemplo: montaña, urbana).
     * @param numVelocidades Número de velocidades.
     */
    public Bicicleta(String tipo, int numVelocidades) {
        this.tipo = tipo;
        this.numVelocidades = numVelocidades;
    }

    @Override
    public void acelerar() {
        System.out.println("La bicicleta " + tipo + " está siendo pedaleada");
    }

    @Override
    public void frenar() {
        System.out.println("La bicicleta está frenando con frenos de mano");
    }

    @Override
    public void mostrarInfo() {
        System.out.println("🚲 Bicicleta: " + tipo + " - " + numVelocidades + " velocidades, ecológica");
    }
}

