import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class anapanel extends JFrame{
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


    public anapanel() {
        connectionStatusLabel.setText("Not Connected");

        ButtonGroup modes = new ButtonGroup();
        modes.add(CBCRadioButton);
        modes.add(OFBRadioButton);



        ButtonGroup methods = new ButtonGroup();
        methods.add(AESRadioButton);
        methods.add(DESRadioButton);

        final String[] username = new String[1];
        add(rootPanel);
        setTitle("Crypto Messenger");
        setSize(840,880);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //connect button
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String usernamefrompanel = (String)JOptionPane.showInputDialog(
                        "Enter Username: ");
                username[0] = usernamefrompanel;
            }
        });

        //encrypt button
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                System.out.println(CBCRadioButton.isSelected());
            }
        });


    }



    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
