import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private Socket socket            = null;
    private Scanner input   = null;
    public ClientThread clientThread=null;
    public Packet packet;
public Client(String IP,int port) throws Exception{
    try
    {
        socket = new Socket(IP, port);
        System.out.println("Connected");
        clientThread=new ClientThread(socket,this);
      clientThread.start();
        input  =new Scanner(System.in);

    } catch(IOException u)
    {
        System.out.println(u.toString());
    }

    String line = "";
     packet=new Packet("");


    while (packet.isOpen())
    {
            assert input != null;
            line=input.nextLine();

            packet=new Packet(line);
        if(line.equals("quit")){
            packet.setOpen(false);
        }
            clientThread.request(packet);


    }
    assert input != null;
    input.close();


}

public static void main(String [] args) throws Exception {
    new Client("localhost",5000);
}

}
