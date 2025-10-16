public class Estudiante {
    private static volatile Estudiante instancia;

    private int id;
    private String nombre;
    private String correo;

    //Se le implemento patrón Singleton double-checked locking para hacer la inicialización más temprana, este metodo es bueno
    //cuando se tiene el multi-hilo (no es el caso pero es el más conveniente usar debido al DAO)

    // Constructor privado
    private Estudiante() {}

    Estudiante(int id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
    }

    // Metodo con double-checked locking para mejor performance
    public static Estudiante getInstancia() {
        if (instancia == null) {
            synchronized (Estudiante.class) {
                if (instancia == null) {
                    instancia = new Estudiante();
                }
            }
        }
        return instancia;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
