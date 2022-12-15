package main.java.view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UserInterface  {
  public static JFrame display() {
    // Creates a parent frame
    JFrame ui = new JFrame();

    // Creates the form for tab 1.
    JPanel tab1Form = new JPanel();
    tab1Form.setLayout(new GridLayout(5, 2, 5, 5)); 

    JLabel lbReportNumber = new JLabel("Report Number");
    tab1Form.add(lbReportNumber);

    JTextField tfReportNumber = new JTextField();
    tab1Form.add(tfReportNumber);

    JLabel lbDateTimeGroup = new JLabel("Date-Time Group");
    tab1Form.add(lbDateTimeGroup);

    JTextField tfDateTimeGroup = new JTextField();
    tab1Form.add(tfDateTimeGroup);

    JLabel lbLocation = new JLabel("Location");
    tab1Form.add(lbLocation);    

    JTextField tfLocation = new JTextField();   
    tab1Form.add(tfLocation); 

    JLabel lbSystemsAffected = new JLabel("Systems Affected");
    tab1Form.add(lbSystemsAffected); 

    JTextField tfSystemsAffected = new JTextField();  
    tab1Form.add(tfSystemsAffected);

    JLabel lbActionsTaken = new JLabel("Actions Taken"); 
    tab1Form.add(lbActionsTaken); 

    JTextArea tfActionsTaken = new JTextArea(1,0);
    tfActionsTaken.setLineWrap(true);
    tfActionsTaken.setWrapStyleWord(true);
    tab1Form.add(tfActionsTaken);

    // Creates the buttons for tab 1.
    JPanel tab1Buttons = new JPanel();
    tab1Buttons.setLayout(new GridLayout(1, 2, 5, 5));

    JButton tab1ButtonOK = new JButton("OK");
    tab1ButtonOK.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
      }
    }); 
    tab1Buttons.add(tab1ButtonOK);

    JButton tab1ButtonCancel = new JButton("Cancel");
    tab1ButtonCancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (Component c: tab1Form.getComponents()) {
          if (c instanceof JTextField) {
            ((JTextField) c).setText(null);
          } else if (c instanceof JTextArea) {
            ((JTextArea) c).setText(null);
          }
        }
      }
    });    
    tab1Buttons.add(tab1ButtonCancel); 

    // Attaches the form and buttons for tab to a panel.
    JPanel tab1 = new JPanel();
    tab1.setLayout(new BorderLayout());
    tab1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    tab1.add(tab1Form, BorderLayout.NORTH);
    tab1.add(tab1Buttons, BorderLayout.SOUTH);

    // Creates a tabbed pane and attaches tabs to it.
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Create", null, tab1, "Create a report");

    // Attaches the tabbed pane to the parent frame.
    ui.setTitle("Spiritbox");
    ui.setIconImage(new ImageIcon("src/main/resources/ghost.png").getImage());
    ui.setMinimumSize(new Dimension(300,400));
    ui.setSize(500,600);
    ui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    ui.add(tabbedPane);
    ui.setVisible(true);
    return ui;
    }
}