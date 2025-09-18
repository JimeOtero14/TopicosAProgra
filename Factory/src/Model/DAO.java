package Model;

import java.util.ArrayList;
import java.util.List;

public class DAO<T> {
    private List<T> elementos;

    public DAO() {
        this.elementos = new ArrayList<>();
    }

    // Métodos genéricos para CRUD
    public void agregar(T elemento) {
        elementos.add(elemento);
    }

    public List<T> obtenerTodos() {
        return new ArrayList<>(elementos);
    }

    public T obtenerPorMatricula(String matricula) {
        for (T elemento : elementos) {
            if (elemento instanceof Coche) {
                Coche coche = (Coche) elemento;
                if (coche.getMatricula().equals(matricula)) {
                    return elemento;
                }
            }
        }
        return null;
    }

    public boolean actualizar(String matricula, T nuevoElemento) {
        for (int i = 0; i < elementos.size(); i++) {
            T elemento = elementos.get(i);
            if (elemento instanceof Coche) {
                Coche coche = (Coche) elemento;
                if (coche.getMatricula().equals(matricula)) {
                    elementos.set(i, nuevoElemento);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean eliminar(String matricula) {
        for (int i = 0; i < elementos.size(); i++) {
            T elemento = elementos.get(i);
            if (elemento instanceof Coche) {
                Coche coche = (Coche) elemento;
                if (coche.getMatricula().equals(matricula)) {
                    elementos.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public int tamaño() {
        return elementos.size();
    }

    public List<T> buscarPorMarca(String marca) {
        List<T> resultados = new ArrayList<>();
        for (T elemento : elementos) {
            if (elemento instanceof Coche) {
                Coche coche = (Coche) elemento;
                if (coche.getMarca().equalsIgnoreCase(marca)) {
                    resultados.add(elemento);
                }
            }
        }
        return resultados;
    }
}
