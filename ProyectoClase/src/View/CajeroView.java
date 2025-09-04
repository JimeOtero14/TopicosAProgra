package View;

import java.util.Scanner;

/**
 * Clase que gestiona la interfaz de usuario del cajero automático.
 * Se encarga de mostrar mensajes, solicitar datos y leer las opciones del usuario.
 *
 * @author Jimena Otero
 */

public class CajeroView {
    private Scanner scanner;

    /**
     * Constructor que inicializa el scanner para leer la entrada del usuario.
     */

    public CajeroView(){
        scanner = new Scanner(System.in);
    }

    /**
     * Muestra el mensaje de bienvenida del cajero automático.
     */

    public void mostrarBienvenida(){
        System.out.println("======================================");
        System.out.println("Bienvenido al cajero automatico de BBVA");
        System.out.println("======================================");
    }

    /**
     * Solicita al usuario que ingrese su número de cuenta.
     *
     * @return El número de cuenta ingresado por el usuario
     */

    public String solicitarNumeroCuenta(){
        System.out.println("Ingresa tu número de cuenta: ");
        return scanner.nextLine();
    }

    /**
     * Solicita al usuario que ingrese su PIN.
     *
     * @return El PIN ingresado por el usuario
     */

    public String solicitarPin(){
        System.out.println("Ingresa tu PIN: ");
        return scanner.nextLine();
    }

    /**
     * Muestra el menú principal del cajero automático.
     *
     * @param titular El nombre del titular de la cuenta para personalizar el mensaje
     */

    public void mostrarMenuPrincipal(String titular){
        System.out.println("======================================");
        System.out.println("Bienvenido usuario: " + titular);
        System.out.println("======================================");
        System.out.println("1.- Consultar Saldo");
        System.out.println("2.- Retirar");
        System.out.println("3.- Depositar");
        System.out.println("4.- Transferir");
        System.out.println("5.- Cambiar PIN");
        //definir las opciones faltantes
        System.out.println("9.- Salir");
    }

    /**
     * Lee la opción seleccionada por el usuario desde el teclado.
     *
     * @return La opción numérica seleccionada o -1 si la entrada no es válida
     */

    public int leerOpcion(){
        try{
            return Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    /**
     * Muestra el saldo actual de la cuenta del usuario.
     *
     * @param saldo El saldo a mostrar
     */

    public void mostrarSaldo(double saldo){
        System.out.println("======================================");
        System.out.println("Tu saldo es $:" + saldo);
        System.out.println("======================================");
    }

    /**
     * Solicita al usuario que ingrese una cantidad para una operación específica.
     *
     * @param operacion El nombre de la operación (retirar, depositar, transferir)
     * @return La cantidad ingresada o -1 si la entrada no es válida
     */

    public double solicitarCantidad(String operacion){
        System.out.println("Ingresa la antidad a " + operacion + ": ");
        try {
            return Double.parseDouble(scanner.nextLine());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    /**
     * Muestra un mensaje genérico al usuario.
     *
     * @param mensaje El mensaje a mostrar
     */

    public void mostrarMensaje(String mensaje){
        System.out.println("==== "+mensaje);
    }
    //Personalizar mensajes de error y de exito
    //metodo para salir cerrar el scanner

    /**
     * Solicita al usuario el número de cuenta destino para una transferencia.
     *
     * @return El número de cuenta destino ingresado por el usuario
     */

    public String solicitarCuentaDestino(){
        System.out.print("Cuenta destino: ");
        return scanner.nextLine();
    }

    /**
     * Solicita al usuario que ingrese su PIN actual para validar una operación.
     *
     * @return El PIN actual ingresado por el usuario
     */

    public String solicitarPinActual(){
        System.out.print("PIN actual: ");
        return scanner.nextLine();
    }

    /**
     * Solicita al usuario que ingrese un nuevo PIN para cambiarlo.
     *
     * @return El nuevo PIN ingresado por el usuario
     */

    public String solicitarNuevoPin(){
        System.out.print("Nuevo PIN: ");
        return scanner.nextLine();
    }

    /**
     * Muestra un mensaje de error al usuario.
     *
     * @param error El mensaje de error a mostrar
     */

    public void mostrarError(String error){
        System.out.println("Error: " + error);
    }

    /**
     * Cierra el scanner para liberar recursos del sistema.
     * Debe llamarse cuando finaliza el uso del cajero.
     */

    public void cerrarScanner(){
        scanner.close();
    }
}
