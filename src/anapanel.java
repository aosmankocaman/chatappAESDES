import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class anapanel extends JFrame implements WindowListener {
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
    private JTextField notCryptedText;
    private JTextField cryptedText;
    private JButton encryptButton;
    private JButton sendButton;
    private JLabel connectionStatusLabel;
    private JScrollPane messageStream;
    private String username;
    private Client client;

    public anapanel() throws Exception {
        //final String[] username = new String[1];
        connectionStatusLabel.setText("Not Connected");

        ButtonGroup modes = new ButtonGroup();
        modes.add(CBCRadioButton);
        modes.add(OFBRadioButton);
        ButtonGroup methods = new ButtonGroup();
        methods.add(AESRadioButton);
        methods.add(DESRadioButton);
        add(rootPanel);
        setTitle("Crypto Messenger");
        setSize(840,880);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        username= (String)JOptionPane.showInputDialog("Enter Username: ");
         //client = new Client("localhost", 5000,this);

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Packet packet=new Packet("seam sakhalin");

                System.out.println("sasase");
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
    public void print(String sa){
        System.out.println(sa);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
