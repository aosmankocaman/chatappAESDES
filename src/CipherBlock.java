import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CipherBlock {
    private byte[] desIV;
    private byte[] aesIV;
    private SecretKey aesKey;
    private SecretKey desKey;
    public CipherBlock(byte[] desIV,byte[] aesIV, SecretKey desKey, SecretKey aesKey){
        this.desIV=desIV;
        this.aesIV=aesIV;
        this.desKey=desKey;
        this.aesKey=aesKey;

    }
    public  byte[] encryption(Packet packet,byte [] message){
        try{
            switch (packet.getMethod()){
                case "AES":switch (packet.getMode()){
                    case "CBC":
                        return  base64Encoder(AESCBCEncryption(message));
                    case "OFB":
                        return  base64Encoder(AESOFBEncryption(message));

                }
                case "DES":switch (packet.getMode()){
                    case "CBC":
                        return  base64Encoder(DESCBCEncryption(message));
                    case "OFB":
                        return  base64Encoder(DESOFBEncryption(message));

                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
        public byte[] decryption(Packet packet,byte [] message){
            try{
                switch (packet.getMethod()){
                    case "AES":switch (packet.getMode()){
                        case "CBC":
                            return  AESCBCDecryption(base64Decoder(message));
                        case "OFB":
                            return  AESOFBDecryption(base64Decoder(message));
                    }
                    case "DES":switch (packet.getMode()){
                        case "CBC":
                            return  DESCBCDecryption(base64Decoder(message));
                        case "OFB":
                            return  DESOFBDecryption(base64Decoder(message));
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
    }
    public byte[] DESCBCEncryption( byte[] message) throws Exception {
        Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,this.desKey,new IvParameterSpec(this.desIV));
        return cipher.doFinal(message);
    }
    public byte[] DESCBCDecryption( byte[] message) throws Exception {
        Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,this.desKey,new IvParameterSpec(this.desIV));
        return cipher.doFinal(message);
    }
    public byte[] DESOFBEncryption( byte[] message) throws Exception {
        Cipher cipher=Cipher.getInstance("DES/OFB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,this.desKey,new IvParameterSpec(this.desIV));
        return cipher.doFinal(message);
    }
    public byte[] DESOFBDecryption(byte[] message) throws Exception {
        Cipher cipher=Cipher.getInstance("DES/OFB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,this.desKey,new IvParameterSpec(this.desIV));
        return cipher.doFinal(message);
    }
    public byte[] AESCBCEncryption( byte[] message) throws Exception {
        Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,this.aesKey,new IvParameterSpec(this.aesIV));
        return cipher.doFinal(message);
    }
    public byte[] AESCBCDecryption( byte[] message) throws Exception {
        Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,this.aesKey,new IvParameterSpec(this.aesIV));
        return cipher.doFinal(message);
    }
    public byte[] AESOFBEncryption(byte[] message) throws Exception {
        Cipher cipher=Cipher.getInstance("AES/OFB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,this.aesKey,new IvParameterSpec(this.aesIV));
        return cipher.doFinal(message);
    }
    public byte[] AESOFBDecryption(byte[] message) throws Exception {
        Cipher cipher=Cipher.getInstance("AES/OFB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,this.aesKey,new IvParameterSpec(this.aesIV));
        return cipher.doFinal(message);
    }
    public byte[] base64Encoder(byte[] message){
        return Base64.getEncoder().encode(message);
    }
    public byte[] base64Decoder(byte[] message){
        return Base64.getDecoder().decode(message);
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