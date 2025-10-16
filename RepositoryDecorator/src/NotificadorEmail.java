public class NotificadorEmail extends Notificador {
    private String email;

    public NotificadorEmail(String email) {
        this.email = email;
    }

    @Override
    public void enviar(String mensaje) {
        System.out.println("Enviando EMAIL a " + email + ": " + mensaje);
    }

    @Override
    public String getTipo() {
        return "Email";
    }
}