public class NotificadorPush extends Notificador {
    private String usuarioId;

    public NotificadorPush(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public void enviar(String mensaje) {
        System.out.println("Enviando PUSH a " + usuarioId + ": " + mensaje);
    }

    @Override
    public String getTipo() {
        return "Push";
    }
}
