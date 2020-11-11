import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread implements Runnable {
    private Socket socket;
    private Server server;
    private ObjectOutputStream dout;
    private ObjectInputStream din;
    private Packet packet;
    public ServerThread(Socket socket, Server server){
        this.socket=socket;
        this.server=server;
        packet=new Packet("");
    }
    public void sendPacket(Packet packet) throws IOException {
            dout.writeObject(packet);
            dout.flush();
    }
    public void sendAll(Packet packet) throws IOException {
        for(int i=0;i<this.server.clients.size();i++){
            this.server.clients.get(i).sendPacket(packet);
        }
    }
    @Override
    public void run()  {
        try {
            dout=new ObjectOutputStream(socket.getOutputStream());
            din=new ObjectInputStream(socket.getInputStream());
            while (true){
                packet= (Packet) din.readObject();
                sendAll(packet);
            }
        }catch (Exception e){
            e.printStackTrace();
        };


    }
}
