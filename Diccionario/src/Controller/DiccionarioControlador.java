package Controller;

import Model.DiccionarioRepositorio;
import Libreries.ApiCliente;
import Util.Validador;
import java.util.List;

/**
 * Controlador principal que coordina las operaciones del diccionario
 */
public class DiccionarioControlador {
    private final DiccionarioRepositorio repositorio;
    private final ApiCliente apiCliente;

    public DiccionarioControlador(String rutaArchivo) {
        this.repositorio = DiccionarioRepositorio.obtenerInstancia(rutaArchivo);
        this.apiCliente = new ApiCliente();
    }

    /**
     * Busca una palabra en el diccionario
     * @return Mensaje con el resultado de la búsqueda
     */
    public String buscarPalabra(String palabra) {
        if (!Validador.esValida(palabra)) {
            return "Entrada invalida. Solo se permiten letras.";
        }

        String palabraNormalizada = Validador.normalizar(palabra);

        if (repositorio.existe(palabraNormalizada)) {
            return "La palabra '" + palabraNormalizada + "' se encuentra en el diccionario.";
        } else {
            return "La palabra '" + palabraNormalizada + "' no está en el diccionario.";
        }
    }

    /**
     * Guarda una nueva palabra en el diccionario
     * @return Mensaje con el resultado de la operación
     */
    public String guardarPalabra(String palabra) {
        if (!Validador.esValida(palabra)) {
            return "Entrada invalida. Solo se permiten letras.";
        }

        String palabraNormalizada = Validador.normalizar(palabra);

        if (repositorio.existe(palabraNormalizada)) {
            return "La palabra '" + palabraNormalizada + "' ya existe en el diccionario.";
        }

        if (repositorio.agregar(palabraNormalizada)) {
            return "La palabra '" + palabraNormalizada + "' ha sido agregada exitosamente.";
        } else {
            return "Error al agregar la palabra.";
        }
    }

    /**
     * Elimina una palabra del diccionario
     * @return Mensaje con el resultado de la operación
     */
    public String borrarPalabra(String palabra) {
        if (!Validador.esValida(palabra)) {
            return "Entrada invalida. Solo se permiten letras.";
        }

        String palabraNormalizada = Validador.normalizar(palabra);

        if (!repositorio.existe(palabraNormalizada)) {
            return "No se puede borrar, la palabra '" + palabraNormalizada
                    + "' no está en el diccionario.";
        }

        if (repositorio.eliminar(palabraNormalizada)) {
            return "La palabra '" + palabraNormalizada + "' ha sido eliminada exitosamente.";
        } else {
            return "Error al eliminar la palabra.";
        }
    }

    /**
     * Importa palabras desde la API pública
     * @return Mensaje con el resultado de la importación
     */
    public String importarPalabrasDesdeAPI(int cantidad) {
        List<String> palabrasAPI = apiCliente.obtenerPalabrasAleatorias(cantidad);

        if (palabrasAPI.isEmpty()) {
            return "No se pudieron obtener palabras de la API.";
        }

        int agregadas = 0;
        StringBuilder resultado = new StringBuilder();
        resultado.append("Palabras obtenidas de la API:\n");

        for (String palabra : palabrasAPI) {
            if (Validador.esValida(palabra)) {
                String palabraNormalizada = Validador.normalizar(palabra);
                if (repositorio.agregar(palabraNormalizada)) {
                    resultado.append("  + ").append(palabraNormalizada)
                            .append(" (agregada)\n");
                    agregadas++;
                } else {
                    resultado.append("  - ").append(palabraNormalizada)
                            .append(" (ya existe)\n");
                }
            } else {
                resultado.append("  - ").append(palabra)
                        .append(" (inválida)\n");
            }
        }

        resultado.append("\nTotal de palabras nuevas agregadas: ").append(agregadas);
        return resultado.toString();
    }
}