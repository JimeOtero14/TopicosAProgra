/*
*
* Examen Primer Parcial
* Tópicos Avanzados de Programación
*
* @Author Jimena Otero
*
* */

package View;

import Controller.ProductoController;
import Controller.ClienteController;
import Model.Producto;
import Model.Cliente;
import Model.Inventario;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final Inventario<Producto> inventarioProductos = new Inventario<>(100);
    private static final Inventario<Cliente> inventarioClientes = new Inventario<>(50);
    private static final ProductoController productoController = new ProductoController(inventarioProductos);
    private static final ClienteController clienteController = new ClienteController(inventarioClientes);

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n===== SISTEMA DE INVENTARIO =====");
            System.out.println("1. Gestionar Productos");
            System.out.println("2. Gestionar Clientes");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    menuProductos();
                    break;
                case "2":
                    menuClientes();
                    break;
                case "3":
                    salir = true;
                    System.out.println("¡Gracias por usar el sistema!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void menuProductos() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n----- GESTIÓN DE PRODUCTOS -----");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Modificar Producto");
            System.out.println("3. Eliminar Producto");
            System.out.println("4. Listar Productos");
            System.out.println("5. Buscar Producto");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1": agregarProductoView();
                    break;
                case "2": modificarProductoView();
                    break;
                case "3": eliminarProductoView();
                    break;
                case "4": listarProductosView();
                    break;
                case "5": buscarProductoView();
                    break;
                case "6": volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void agregarProductoView() {
        System.out.print("Código: ");
        String codigo = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());
        System.out.print("Cantidad: ");
        int cantidad = Integer.parseInt(scanner.nextLine());
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("Fecha de Vencimiento (DD/MM/YYYY) o N/A: ");
        String fechaVencimiento = scanner.nextLine();
        Producto producto = new Producto(codigo, nombre, precio, cantidad, categoria, fechaVencimiento);

        boolean exito = productoController.agregarProducto(producto);
        System.out.println(exito ? "Producto agregado." : "Error: El código ya existe o límite alcanzado.");
    }

    private static void modificarProductoView() {
        System.out.print("Ingrese el código del producto a modificar: ");
        String codigo = scanner.nextLine();
        Optional<Producto>prodOpt = productoController.buscarPorCodigo(codigo);

        if (!prodOpt.isPresent()) {
            System.out.println("Producto no encontrado.");
            return;
        }

        Producto p = prodOpt.get();
        System.out.print("Nombre [" + p.getNombre() + "]: ");
        String nombre = scanner.nextLine();
        nombre = nombre.isEmpty() ? null : nombre;
        System.out.print("Precio [" + p.getPrecio() + "]: ");
        String precioStr = scanner.nextLine();
        Double precio = precioStr.isEmpty() ? null : Double.parseDouble(precioStr);
        System.out.print("Cantidad [" + p.getCantidad() + "]: ");
        String cantidadStr = scanner.nextLine();
        Integer cantidad = cantidadStr.isEmpty() ? null : Integer.parseInt(cantidadStr);
        System.out.print("Categoría [" + p.getCategoria() + "]: ");
        String categoria = scanner.nextLine();
        categoria = categoria.isEmpty() ? null : categoria;
        System.out.print("Fecha de Vencimiento [" + p.getFechaVencimiento() + "]: ");
        String fechaVencimiento = scanner.nextLine();
        fechaVencimiento = fechaVencimiento.isEmpty() ? null : fechaVencimiento;
        boolean exito = productoController.modificarProducto(codigo, nombre, precio, cantidad, categoria, fechaVencimiento);
        System.out.println(exito ? "Producto modificado." : "No se pudo modificar el producto.");
    }

    private static void eliminarProductoView() {
        System.out.print("Ingrese el código del producto a eliminar: ");
        String codigo = scanner.nextLine();
        boolean exito = productoController.eliminarProducto(codigo);
        System.out.println(exito ? "Producto eliminado." : "Producto no encontrado.");
    }

    private static void listarProductosView() {
        List<Producto> productos = productoController.listarProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }


        System.out.println("Código\tNombre\t\t\tPrecio\tCantidad\tCategoría\tVencimiento");
        System.out.println("-------------------------------------------------------------------------------");
        for (Producto p : productos) {
            System.out.printf("%-7s %-15s %.2f\t%-8d %-10s %s\n",
                    p.getCodigo(), p.getNombre(), p.getPrecio(), p.getCantidad(), p.getCategoria(), p.getFechaVencimiento());
        }
    }

    private static void buscarProductoView() {
        System.out.println("1. Buscar por Código");
        System.out.println("2. Buscar por Nombre");
        System.out.print("Seleccione una opción: ");
        String opcion = scanner.nextLine();
        switch (opcion) {
            case "1":
                System.out.print("Ingrese el código a buscar: ");
                String codigo = scanner.nextLine();
                Optional<Producto> pOpt = productoController.buscarPorCodigo(codigo);
                System.out.println(pOpt.map(Main::detalleProducto).orElse("Producto no encontrado."));
        break;
            case "2":
                System.out.print("Ingrese el nombre a buscar: ");
                String nombre = scanner.nextLine();
                List<Producto> encontrados = productoController.buscarPorNombre(nombre);
                if (encontrados.isEmpty()) System.out.println("No se encontró producto.");
                else encontrados.forEach(p -> System.out.println(detalleProducto(p)));
        break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static String detalleProducto(Producto p) {
        return String.format("\nCódigo: %s\nNombre: %s\nPrecio: %.2f\nCantidad: %d\nCategoría: %s\nFecha Vencimiento: %s",
                p.getCodigo(), p.getNombre(), p.getPrecio(), p.getCantidad(), p.getCategoria(), p.getFechaVencimiento());
    }

    private static void menuClientes() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n----- GESTIÓN DE CLIENTES -----");
            System.out.println("1. Agregar Cliente");
            System.out.println("2. Modificar Cliente");
            System.out.println("3. Eliminar Cliente");
            System.out.println("4. Listar Clientes");
            System.out.println("5. Buscar Cliente");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1": agregarClienteView();
                    break;
                case "2": modificarClienteView();
                    break;
                case "3": eliminarClienteView();
                    break;
                case "4": listarClientesView();
                    break;
                case "5": buscarClienteView();
                    break;
                case "6": volver = true;
                    break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private static void agregarClienteView() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Saldo inicial: ");
        double saldo = Double.parseDouble(scanner.nextLine());
        Cliente cliente = new Cliente(nombre, email, telefono, saldo);
        boolean exito = clienteController.agregarCliente(cliente);
        System.out.println(exito ? "Cliente agregado." : "Error: Email duplicado o límite alcanzado.");
    }

    private static void modificarClienteView() {
        List<Cliente> clientes = clienteController.listarClientes();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.print("Ingrese el número de cliente a modificar (1 - " + clientes.size() + "): ");
        int indice;

        try {
            indice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch(NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }

        if (indice < 0 || indice >= clientes.size()) {
            System.out.println("Número de cliente no válido.");
            return;
        }

        Cliente c = clientes.get(indice);
        System.out.print("Nombre [" + c.getNombre() + "]: ");
        String nombre = scanner.nextLine();
        nombre = nombre.isEmpty() ? null : nombre;
        System.out.print("Email [" + c.getEmail() + "]: ");
        String email = scanner.nextLine();
        email = email.isEmpty() ? null : email;
        System.out.print("Teléfono [" + c.getTelefono() + "]: ");
        String telefono = scanner.nextLine();
        telefono = telefono.isEmpty() ? null : telefono;
        System.out.print("Saldo [" + c.getSaldo() + "]: ");
        String saldoStr = scanner.nextLine();
        Double saldo = saldoStr.isEmpty() ? null : Double.parseDouble(saldoStr);
        boolean exito = clienteController.modificarCliente(indice, nombre, email, telefono, saldo);
        System.out.println(exito ? "Cliente modificado." : "No se pudo modificar el cliente.");
    }

    private static void eliminarClienteView() {
        List<Cliente> clientes = clienteController.listarClientes();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.print("Ingrese el número de cliente a eliminar (1 - " + clientes.size() + "): ");
        int indice;

        try {
            indice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch(NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }

        if (indice < 0 || indice >= clientes.size()) {
            System.out.println("Número de cliente no válido.");
            return;
        }

        boolean exito = clienteController.eliminarCliente(indice);
        System.out.println(exito ? "Cliente eliminado." : "Error al eliminar cliente.");
    }

    private static void listarClientesView() {
        List<Cliente> clientes = clienteController.listarClientes();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.println("ID\tNombre\t\t\tEmail\t\t\t\tTeléfono\t\t\tSaldo");
        System.out.println("------------------------------------------------------------------");
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            System.out.printf("%-3d %-15s %-20s %-12s $%.2f\n", (i+1), c.getNombre(), c.getEmail(), c.getTelefono(), c.getSaldo());
        }
    }

    private static void buscarClienteView() {
        System.out.println("1. Buscar por Nombre");
        System.out.println("2. Buscar por Email");
        System.out.print("Seleccione una opción: ");
        String opcion = scanner.nextLine();
        switch(opcion) {
            case "1":
                System.out.print("Ingrese el nombre a buscar: ");
                String nombre = scanner.nextLine();
                List<Cliente> encontrados = clienteController.buscarPorNombre(nombre);
                if (encontrados.isEmpty()) System.out.println("No se encontraron clientes con ese nombre.");
                else encontrados.forEach(c -> System.out.println(detalleCliente(c)));
        break;
            case "2":
                System.out.print("Ingrese el email a buscar: ");
                String email = scanner.nextLine();
                Optional<Cliente> clienteOpt = clienteController.buscarPorEmail(email);
                System.out.println(clienteOpt.map(Main::detalleCliente).orElse("No se encontró cliente con ese email."));
        break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static String detalleCliente(Cliente c) {
        return String.format("\nNombre: %s\nEmail: %s\nTeléfono: %s\nSaldo: %.2f", c.getNombre(), c.getEmail(), c.getTelefono(), c.getSaldo());
    }
}
