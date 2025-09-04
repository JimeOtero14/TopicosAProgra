public class Main2 {
    public static void main(String[] args) {
        Suma suma = new  Suma();
        System.out.println(suma.ejecutar(10, 5));
    }
}

interface Operacion<T>{
    T ejecutar (T a, T b);
}

class Suma implements Operacion<Integer>{
    public Integer ejecutar(Integer a, Integer b){
        return a + b;
    }
}