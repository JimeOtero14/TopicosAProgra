import java.util.List;
import java.util.Optional;

public class CocheService {
    private final CocheRepository cocheRepository;

    public CocheService(CocheRepository cocheRepository) {
        this.cocheRepository = cocheRepository;
    }

    public Coche crearCoche(Coche coche) {
        validarCoche(coche);
        return cocheRepository.guardar(coche);
    }

    public Optional<Coche> obtenerCoche(String id) {
        return cocheRepository.buscarPorId(id);
    }

    public List<Coche> obtenerTodosLosCoches() {
        return cocheRepository.buscarTodos();
    }

    public Coche actualizarCoche(Coche coche) {
        validarCoche(coche);
        if (!cocheRepository.buscarPorId(coche.getId()).isPresent()) {
            throw new IllegalArgumentException("Coche con ID " + coche.getId() + " no encontrado");
        }
        return cocheRepository.actualizar(coche);
    }

    public boolean eliminarCoche(String id) {
        return cocheRepository.eliminar(id);
    }

    public List<Coche> buscarCochesPorMarca(String marca) {
        return cocheRepository.buscarPorMarca(marca);
    }

    public List<Coche> buscarCochesPorAño(int año) {
        return cocheRepository.buscarPorAño(año);
    }

    public List<Coche> buscarCochesPorPrecioMaximo(double precioMaximo) {
        return cocheRepository.buscarPorPrecioMenorQue(precioMaximo);
    }

    private void validarCoche(Coche coche) {
        if (coche.getMarca() == null || coche.getMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("La marca del coche es obligatoria");
        }
        if (coche.getModelo() == null || coche.getModelo().trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo del coche es obligatorio");
        }
        if (coche.getAño() < 1900 || coche.getAño() > 2030) {
            throw new IllegalArgumentException("El año del coche no es válido");
        }
        if (coche.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio del coche debe ser mayor a 0");
        }
    }
}