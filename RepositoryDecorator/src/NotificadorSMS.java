public class NotificadorSMS extends Notificador {
    private String telefono;

    public NotificadorSMS(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public void enviar(String mensaje) {
        System.out.println("Enviando SMS a " + telefono + ": " + mensaje);
    }

    @Override
    public String getTipo() {
        return "SMS";
    }
}
