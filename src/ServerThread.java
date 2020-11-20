
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket=null;
    private Server server=null;
    private ObjectOutputStream out=null;
    private ObjectInputStream in=null;

    ServerThread(Socket socket, Server server){
        this.socket=socket;
        this.server=server;
        //packet=new Packet("server",true);
    }
    @Override
    public void run()  {
        try {
            out=new ObjectOutputStream(socket.getOutputStream());
            in=new ObjectInputStream(socket.getInputStream());
            sendKeysAndIV(server.keysAndIV);

            while (true){
                Packet packet = (Packet) in.readObject();

                if(!packet.isOpen()){
                    sendPacket(packet);
                    break;
                }
                server.writeLogFile(packet);
                sendAll(packet);
            }
            close();
        }catch (Exception e){
            e.printStackTrace();
        };
    }
    public void close() throws Exception{

        server.clients.remove(this);
        in.close();
        out.close();
        socket.close();
    }
    public void sendKeysAndIV(KeysAndIV packet) throws IOException{
        out.writeObject(packet);
        //dout.flush();
    }
    public void sendPacket(Packet packet) throws IOException {
        out.writeObject(packet);
        //dout.flush();
    }
    public void sendAll(Packet packet) throws IOException {
        for(int i=0;i<this.server.clients.size();i++){
            this.server.clients.get(i).sendPacket(packet);
        }
    }

}
