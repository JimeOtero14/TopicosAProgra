public class NotificadorCifrado extends NotificadorDecorator {

    public NotificadorCifrado(Notificador notificador) {
        super(notificador);
    }

    @Override
    public void enviar(String mensaje) {
        String mensajeCifrado = cifrarMensaje(mensaje);
        super.enviar(mensajeCifrado);
    }

    private String cifrarMensaje(String mensaje) {
        // Cifrado simple para demostración
        return new StringBuilder(mensaje).reverse().toString();
    }
}
