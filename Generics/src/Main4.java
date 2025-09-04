import java.util.ArrayList;
import java.util.List;

public class Main4 {
    public static void main(String[] args) {
        List<Integer> numeros = List.of(1, 2, 3);
        List<String> palabras = List.of ("Java", "Genericos");
    }
}

class Util{
    public static void imprimirLista(List<?> lista){
        for(Object elemento : lista){
            System.out.println(elemento);
        }
    }
}
