import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;
import javax.crypto.*;
public class Server {
    private Socket socket   = null;
    private ServerSocket server   = null;
    private ServerThread client;
    public KeysAndIV keysAndIV=null;
    public ArrayList<ServerThread> clients=new ArrayList<ServerThread>();
    private KeysAndIV createKeysAndIV() {
        SecretKey AESKey = null;
        SecretKey DESKey = null;
        byte[] AESIVBytes = new byte[0];
        byte[] DESIVBytes = new byte[0];
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = new SecureRandom();
            keyGenerator.init(128, secureRandom);
            AESKey = keyGenerator.generateKey();
            keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56, secureRandom);
            DESKey = keyGenerator.generateKey();
            AESIVBytes = new byte[16];
            DESIVBytes = new byte[8];
            secureRandom.nextBytes(AESIVBytes);
            secureRandom.nextBytes(DESIVBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new KeysAndIV(AESKey, DESKey, AESIVBytes, DESIVBytes);
    }
    public Server(int port) throws Exception
    {
        try {
            server = new ServerSocket(port);
            keysAndIV=createKeysAndIV();
            while (true){
                socket=server.accept();
                client=new ServerThread(socket,this);
                client.start();
                clients.add(client);
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception
    {
        Server server = new Server(5000);
    }

}
