package Controller;

import Model.Producto;
import Model.Inventario;

import java.util.List;
import java.util.Optional;

public class ProductoController {
    private Inventario<Producto> inventario;

    public ProductoController(Inventario<Producto> inventario) {
        this.inventario = inventario;
    }

    public boolean agregarProducto(Producto producto) {
        Optional<Producto> existente = buscarPorCodigo(producto.getCodigo());
        if (existente.isPresent()) {
            return false;
        }
        return inventario.agregar(producto);
    }

    public boolean modificarProducto(String codigo, String nuevoNombre, Double nuevoPrecio, Integer nuevaCantidad, String nuevaCategoria, String nuevaFechaVencimiento) {
        Optional<Producto> opt = buscarPorCodigo(codigo);
        if (!opt.isPresent()) {
            return false;
        }
        Producto p = opt.get();
        if (nuevoNombre != null) p.setNombre(nuevoNombre);
        if (nuevoPrecio != null) p.setPrecio(nuevoPrecio);
        if (nuevaCantidad != null) p.setCantidad(nuevaCantidad);
        if (nuevaCategoria != null) p.setCategoria(nuevaCategoria);
        if (nuevaFechaVencimiento != null) p.setFechaVencimiento(nuevaFechaVencimiento);
        return true;
    }

    public boolean eliminarProducto(String codigo) {
        Optional<Producto> opt = buscarPorCodigo(codigo);
        return opt.map(producto -> inventario.eliminar(producto)).orElse(false);
    }

    public List<Producto> listarProductos() {
        return inventario.listar();
    }

    public Optional<Producto> buscarPorCodigo(String codigo) {
        return inventario.buscar(p -> p.getCodigo().equals(codigo));
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return inventario.buscarTodos(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()));
    }
}
