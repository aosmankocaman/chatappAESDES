

import javax.swing.*;

import java.awt.event.*;

import java.nio.charset.StandardCharsets;


public class Gui  extends JFrame  {
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
    private boolean connection=false;
    private boolean encryption=false;
    private String username;
    private ButtonGroup modes;
    private ButtonGroup methods;
    private Client client=null;
    private Packet packet;

    public Gui() {
        setup();

        connectButton.addActionListener(
                this::setConnectButton
        );
        disconnectButton.addActionListener(
                this::setDisconnectButton
        );
        encryptButton.addActionListener(
                this::setEncryptButton
        );
        sendButton.addActionListener(
                this::setSendButton
        );


    }

    private void closeWindow(){

        this.username=null;
        if(client!=null)
            this.client.closeConnection();
        this.packet=null;
        this.client=null;

    }
    private void setSendButton(ActionEvent actionEvent) {
        cryptedText.setText("");
        notCryptedText.setText("");
        this.client.request(this.packet);
        encryption=false;
        activateButtons();

    }

    private void setEncryptButton(ActionEvent e) {
        if(connection){
            if(!notCryptedText.getText().equals("")){
                if(CBCRadioButton.isSelected() && AESRadioButton.isSelected()){
                    // System.out.println("CBC with AES");
                    this.packet.setMode("CBC");
                    this.packet.setMethod("AES");
                }
                else if(CBCRadioButton.isSelected() && DESRadioButton.isSelected()){
                    //System.out.println("CBC with DES");
                    this.packet.setMode("CBC");
                    this.packet.setMethod("DES");

                }
                else if(OFBRadioButton.isSelected() && AESRadioButton.isSelected()){
                    // System.out.println("OFB with AES");
                    this.packet.setMode("OFB");
                    this.packet.setMethod("AES");

                }
                else{
                    //System.out.println("OFB with DES");
                    this.packet.setMode("OFB");
                    this.packet.setMethod("DES");

                }
                encryption=true;
                packet.setText(this.client.getCipherBlock().encryption(this.packet,notCryptedText.getText().getBytes(StandardCharsets.UTF_8)));
                cryptedText.setText(new String(this.packet.getText()));
                activateButtons();

            }
            else{
                JOptionPane.showMessageDialog(this, "Please write a message before Encrypting.");
            }
        }
        else JOptionPane.showMessageDialog(this, "You have to connect first.");

    }


    public void addTextToMsgArea(String msg){
        this.msgArea.insert(msg+"\n",this.msgArea.getText().length());
    }

    public void setDisconnectButton(ActionEvent e){
        connectionStatusLabel.setText("Not Connected");
        //addTextToMsgArea(username + " disconnected.");
        connection=false;
        encryption=false;
        activateButtons();
        this.username=null;
        if(client!=null)
            client.closeConnection();
        this.packet=null;
        client=null;

    }
    public void setConnectButton( ActionEvent e){
        username = (String)JOptionPane.showInputDialog(
                "Enter Username: ");
        if(username!=null){
            if(!username.equals("")){
                connectionStatusLabel.setText("Connected");
                this.client=new Client(this,username);
                packet=new Packet(username,true);
                connection=true;
                activateButtons();
            }
            else JOptionPane.showMessageDialog(this,"Please enter valid name.");
        }

    }
    public void activateButtons(){
        sendButton.setEnabled(connection&&encryption);
        disconnectButton.setEnabled(connection);
        connectButton.setEnabled(!connection);
    }

    public void setup(){

        modes = new ButtonGroup();
        modes.add(CBCRadioButton);
        modes.add(OFBRadioButton);
        CBCRadioButton.setActionCommand("CBC");
        OFBRadioButton.setActionCommand("OFB");
        AESRadioButton.setActionCommand("AES");
        DESRadioButton.setActionCommand("DES");
        CBCRadioButton.setSelected(true);
        AESRadioButton.setSelected(true);
        connectionStatusLabel.setText("Not Connected");
        msgArea.setEditable(false);
        cryptedText.setEditable(false);
        activateButtons();

        methods = new ButtonGroup();
        methods.add(AESRadioButton);
        methods.add(DESRadioButton);
        add(rootPanel);
        setTitle("Crypto Messenger");
        setSize(840,950);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        notCryptedText.setLineWrap(true);
        notCryptedText.setWrapStyleWord(true);
        msgArea.setLineWrap(true);
        msgArea.setWrapStyleWord(true);
        cryptedText.setLineWrap(true);
        cryptedText.setWrapStyleWord(true);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                closeWindow();
            }

        });
    }



}