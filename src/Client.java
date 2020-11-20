
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;


public class Client extends Thread{
    public Socket socket            = null;
 //   private ClientThread clientThread=null;
    public Packet packet=null;
    public String name=null;
    public Gui mainPanel=null;
    private ObjectOutputStream out     = null;
    private ObjectInputStream in=null;
    private KeysAndIV keysAndIV=null;
    private CipherBlock cipherBlock;

    public Client(Gui mainPanel,String name)  {
        this.mainPanel=mainPanel;
        this.name=name;
        packet=new Packet(name,true);
        try {
            String IP = "localhost";
            int port = 5000;
            socket=new Socket(IP, port);

        }catch (Exception e){
            e.printStackTrace();
        }
        this.start();
    }


    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
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

        while (true){

            try {

                if(!packet.isOpen())
                    break;
                packet = (Packet) in.readObject();
                if(packet.isOpen()){
                    mainPanel.addTextToMsgArea(new String(packet.getText()));
                    mainPanel.addTextToMsgArea(packet.getUserName()+" > "+new String(cipherBlock.decryption(packet,packet.getText())));
                }
                //mainPanel.msgArea.insert("\n"+response.getName(),mainPanel.msgArea.getText().length());
                // if(response.isOpen()) {
                // System.out.println(response.getName());
                //}
            }catch (  IOException | ClassNotFoundException e){
               break;
            }
        }
        close();



    }
    public void request(Packet packet) {
        try {
            this.packet.setText(packet.getText());
            this.packet.setMethod(packet.getMethod());
            this.packet.setMode(packet.getMode());
            this.packet.setOpen(packet.isOpen());
            this.packet.setUserName(packet.getUserName());
            out.writeObject(this.packet);
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public CipherBlock getCipherBlock(){
        return this.cipherBlock;
    }
    public void closeConnection(){
        packet.setOpen(false);
        this.request(this.packet);

    }
    public void close(){
        System.out.println("close");
        try{
            in.close();
            out.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
