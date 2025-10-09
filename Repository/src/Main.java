import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Crear el repositorio y servicio
        CocheRepository repositorio = new CocheRepositoryMemoria();
        CocheService servicio = new CocheService(repositorio);

        // Crear algunos coches de ejemplo
        Coche coche1 = new Coche(null, "Toyota", "Corolla", 2022, 25000.0, "Rojo");
        Coche coche2 = new Coche(null, "Honda", "Civic", 2023, 27000.0, "Azul");
        Coche coche3 = new Coche(null, "Nissan", "350Z", 2021, 32000.0, "Blanco");
        Coche coche4 = new Coche(null, "Ford", "Mustang", 2020, 45000.0, "Negro");

        // Guardar coches
        servicio.crearCoche(coche1);
        servicio.crearCoche(coche2);
        servicio.crearCoche(coche3);
        servicio.crearCoche(coche4);

        System.out.println("=== TODOS LOS COCHES ===");
        List<Coche> todos = servicio.obtenerTodosLosCoches();
        todos.forEach(System.out::println);

        System.out.println("\n=== COCHES TOYOTA ===");
        List<Coche> toyotas = servicio.buscarCochesPorMarca("Toyota");
        toyotas.forEach(System.out::println);

        System.out.println("\n=== COCHES POR DEBAJO DE 30000 ===");
        List<Coche> economicos = servicio.buscarCochesPorPrecioMaximo(30000);
        economicos.forEach(System.out::println);

        System.out.println("\n=== COCHES DEL AÑO 2022 ===");
        List<Coche> del2022 = servicio.buscarCochesPorAño(2022);
        del2022.forEach(System.out::println);

        // Buscar y actualizar un coche
        System.out.println("\n=== ACTUALIZAR COCHE ===");
        String primerId = todos.get(0).getId();
        servicio.obtenerCoche(primerId).ifPresent(coche -> {
            coche.setPrecio(26000.0);
            coche.setColor("Gris");
            servicio.actualizarCoche(coche);
            System.out.println("Coche actualizado: " + coche);
        });

        System.out.println("\n=== DESPUÉS DE ACTUALIZAR ===");
        servicio.obtenerTodosLosCoches().forEach(System.out::println);
    }
}