// A Java program for a Server
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server
{
    //initialize socket and input stream
    private Socket		 socket   = null;
    private ServerSocket server   = null;
    private ServerThread client;
    public ArrayList<ServerThread> clients=new ArrayList<ServerThread>();
    // constructor with port
    public Server(int port) throws Exception
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            while (true){
                socket=server.accept();
                System.out.println("connectted");
                client=new ServerThread(socket,this);
                Thread t=new Thread(client);
                clients.add(client);
                t.start();
            }

        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String[] args) throws Exception
    {
        Server server = new Server(5000);
    }
}
