public class Tupla<T, U, V> {
    private T primer;
    private U segundo;
    private V tercero;

    public Tupla(T primer, U segundo, V tercero) {
        this.primer = primer;
        this.segundo = segundo;
        this.tercero = tercero;
    }

    // Getters y Setters
    public T getPrimer() { return primer; }
    public U getSegundo() { return segundo; }
    public V getTercero() { return tercero; }

    public void setPrimer(T primer) { this.primer = primer; }
    public void setSegundo(U segundo) { this.segundo = segundo; }
    public void setTercero(V tercero) { this.tercero = tercero; }

    @Override
    public String toString() {
        return "Tupla{" + primer + ", " + segundo + ", " + tercero + "}";
    }
}