import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class ClientThread implements Runnable {
    private Socket socket            = null;
    private Client client;
    private BufferedReader input   = null;
    private ObjectOutputStream out     = null;
    private ObjectInputStream in=null;
    private KeysAndIV keysAndIV=null;
    public ClientThread(Socket socket,Client client){
        this.socket=socket;
        this.client=client;

    }
    public void request(Packet packet) throws IOException {
        out.writeObject(packet);
        out.flush();
    }

    @Override
    public void run() {
        try {
            out    = new ObjectOutputStream(socket.getOutputStream());
            in=new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Packet response=new Packet("");
        try {
             this.keysAndIV=(KeysAndIV)in.readObject();
            System.out.println(new String(keysAndIV.getAESIV()));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        while (true){
            assert in != null;
            try {
                response=(Packet)in.readObject();
                System.out.println(response.getMsg());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }
}
