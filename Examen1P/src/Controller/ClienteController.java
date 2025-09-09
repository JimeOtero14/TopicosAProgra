package Controller;

import Model.Cliente;
import Model.Inventario;

import java.util.List;
import java.util.Optional;

public class ClienteController {
    private Inventario<Cliente> inventarioClientes;

    public ClienteController(Inventario<Cliente> inventarioClientes) {
        this.inventarioClientes = inventarioClientes;
    }

    public boolean agregarCliente(Cliente cliente) {
        Optional<Cliente> existente = buscarPorEmail(cliente.getEmail());
        if (existente.isPresent()) {
            return false;
        }
        return inventarioClientes.agregar(cliente);
    }

    public boolean modificarCliente(int indice, String nombre, String email, String telefono, Double saldo) {
        List<Cliente> clientes = inventarioClientes.listar();
        if (indice < 0 || indice >= clientes.size()) {
            return false;
        }

        Cliente c = clientes.get(indice);
        if (nombre != null) c.setNombre(nombre);
        if (email != null) c.setEmail(email);
        if (telefono != null) c.setTelefono(telefono);
        if (saldo != null) c.setSaldo(saldo);
        return true;
    }

    public boolean eliminarCliente(int indice) {
        List<Cliente> clientes = inventarioClientes.listar();
        if (indice < 0 || indice >= clientes.size()) {
            return false;
        }

        return inventarioClientes.eliminar(clientes.get(indice));
    }

    public List<Cliente> listarClientes() {
        return inventarioClientes.listar();
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        return inventarioClientes.buscar(c -> c.getEmail().equalsIgnoreCase(email));
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        return inventarioClientes.buscarTodos(c -> c.getNombre().toLowerCase().contains(nombre.toLowerCase()));
    }

}
