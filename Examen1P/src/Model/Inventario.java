package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Inventario<T> {
    private List<T> items;
    private int maxCapacity;

    public Inventario(int maxCapacity) {
        this.items = new ArrayList<>();
        this.maxCapacity = maxCapacity;
    }

    public boolean agregar(T item) {
        if (items.size() < maxCapacity) {
            items.add(item);
            return true;
        }
        return false;
    }

    public boolean eliminar(T item) {
        return items.remove(item);
    }

    public List<T> listar() {
        return new ArrayList<>(items);
    }
    
    public Optional<T> buscar(java.util.function.Predicate<T> criterio) {
        return items.stream().filter(criterio).findFirst();
    }
    
    public List<T> buscarTodos(java.util.function.Predicate<T> criterio) {
        List<T> resultados = new ArrayList<>();
        for (T item : items) {
            if (criterio.test(item)) {
                resultados.add(item);
            }
        }
        return resultados;
    }
}
