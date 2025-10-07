import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

public class CifradoAsimetrico {
    public static void main(String[] args) throws Exception {
        String mensaje = "Prueba asimetrica";

        // Generar par de claves
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair keyPair = kpg.generateKeyPair();

        // Cifrar con la clave pública
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] cifrado = cipher.doFinal(mensaje.getBytes());

        // Descifrar con la clave privada
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        byte[] descifrado = cipher.doFinal(cifrado);

        System.out.println("Cifrado (Base64): " + Base64.getEncoder().encodeToString(cifrado));
        System.out.println("Descifrado: " + new String(descifrado));
    }
}