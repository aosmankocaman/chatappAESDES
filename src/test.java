


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class test {
    private static KeysAndIV createKeysAndIV() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(128,secureRandom);
        SecretKey AESKey = keyGenerator.generateKey();
        keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56,secureRandom);
        SecretKey DESKey=keyGenerator.generateKey();
        byte[] AESIVBytes=new byte[16];
        byte[] DESIVBytes=new byte[8];
        secureRandom.nextBytes(AESIVBytes);
        secureRandom.nextBytes(DESIVBytes);
        return new KeysAndIV(AESKey,DESKey,AESIVBytes,DESIVBytes);
    }
    public static void main(String[] args) throws Exception {
        KeysAndIV keysAndIV=createKeysAndIV();
        CipherBlock cipherBlock=new CipherBlock();
        String message="selamin aleyküm aslan parçası uy heeee";
        byte[] ciphertext=cipherBlock.AESCBCEncryption(keysAndIV.getAESIV(),keysAndIV.getAESKey(),message.getBytes(StandardCharsets.UTF_8));
        ciphertext=Base64.getEncoder().encode(ciphertext);

        System.out.println(new String(ciphertext));
        ciphertext=Base64.getDecoder().decode(ciphertext);
        System.out.println(new String(cipherBlock.AESCBCDecryption(keysAndIV.getAESIV(),keysAndIV.getAESKey(),ciphertext)));
    }
}
