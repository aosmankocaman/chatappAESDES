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
    private anapanel mainPanel;
    private Packet response;
    private CipherBlock cipherBlock;
    public ClientThread(Socket socket,Client client,anapanel mainPanel){
        this.socket=socket;
        this.client=client;
        this.mainPanel=mainPanel;


    }
    public void request(Packet packet) {
        try {
            System.out.println(packet.isOpen());
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
             this.cipherBlock=new CipherBlock(keysAndIV.getDESIV(),this.keysAndIV.getAESIV(),this.keysAndIV.getDESKey(),this.keysAndIV.getAESKey());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        response=client.packet;
        while (response.isOpen()){
            assert in != null;
            try {

                response = (Packet) in.readObject();
                if(!response.isOpen())
                    break;

                mainPanel.msgArea.insert("\n"+response.getName(),mainPanel.msgArea.getText().length());
               // if(response.isOpen()) {
                   // System.out.println(response.getName());
                //}
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        close();


    }
    public CipherBlock getCipherBlock(){
        return this.cipherBlock;
    }
    public void close(){
        System.out.println("sase");
        try{
            in.close();
            out.close();
            socket.close();
            client.socket=null;
           //System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
