
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import javax.crypto.*;
public class Server {
    private Socket socket   = null;
    private ServerSocket server   = null;
    private ServerThread client;
    public KeysAndIV keysAndIV=null;
    private File file=null;
    private FileWriter logFile=null;
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
            initLog();

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
    public void initLog() {
        file=new File("log.txt");
        try {
            logFile=new FileWriter(file,true);
            logFile.write("AES IV > "+new String(Base64.getEncoder().encode(keysAndIV.getAESIV()))+"\n");
            logFile.write("DES IV > "+new String(Base64.getEncoder().encode(keysAndIV.getDESIV()))+"\n");
            logFile.write("AES Key > "+new String(Base64.getEncoder().encode(keysAndIV.getAESKey().getEncoded()))+"\n");
            logFile.write("DES Key > "+new String(Base64.getEncoder().encode(keysAndIV.getDESKey().getEncoded()))+"\n");
            System.out.println("AES IV > "+new String(Base64.getEncoder().encode(keysAndIV.getAESIV())));
            System.out.println("DES IV > "+new String(Base64.getEncoder().encode(keysAndIV.getDESIV())));
            System.out.println("AES Key > "+new String(Base64.getEncoder().encode(keysAndIV.getAESKey().getEncoded())));
            System.out.println("DES Key > "+new String(Base64.getEncoder().encode(keysAndIV.getDESKey().getEncoded())));

            logFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeLogFile(Packet packet){
        try {
            logFile=new FileWriter(file,true);
            logFile.write(packet.getUserName()+" > "+new String(packet.getText())+"\n");
            System.out.println(packet.getUserName()+" > "+new String(packet.getText()));
            logFile.close();
        }catch (IOException e){
            e.printStackTrace();
        }


    }
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(5000);
    }

}
