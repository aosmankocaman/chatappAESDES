

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
        encryption=true;
        activateButtons();

    }


    public void addTextToMsgArea(String msg){
        this.msgArea.insert(msg+"\n",this.msgArea.getText().length());
    }
    public void setDisconnectButton(ActionEvent e){
        addTextToMsgArea("alis");
        connection=false;
        encryption=false;
        activateButtons();

    }
    public void setConnectButton( ActionEvent e){
            connection=true;
            activateButtons();
    }
    public void activateButtons(){
        encryptButton.setEnabled(connection);
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