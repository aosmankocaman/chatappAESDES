import java.io.Serializable;

public class Packet implements Serializable {
    private String msg;
    public Packet(String msg){
        this.msg=msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setMsg(String msg){
        this.msg=msg;
    }
}
