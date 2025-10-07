import java.security.MessageDigest;
import java.util.Base64;

public class Hashing {
    public static void main(String[] args) throws Exception {
        String texto = "cifrado hash";

        // Hashear el texto
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(texto.getBytes());

        System.out.println("SHA-256 Hash: " + Base64.getEncoder().encodeToString(hash));
    }
}