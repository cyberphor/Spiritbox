import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.channels.AcceptPendingException;

import javax.swing.*;

public class Menu extends JFrame {
    private JComponent makeTextPanel(String string) {
        return null;
    }
    
    public void initialize() {
        JTextField 
            tfNumber, 
            tfDateTimeGroup, 
            tfLocation, 
            tfSystemsAffected, 
            tfActionsTaken;

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
        // set layout so num of rows = num of form fields
        formPanel.setLayout(new GridLayout(5, 2, 5, 5));
        formPanel.add(lbNumber);
        formPanel.add(tfNumber);
        formPanel.add(lbDateTimeGroup);
        formPanel.add(tfDateTimeGroup);
        formPanel.add(lbLocation);
        formPanel.add(tfLocation);
        formPanel.add(lbSystemsAffected);
        formPanel.add(tfSystemsAffected);
        formPanel.add(lbActionsTaken);
        formPanel.add(tfActionsTaken);

        JButton btOK = new JButton("OK");
        btOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                String number = tfNumber.getText();
                String dateTimeGroup = tfDateTimeGroup.getText();
                String location = tfLocation.getText();
                lbWelcome.setText(number);
                */
            }
        });

        JButton btCancel = new JButton("Cancel");
        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfNumber.setText("");
                tfDateTimeGroup.setText("");
                tfLocation.setText("");
                // lbWelcome.setText("");
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 5, 5));
        buttonsPanel.add(btOK);
        buttonsPanel.add(btCancel);

        JTabbedPane tabbedPane = new JTabbedPane();
        JComponent panel1 = makeTextPanel("Tab 1");
        tabbedPane.addTab("Tab 1", null, panel1, "Does nothing");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setIconImage(new ImageIcon(getClass().getResource("ghost.png")).getImage());
        setTitle("Spiritbox");
        setSize(500,600);
        setMinimumSize(new Dimension(300,400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}