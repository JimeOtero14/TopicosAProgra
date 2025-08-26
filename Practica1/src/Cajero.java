import java.util.Scanner;

public class Cajero {
    private Usuario[] usuarios;
    private Usuario usuarioActual;

    public Cajero(Usuario[] usuarios) {
        this.usuarios = usuarios;
        this.usuarioActual = null;
    }

    public Usuario autenticar(String pin) {
        for (Usuario u : usuarios) {
            if (u.getPin().equals(pin)) {
                usuarioActual = u;
                return u;
            }
        }
        return null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public double verSaldo() {
        return usuarioActual.getSaldo();
    }

    public boolean retirar(double cantidad) {
        return usuarioActual.retirar(cantidad);
    }

    public void depositar(double cantidad) {
        usuarioActual.depositar(cantidad);
    }

    public void iniciarSesion() {
        Scanner scanner = new Scanner(System.in);
        int intentos = 0;
        boolean salir = false;
        Usuario usuarioActual = null;

        System.out.println("=== Bienvenido al Cajero ===");

        while (intentos < 3 && usuarioActual == null) {
            System.out.print("Ingrese su PIN: ");
            String pin = scanner.nextLine();
            usuarioActual = autenticar(pin);
            if (usuarioActual == null) {
                System.out.println("PIN incorrecto.");
                intentos++;
            }
        }

        if (usuarioActual == null) {
            System.out.println("Demasiados intentos fallidos. Adiós.");
            return;
        }

        System.out.println("Bienvenido, " + usuarioActual.getNombre());

        while (!salir) {
            System.out.println("\n1. Ver saldo");
            System.out.println("2. Retirar dinero");
            System.out.println("3. Depositar dinero");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.println("Su saldo es: $" + verSaldo());
                    break;
                case 2:
                    System.out.print("Ingrese cantidad a retirar: ");
                    double retiro = scanner.nextDouble();
                    if (retirar(retiro)) {
                        System.out.println("Retiro exitoso. Nuevo saldo: $" + verSaldo());
                    } else {
                        System.out.println("Fondos insuficientes.");
                    }
                    break;
                case 3:
                    System.out.print("Ingrese cantidad a depositar: ");
                    double deposito = scanner.nextDouble();
                    depositar(deposito);
                    System.out.println("Depósito exitoso. Nuevo saldo: $" + verSaldo());
                    break;
                case 4:
                    salir = true;
                    System.out.println("Gracias por usar el cajero.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        scanner.close();
    }
}
