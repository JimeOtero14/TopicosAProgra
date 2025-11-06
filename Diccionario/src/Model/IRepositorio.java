package Model;

import java.util.List;

/**
 * Interface genérica para operaciones CRUD en repositorios
 * Utiliza Generics, Repository y Strategy
 * @param <T> Tipo de dato a manejar
 */
public interface IRepositorio<T> {
    boolean existe(T elemento);
    boolean agregar(T elemento);
    boolean eliminar(T elemento);
    List<T> obtenerTodos();
}