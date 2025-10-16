import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class UsuarioMemoria extends UsuarioRepository {
        private final Map<String, Usuario> almacenamiento = new ConcurrentHashMap<>();

        @Override
        public Usuario guardar(Usuario usuario) {
            if (usuario.getId() == null || usuario.getId().trim().isEmpty()) {
                usuario = new Usuario(UUID.randomUUID().toString(),
                        usuario.getNombre(),
                        usuario.getEmail(),
                        usuario.getTelefono());
            }
            almacenamiento.put(usuario.getId(), usuario);
            return usuario;
        }

        @Override
        public Optional<Usuario> buscarPorId(String id) {
            return Optional.ofNullable(almacenamiento.get(id));
        }

        @Override
        public List<Usuario> buscarTodos() {
            return new ArrayList<>(almacenamiento.values());
        }

        @Override
        public boolean eliminar(String id) {
            return almacenamiento.remove(id) != null;
        }

        @Override
        public Usuario actualizar(Usuario usuario) {
            if (usuario.getId() == null || !almacenamiento.containsKey(usuario.getId())) {
                throw new IllegalArgumentException("Usuario no encontrado para actualizar");
            }
            almacenamiento.put(usuario.getId(), usuario);
            return usuario;
        }

        @Override
        public Optional<Usuario> buscarPorEmail(String email) {
            return almacenamiento.values().stream()
                    .filter(usuario -> usuario.getEmail().equalsIgnoreCase(email))
                    .findFirst();
        }
    }
