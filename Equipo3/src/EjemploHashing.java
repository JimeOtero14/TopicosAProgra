import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class EjemploHashing {
    public static void main(String[] args) throws Exception {
        String password = "miClaveSegura";

        // Hashear la contraseña
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashGuardado = md.digest(password.getBytes());

        // Usuario intenta autenticarse
        String intento = "miClave"; // contraseña ingresada
        byte[] hashIntento = md.digest(intento.getBytes());

        System.out.println("hash intento: " + Base64.getEncoder().encodeToString(hashIntento));
        System.out.println("hash guardado: " + Base64.getEncoder().encodeToString(hashGuardado));


        // Comparar hashes
        if (Arrays.equals(hashGuardado, hashIntento)) {
            System.out.println("Contraseña correcta");
        } else {
            System.out.println("Contraseña incorrecta");
        }
    }
}