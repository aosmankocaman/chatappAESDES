import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public Socket socket            = null;
    private Scanner input   = null;
    private ClientThread clientThread=null;
    public Packet packet;
    public String name;
    private String IP;
    private int port;
    public anapanel mainPanel;
public Client(String IP, int port, anapanel mainPanel) throws Exception{

        this.mainPanel=mainPanel;
      this.IP=IP;
      this.port=port;
    this.packet=new Packet("",false);


}
public void  connect()  {
    try {
        socket = new Socket(IP, port);
    } catch (IOException e) {
        e.printStackTrace();
    }
    this.packet.setOpen(true);
    this.packet.setName(this.name);
    clientThread=new ClientThread(socket,this,this.mainPanel);
    clientThread.start();
}
public void disconnect(){
    this.packet.setOpen(false);
    this.close();
}
public ClientThread getClientThread(){
    return this.clientThread;
}
public void send(Packet packet){
    this.packet=packet;
    clientThread.request(this.packet);
}

public void close(){
   this.packet.setOpen(false);
   this.packet.setName("");

   clientThread.request(this.packet);


}


}
