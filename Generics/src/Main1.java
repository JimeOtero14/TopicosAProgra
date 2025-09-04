public class Main1{
    public static void main(String[] args) {
        Utilidad.imprimir("Texto Generico");
        Utilidad.imprimir(123);
        Utilidad.imprimir(3.14);
    }
}

class Utilidad {
    public static <T> void  imprimir(T elemento){
        System.out.println(elemento);
    }
}