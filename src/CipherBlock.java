import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class CipherBlock {
    public byte[] DESCBCEncryption(byte [] iv, SecretKey key, byte[] message) throws Exception {
        Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,key,new IvParameterSpec(iv));
         return cipher.doFinal(message);
    }
    public byte[] DESCBCDecryption(byte [] iv, SecretKey key, byte[] message) throws Exception {
        Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,key,new IvParameterSpec(iv));
        return cipher.doFinal(message);
    }
    public byte[] DESOFBEncryption(byte [] iv, SecretKey key, byte[] message) throws Exception {
        Cipher cipher=Cipher.getInstance("DES/OFB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,key,new IvParameterSpec(iv));
        return cipher.doFinal(message);
    }
    public byte[] DESOFBDecryption(byte [] iv, SecretKey key, byte[] message) throws Exception {
        Cipher cipher=Cipher.getInstance("DES/OFB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,key,new IvParameterSpec(iv));
        return cipher.doFinal(message);
    }
    public byte[] AESCBCEncryption(byte [] iv, SecretKey key, byte[] message) throws Exception {
        Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,key,new IvParameterSpec(iv));
        return cipher.doFinal(message);
    }
    public byte[] AESCBCDecryption(byte [] iv, SecretKey key, byte[] message) throws Exception {
        Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,key,new IvParameterSpec(iv));
        return cipher.doFinal(message);
    }
    public byte[] AESOFBEncryption(byte [] iv, SecretKey key, byte[] message) throws Exception {
        Cipher cipher=Cipher.getInstance("AES/OFB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,key,new IvParameterSpec(iv));
        return cipher.doFinal(message);
    }
    public byte[] AESOFBDecryption(byte [] iv, SecretKey key, byte[] message) throws Exception {
        Cipher cipher=Cipher.getInstance("AES/OFB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,key,new IvParameterSpec(iv));
        return cipher.doFinal(message);
    }

   /* public static void main(String[] args) throws  Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        Cipher cipher2 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(256,secureRandom);
        Key secretKey = keyGenerator.generateKey();

        String sa=Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println(sa.length());

        System.out.println(new String(Base64.getDecoder().decode(Base64.getEncoder().encodeToString(secretKey.getEncoded()))));
        System.out.println(new String(secretKey.getEncoded()));
        byte[] iv=new byte[16];
        secureRandom.nextBytes(iv);
        byte[] iv2=new byte[8];
        secureRandom.nextBytes(iv2);
        IvParameterSpec IV=new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, IV);
        cipher2.init(Cipher.ENCRYPT_MODE,secretKey,IV);
        byte[] enc="senin amuga goyim".getBytes(StandardCharsets.UTF_8);
        byte[] cipherText=cipher2.doFinal(enc);
        System.out.println(new String(cipherText));
        System.out.println(new String(cipher.doFinal(cipherText)));
        keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56,secureRandom);
        secretKey = keyGenerator.generateKey();
        sa=Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println(sa.length());
        System.out.println(new String(IV.getIV()));
        System.out.println(new String(iv));


    }*/
}
