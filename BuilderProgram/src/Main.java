public class Main {
    public static void main(String[] args) {
        System.out.println("=== CONSTRUCCIÓN DE COCHES CON PATRÓN BUILDER ===\n");

                // 1. Uso directo del Builder
                System.out.println("1. COCHE PERSONALIZADO:");
                Coche cochePersonalizado = new Coche.Builder()
                        .withMarca("Toyota")
                        .withModelo("Corolla")
                        .withColor("Rojo")
                        .withMotor("2.0L")
                        .withPuertas(4)
                        .withTransmision("Automática")
                        .withExtraGPS()
                        .withExtraAireAcondicionado()
                        .build();

                System.out.println(cochePersonalizado);
                System.out.println("\n" + "=".repeat(50) + "\n");

                // 2. Uso del Director
                System.out.println("2. COCHE DEPORTIVO (con Director):");
                DirectorCoche director = new DirectorCoche();
                Coche cocheDeportivo = director.construirCocheDeportivo("Ferrari", "488 GTB");
                System.out.println(cocheDeportivo);
                System.out.println("\n" + "=".repeat(50) + "\n");

                // 3. Coche familiar
                System.out.println("3. COCHE FAMILIAR (con Director):");
                Coche cocheFamiliar = director.construirCocheFamiliar("Volkswagen", "Golf");
                System.out.println(cocheFamiliar);
                System.out.println("\n" + "=".repeat(50) + "\n");

                // 4. Coche económico
                System.out.println("4. COCHE ECONÓMICO (con Director):");
                Coche cocheEconomico = director.construirCocheEconomico("Kia", "Rio");
                System.out.println(cocheEconomico);
                System.out.println("\n" + "=".repeat(50) + "\n");

                // 5. Uso de builders específicos
                System.out.println("5. COCHE DEPORTIVO (con Builder específico):");
                DeportivoBuilder deportivoBuilder = new DeportivoBuilder();
                Coche otroDeportivo = deportivoBuilder
                        .baseDeportiva("Porsche", "911")
                        .conMotorPotente()
                        .conExtrasDeportivos()
                        .build();
                System.out.println(otroDeportivo);
                System.out.println("\n" + "=".repeat(50) + "\n");

                // 6. Construcción flexible
                System.out.println("6. COCHE MIXTO (construcción paso a paso):");
                Coche cocheMixto = new Coche.Builder()
                        .withMarca("BMW")
                        .withModelo("Serie 5")
                        .withColor("Gris")
                        .withMotor("2.5L")
                        .withPuertas(4)
                        // Sin GPS
                        .withExtraAireAcondicionado()
                        .build();
                System.out.println(cocheMixto);
            }
        }