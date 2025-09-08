public class Main {
    public static void main(String[] args) {
        // Crear tupla con diferentes tipos
        Tupla<String, Integer, Boolean> estudiante =
                new Tupla<>("Ana", 20, true);

        // Acceder a los valores
        String nombre = estudiante.getPrimer();
        int edad = estudiante.getSegundo();
        boolean activo = estudiante.getTercero();

        System.out.println(estudiante);

        // Modificar valores
        estudiante.setTercero(false);
        System.out.println(estudiante);
    }
}