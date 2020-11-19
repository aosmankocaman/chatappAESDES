import javax.crypto.SecretKey;
import java.io.Serializable;

public class KeysAndIV implements Serializable {

    private byte[] AESIV;
    private SecretKey AESKey;
    private byte[] DESIV;
    private SecretKey DESKey;

    public KeysAndIV( SecretKey AESKey, SecretKey DESKey, byte[] AESIV, byte[] DESIV){
        this.DESKey=DESKey;
        this.AESIV=AESIV;
        this.AESKey=AESKey;
        this.DESIV=DESIV;
    }



    public SecretKey getAESKey() {
        return AESKey;
    }

    public void setAESKey(SecretKey AESKey) {
        this.AESKey = AESKey;
    }


    public SecretKey getDESKey() {
        return DESKey;
    }

    public void setDESKey(SecretKey DESKey) {
        this.DESKey = DESKey;
    }

    public byte[] getAESIV() {
        return AESIV;
    }

    public void setAESIV(byte[] AESIV) {
        this.AESIV = AESIV;
    }

    public byte[] getDESIV() {
        return DESIV;
    }

    public void setDESIV(byte[] DESIV) {
        this.DESIV = DESIV;
    }
}