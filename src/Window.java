import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.channels.AcceptPendingException;

import javax.swing.*;

public class Window {
    public void display() {
        JFrame frame = new JFrame();
        JTabbedPane tabbedPane = new JTabbedPane(); 

        ////////// Tab 1 //////////
        JPanel tab1 = new JPanel(); 
        JPanel tab1Form = new JPanel();
        JPanel tab1Buttons = new JPanel();
        JLabel lbNumber = new JLabel("Number");
        JLabel lbDateTimeGroup = new JLabel("Date-Time Group");
        JLabel lbLocation = new JLabel("Location");
        JLabel lbSystemsAffected = new JLabel("Systems Affected");
        JLabel lbActionsTaken = new JLabel("Actions Taken");
        JTextField tfNumber = new JTextField();
        JTextField tfDateTimeGroup = new JTextField();
        JTextField tfLocation = new JTextField();
        JTextField tfSystemsAffected = new JTextField();
        JTextField tfActionsTaken = new JTextField();
        JButton tab1ButtonOK = new JButton("OK");
        JButton tab1ButtonCancel = new JButton("Cancel");
        tab1Form.setLayout(new GridLayout(5, 2, 5, 5)); // set layout so num of rows = num of form fields
        tab1Form.add(lbNumber);
        tab1Form.add(tfNumber);
        tab1Form.add(lbDateTimeGroup);
        tab1Form.add(tfDateTimeGroup);
        tab1Form.add(lbLocation);
        tab1Form.add(tfLocation);
        tab1Form.add(lbSystemsAffected);
        tab1Form.add(tfSystemsAffected);
        tab1Form.add(lbActionsTaken);
        tab1Form.add(tfActionsTaken);
        tab1ButtonOK.addActionListener(new ActionListener() {
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
        tab1ButtonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfNumber.setText("");
                tfDateTimeGroup.setText("");
                tfLocation.setText("");
            }
        });
        tab1Buttons.setLayout(new GridLayout(1, 2, 5, 5));
        tab1Buttons.add(tab1ButtonOK);
        tab1Buttons.add(tab1ButtonCancel);
        tab1.setLayout(new BorderLayout());
        tab1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        tab1.add(tab1Form, BorderLayout.NORTH);
        tab1.add(tab1Buttons, BorderLayout.SOUTH);
        tabbedPane.add("Add", tab1);  

        ////////// Tab 2 //////////
        JPanel tab2 = new JPanel();  
        JPanel tab2Form = new JPanel();  
        tab2.add(tab2Form);
        tabbedPane.add("Search", tab2);

        ////////// Tab 3 //////////
        JPanel tab3 = new JPanel(); 
        JPanel tab3Form = new JPanel();
        tab3.add(tab3Form);  
        tabbedPane.add("Edit", tab3);  

        ////////// Tab 4 //////////
        JPanel tab4 = new JPanel();
        JPanel tab4Form = new JPanel(); 
        tab4.add(tab4Form);
        tabbedPane.add("Monitor", tab4);           

        frame.add(tabbedPane);
        frame.setIconImage(new ImageIcon(getClass().getResource("ghost.png")).getImage());
        frame.setTitle("Spiritbox");
        frame.setSize(500,600);
        frame.setMinimumSize(new Dimension(300,400));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}