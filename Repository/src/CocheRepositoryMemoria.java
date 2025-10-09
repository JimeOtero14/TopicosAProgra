import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CocheRepositoryMemoria implements CocheRepository {
    private final Map<String, Coche> almacenamiento = new ConcurrentHashMap<>();

    @Override
    public Coche guardar(Coche coche) {
        if (coche.getId() == null || coche.getId().trim().isEmpty()) {
            coche.setId(UUID.randomUUID().toString());
        }
        almacenamiento.put(coche.getId(), coche);
        return coche;
    }

    @Override
    public Optional<Coche> buscarPorId(String id) {
        return Optional.ofNullable(almacenamiento.get(id));
    }

    @Override
    public List<Coche> buscarTodos() {
        return new ArrayList<>(almacenamiento.values());
    }

    @Override
    public boolean eliminar(String id) {
        return almacenamiento.remove(id) != null;
    }

    @Override
    public Coche actualizar(Coche coche) {
        if (coche.getId() == null || !almacenamiento.containsKey(coche.getId())) {
            throw new IllegalArgumentException("Coche no encontrado para actualizar");
        }
        almacenamiento.put(coche.getId(), coche);
        return coche;
    }

    @Override
    public List<Coche> buscarPorMarca(String marca) {
        return almacenamiento.values().stream()
                .filter(coche -> coche.getMarca().equalsIgnoreCase(marca))
                .collect(Collectors.toList());
    }

    @Override
    public List<Coche> buscarPorAño(int año) {
        return almacenamiento.values().stream()
                .filter(coche -> coche.getAño() == año)
                .collect(Collectors.toList());
    }

    @Override
    public List<Coche> buscarPorPrecioMenorQue(double precioMaximo) {
        return almacenamiento.values().stream()
                .filter(coche -> coche.getPrecio() <= precioMaximo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Coche> buscarPorModelo(String modelo) {
        return almacenamiento.values().stream()
                .filter(coche -> coche.getModelo().toLowerCase().contains(modelo.toLowerCase()))
                .collect(Collectors.toList());
    }
}