import java.io.Serializable;

public class Packet implements Serializable {

    private String userName;
    private String mode;
    private String method;
    private byte[] text;
    private boolean isOpen;
    public Packet(String userName,boolean isOpen){
        this.userName=userName;
        this.isOpen=true;
        this.method="AES";
        this.mode="CBC";
        text=null;
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public byte[] getText() {
        return text;
    }

    public void setText(byte[] text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}