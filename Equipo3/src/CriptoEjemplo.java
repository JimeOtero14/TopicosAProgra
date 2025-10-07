import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class CriptoEjemplo {
    public static void main(String[] args) throws Exception {
        // 1. Generar clave AES
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey clave = keyGen.generateKey();

        // 2. Crear cifrador
        Cipher cipher = Cipher.getInstance("AES");

        // 3. Encriptar
        cipher.init(Cipher.ENCRYPT_MODE, clave);
        String texto = "Hola como estas";
        byte[] cifrado = cipher.doFinal(texto.getBytes());
        String cifradoBase64 = Base64.getEncoder().encodeToString(cifrado);
        System.out.println("Texto cifrado: " + cifradoBase64);

        // 4. Desencriptar
        cipher.init(Cipher.DECRYPT_MODE, clave);
        byte[] descifrado = cipher.doFinal(Base64.getDecoder().decode(cifradoBase64));
        System.out.println("Texto original: " + new String(descifrado));
    }
}