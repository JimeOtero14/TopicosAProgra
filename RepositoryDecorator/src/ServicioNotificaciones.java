import java.util.ArrayList;
import java.util.List;

public class ServicioNotificaciones{
    private UsuarioRepository usuarioRepository;

    public ServicioNotificaciones(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void enviarNotificacion(String usuarioId, String mensaje) {
        usuarioRepository.buscarPorId(usuarioId).ifPresent(usuario -> {
            List<Notificador> notificadores = crearNotificadores(usuario, mensaje, false);
            notificadores.forEach(notificador -> notificador.enviar(mensaje));
        });
    }

    public void enviarNotificacionUrgente(String usuarioId, String mensaje) {
        usuarioRepository.buscarPorId(usuarioId).ifPresent(usuario -> {
            List<Notificador> notificadores = crearNotificadores(usuario, mensaje, true);
            notificadores.forEach(notificador -> notificador.enviar(mensaje));
        });
    }

    public void enviarNotificacionConCifrado(String usuarioId, String mensaje) {
        usuarioRepository.buscarPorId(usuarioId).ifPresent(usuario -> {
            List<Notificador> notificadores = crearNotificadoresConCifrado(usuario, mensaje, false);
            notificadores.forEach(notificador -> notificador.enviar(mensaje));
        });
    }

    private List<Notificador> crearNotificadores(Usuario usuario, String mensaje, boolean urgente) {
        List<Notificador> notificadores = new ArrayList<>();

        if (usuario.isNotificacionesEmail()) {
            Notificador email = new NotificadorEmail(usuario.getEmail());
            email = new NotificadorLogging(email);
            if (urgente) {
                email = new NotificadorUrgente(email);
            }
            notificadores.add(email);
        }

        if (usuario.isNotificacionesSMS()) {
            Notificador sms = new NotificadorSMS(usuario.getTelefono());
            sms = new NotificadorLogging(sms);
            if (urgente) {
                sms = new NotificadorUrgente(sms);
            }
            notificadores.add(sms);
        }

        if (usuario.isNotificacionesPush()) {
            Notificador push = new NotificadorPush(usuario.getId());
            push = new NotificadorLogging(push);
            if (urgente) {
                push = new NotificadorUrgente(push);
            }
            notificadores.add(push);
        }

        return notificadores;
    }

    private List<Notificador> crearNotificadoresConCifrado(Usuario usuario, String mensaje, boolean urgente) {
        List<Notificador> notificadores = new ArrayList<>();

        if (usuario.isNotificacionesEmail()) {
            Notificador email = new NotificadorEmail(usuario.getEmail());
            email = new NotificadorLogging(email);
            email = new NotificadorCifrado(email);
            if (urgente) {
                email = new NotificadorUrgente(email);
            }
            notificadores.add(email);
        }

        if (usuario.isNotificacionesSMS()) {
            Notificador sms = new NotificadorSMS(usuario.getTelefono());
            sms = new NotificadorLogging(sms);
            sms = new NotificadorCifrado(sms);
            if (urgente) {
                sms = new NotificadorUrgente(sms);
            }
            notificadores.add(sms);
        }

        return notificadores;
    }
}
