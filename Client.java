import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private Socket socket            = null;
    private Scanner input   = null;
    private ClientThread clientThread=null;
public Client(String IP,int port) throws Exception{
    try
    {
        socket = new Socket(IP, port);
        System.out.println("Connected");
        clientThread=new ClientThread(socket,this);
        Thread t=new Thread(clientThread);
        t.start();
        // takes input from terminal
        input  =new Scanner(System.in);

    } catch(IOException u)
    {
        System.out.println(u.toString());
    }
    // string to read message from input
    String line = "";
    Packet packet=new Packet("");

    // keep reading until "Over" is input
    while (!packet.getMsg().equals("Over"))
    {
            assert input != null;
            line=input.nextLine();

            packet=new Packet(line);
            clientThread.request(packet);
    }
    try
    {
        input.close();
        socket.close();
    }
    catch(IOException i)
    {
        System.out.println(i.toString());
    }

}
    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost", 5000);
    }

}
