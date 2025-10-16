public class NotificadorUrgente extends NotificadorDecorator {

    public NotificadorUrgente(Notificador notificador) {
        super(notificador);
    }

    @Override
    public void enviar(String mensaje) {
        String mensajeUrgente = "🚨 URGENTE: " + mensaje + " 🚨";
        super.enviar(mensajeUrgente);
    }
}