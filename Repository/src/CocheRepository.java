import java.util.List;
import java.util.Optional;

public interface CocheRepository {
    // Operaciones CRUD básicas
    Coche guardar(Coche coche);
    Optional<Coche> buscarPorId(String id);
    List<Coche> buscarTodos();
    boolean eliminar(String id);
    Coche actualizar(Coche coche);

    // Operaciones de búsqueda específicas
    List<Coche> buscarPorMarca(String marca);
    List<Coche> buscarPorAño(int año);
    List<Coche> buscarPorPrecioMenorQue(double precioMaximo);
    List<Coche> buscarPorModelo(String modelo);
}