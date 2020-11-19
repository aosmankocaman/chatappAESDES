import javafx.scene.control.RadioButton;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class anapanel extends JFrame   {
    private JPanel rootPanel;
    private JButton connectButton;
    private JButton disconnectButton;
    private JRadioButton AESRadioButton;
    private JRadioButton DESRadioButton;
    private JRadioButton CBCRadioButton;
    private JRadioButton OFBRadioButton;
    private JPanel messagingArea;
    private JPanel generalControl;
    private JPanel textingArea;
    private JTextArea notCryptedText;
    private JTextArea cryptedText;
    private JButton encryptButton;
    private JButton sendButton;
    private JLabel connectionStatusLabel;
    private JScrollPane messageStream;
    private JScrollPane notCryptedTextScroll;
    private JScrollPane cyptedTextScroll;
    public JTextArea msgArea;
    private String username;
    public Client client;
    private boolean activateConnect=true;
    private boolean activateSendButton=false;
    private int i=2;

    private CipherBlock cipherBlock;
    private Packet packet=new Packet("",true);
    public String msg="";
    private void setButtonActivate(){
        disconnectButton.setEnabled(!activateConnect);
        connectButton.setEnabled(activateConnect);
    }

    public anapanel() throws Exception {


        msgArea.setEnabled(false);
        msgArea.setText(msg);
        msgArea.setEditable(false);

        connectionStatusLabel.setText("Not Connected");
        disconnectButton.setEnabled(!activateConnect);
        connectButton.setEnabled(activateConnect);
        sendButton.setEnabled(!activateSendButton);
        CBCRadioButton.setSelected(true);
        AESRadioButton.setSelected(true);
        client= new Client("localhost",5000,this);



        notCryptedText.setLineWrap(true);
        notCryptedText.setWrapStyleWord(true);
        msgArea.setLineWrap(true);
        msgArea.setWrapStyleWord(true);
        cryptedText.setLineWrap(true);
        cryptedText.setWrapStyleWord(true);
        ButtonGroup modes = new ButtonGroup();
        modes.add(CBCRadioButton);
        modes.add(OFBRadioButton);
        CBCRadioButton.setActionCommand("CBC");
        OFBRadioButton.setActionCommand("OFB");
        AESRadioButton.setActionCommand("AES");
        DESRadioButton.setActionCommand("DES");

        ButtonGroup methods = new ButtonGroup();
        methods.add(AESRadioButton);
        methods.add(DESRadioButton);
        methods.getSelection();
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                byte [] text=  notCryptedText.getText().getBytes(StandardCharsets.UTF_8);

                if(methods.getSelection().getActionCommand().equals("AES")){
                    packet.setMethod("AES");
                    if(modes.getSelection().getActionCommand().equals("CBC")){
                        try {
                            cryptedText.setText(new String(client.getClientThread().getCipherBlock().base64Encoder(client.getClientThread().getCipherBlock().AESCBCEncryption(text))));
                            packet.setMode("CBC");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    else{
                        try {
                            cryptedText.setText(new String(client.getClientThread().getCipherBlock().base64Encoder(client.getClientThread().getCipherBlock().AESOFBEncryption(text))));
                            packet.setMode("OFB");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                else {
                    packet.setMethod("DES");
                    if(modes.getSelection().getActionCommand().equals("CBC")){
                        try {
                            cryptedText.setText(new String(client.getClientThread().getCipherBlock().base64Encoder(client.getClientThread().getCipherBlock().DESCBCEncryption(text))));
                            packet.setMode("CBC");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    else{
                        try {
                            cryptedText.setText(new String(client.getClientThread().getCipherBlock().base64Encoder(client.getClientThread().getCipherBlock().DESOFBEncryption(text))));
                            packet.setMode("OFB");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                }

            }
        });
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activateConnect=true;
                setButtonActivate();
                client.disconnect();
                client.name="";
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.send(packet);
                //client.getClientThread().request(new Packet(username,false));
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                close();
            }

        });
        add(rootPanel);
        setTitle("Crypto Messenger");
        setSize(840,950);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


         //client = new Client("localhost", 5000,this);
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(notCryptedText.getText());
            }
        });
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                username= (String)JOptionPane.showInputDialog("Enter Username: ");
                if(username != null ){
                    if(!username.equals("")){

                        activateConnect=false;
                        setButtonActivate();
                        try {
                           client.name=username;
                           packet.setName(username);
                            client.connect();
                            System.out.println(packet.getName());

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                }

            }
        });

/*
        //encrypt button
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String usernamefrompanel = (String)JOptionPane.showInputDialog(
                        "Enter Username: ");
                username[0] = usernamefrompanel;
            }
        });*/


    }
    public void close( ) {

        try{
        client.close();
    }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


}
