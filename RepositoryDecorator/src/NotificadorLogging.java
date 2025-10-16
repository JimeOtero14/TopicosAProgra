public class NotificadorLogging  extends NotificadorDecorator {

    public NotificadorLogging(Notificador notificador) {
        super(notificador);
    }

    @Override
    public void enviar(String mensaje) {
        System.out.println("LOG: Iniciando envío de " + getTipo() + " - " + mensaje);
        super.enviar(mensaje);
        System.out.println("LOG: Envío de " + getTipo() + " completado");
    }
}
