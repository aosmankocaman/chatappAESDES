// A Java program for a Server
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.net.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class Server
{
    //initialize socket and input stream
    private Socket		 socket   = null;
    private ServerSocket server   = null;
    private ServerThread client;
    public KeysAndIV keysAndIV=null;
    public ArrayList<ServerThread> clients=new ArrayList<ServerThread>();

    private KeysAndIV createKeysAndIV() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(128,secureRandom);
        SecretKey AESKey = keyGenerator.generateKey();
        keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56,secureRandom);
        SecretKey DESKey=keyGenerator.generateKey();
        byte[] AESIVBytes=new byte[16];
        byte[] DESIVBytes=new byte[8];
        secureRandom.nextBytes(AESIVBytes);
        secureRandom.nextBytes(DESIVBytes);
        return new KeysAndIV(AESKey,DESKey,AESIVBytes,DESIVBytes);
    }
    public Server(int port) throws Exception
    {


        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");
             keysAndIV=createKeysAndIV();
            while (true){
                socket=server.accept();
                System.out.println("connectted");
                client=new ServerThread(socket,this);
                client.start();
                clients.add(client);
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
