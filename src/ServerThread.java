import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;
    private Server server;
    private ObjectOutputStream dout;
    private ObjectInputStream din;
    private Packet packet;

    public ServerThread(Socket socket, Server server ){
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
    public void sendKeysAndIV(KeysAndIV packet) throws IOException{
        dout.writeObject(packet);
        dout.flush();

    }
    @Override
    public void run()  {
        try {
            dout=new ObjectOutputStream(socket.getOutputStream());
            din=new ObjectInputStream(socket.getInputStream());
            sendKeysAndIV(server.keysAndIV);
            System.out.println(new String(server.keysAndIV.getAESIV()));
            while (packet.isOpen()){
                packet= (Packet) din.readObject();
                sendAll(packet);
            }
          close();
        }catch (Exception e){
            e.printStackTrace();
        };
    }
    public void close() throws Exception{
        for(int i=0;i<server.clients.size();i++){
            if( this.equals(server.clients.get(i))){
                server.clients.remove(i);
                break;
            }
        }

        din.close();
        dout.close();
        socket.close();

    }
}
