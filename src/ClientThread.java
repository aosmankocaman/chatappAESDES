import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class ClientThread extends Thread {
    private Socket socket            = null;
    private Client client;
    private BufferedReader input   = null;
    private ObjectOutputStream out     = null;
    private ObjectInputStream in=null;
    private KeysAndIV keysAndIV=null;
    private anapanel _anapanel;
    private Packet response;
    public ClientThread(Socket socket,Client client){
        this.socket=socket;
        this.client=client;


    }
    public void request(Packet packet) {
        try {
            out.writeObject(packet);
            out.flush();
        }catch (Exception e){
            close();
        }

    }

    @Override
    public void run() {
        try {
            out    = new ObjectOutputStream(socket.getOutputStream());
            in=new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
             this.keysAndIV=(KeysAndIV)in.readObject();
            System.out.println(new String(keysAndIV.getAESIV()));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        while (client.packet.isOpen()){
            assert in != null;
            try {

                response = (Packet) in.readObject();
                if(response.isOpen()) {
                    System.out.println(response.getMsg());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        close();


    }
    public void close(){
        try{
            in.close();
            out.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
