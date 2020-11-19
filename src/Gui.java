

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
    private ButtonGroup modes;
    private ButtonGroup methods;
    private String username;

    public Gui() throws Exception {
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


    private void setSendButton(ActionEvent actionEvent) {

        cryptedText.setText("");
        notCryptedText.setText("");
        encryption=false;
        activateButtons();

    }

    private void setEncryptButton(ActionEvent e) {
        if(connection){
            if(!notCryptedText.getText().equals("")){
                if(CBCRadioButton.isSelected() && AESRadioButton.isSelected()){
                    System.out.println("CBC with AES");
                }
                else if(CBCRadioButton.isSelected() && DESRadioButton.isSelected()){
                    System.out.println("CBC with DES");
                }
                else if(OFBRadioButton.isSelected() && AESRadioButton.isSelected()){
                    System.out.println("OFB with AES");
                }
                else{
                    System.out.println("OFB with DES");
                }
                encryption=true;
                cryptedText.setText("encryption");
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

        addTextToMsgArea(username + " disconnected.");

        connection=false;
        encryption=false;
        activateButtons();

    }

    public boolean connecting(boolean valid){
        username = (String)JOptionPane.showInputDialog(
                "Enter Username: ");
        if(username.equals("")){
            JOptionPane.showMessageDialog(this,"Please enter valid name.");
            return false;
        }
        else return true;


    }
    public void setConnectButton( ActionEvent e){
        boolean valid = false;

        while(!valid){
            valid = connecting(false);
        }



        connection=true;
        activateButtons();
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
                System.out.println("saasa");
            }

        });
    }



}