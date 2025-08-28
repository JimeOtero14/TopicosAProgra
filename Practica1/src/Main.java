public class Main {
    public static void main(String[] args) {
        Usuario[] usuarios = {
                new Usuario("1234", "Juan", 1000.0),
                new Usuario("5678", "Maria", 2500.0)
        };
        Cajero cajero = new Cajero(usuarios);
        cajero.iniciarSesion();
    }
}