import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.channels.AcceptPendingException;

import javax.swing.*;

public class Frame extends JFrame {
    JTextField 
        tfNumber, 
        tfDateTimeGroup, 
        tfLocation, 
        tfSystemsAffected, 
        tfActionsTaken;

    JLabel lbWelcome;

    public void init() {
        JLabel lbNumber = new JLabel("Number");
        tfNumber = new JTextField();

        JLabel lbDateTimeGroup = new JLabel("Date-Time Group");
        tfDateTimeGroup = new JTextField();

        JLabel lbLocation = new JLabel("Location");
        tfLocation = new JTextField();
        
        JLabel lbSystemsAffected = new JLabel("Systems Affected");
        tfSystemsAffected = new JTextField();
        
        JLabel lbActionsTaken = new JLabel("Actions Taken");
        tfActionsTaken = new JTextField();

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 1, 5, 5));
        formPanel.add(lbNumber);
        formPanel.add(tfNumber);
        formPanel.add(lbDateTimeGroup);
        formPanel.add(tfDateTimeGroup);

        /*
        formPanel.add(lbLocation);
        formPanel.add(tfLocation);

        formPanel.add(lbSystemsAffected);
        formPanel.add(tfSystemsAffected);

        formPanel.add(lbActionsTaken);
        formPanel.add(tfActionsTaken);
        */

        lbWelcome = new JLabel();

        JButton btOK = new JButton("OK");
        btOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = tfNumber.getText();
                lbWelcome.setText("Number: " + number);
    
        });

        JButton btCancel = new JButton("Cancel");
        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfNumber.setText("");
                lbWelcome.setText("");
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 5, 5));
        buttonsPanel.add(btOK);
        buttonsPanel.add(btCancel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(lbWelcome, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setTitle("Spiritbox");
        setSize(500,600);
        setMinimumSize(new Dimension(300,400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}