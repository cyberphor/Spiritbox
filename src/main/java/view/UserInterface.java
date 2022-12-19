package main.java.view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import main.java.model.Report;

public class UserInterface {
  private JFrame frame;
  private Dimension dimension;
  private Image image;
  private JTabbedPane tabbedPane;
  private JPanel tab1; 
  private JPanel tab1Form;
  private JLabel lbDateTimeGroup;
  private JLabel lbLocation;
  private JLabel lbSystemsAffected;
  private JLabel lbActionsTaken;
  private JTextField tfDateTimeGroup;
  private JTextField tfLocation;
  private JTextField tfSystemsAffected;
  private JTextArea tfActionsTaken;
  private JScrollPane tfActionsTakenScrollPane;
  private Report report;
  private JPanel tab1Buttons;
  private JButton tab1ButtonSubmit;
  private JButton tab1ButtonCancel;
  private ActionListener tab1ButtonSubmitActionListener;
  private ActionListener tab1ButtonCancelActionListener;

  private void clearTextComponents() {
    for (Component c: tab1Form.getComponents()) {
      if (c instanceof JTextField) {
        ((JTextField) c).setText(null);
      } else if (c instanceof JTextArea) {
        ((JTextArea) c).setText(null);
      } 
    }
  }

  private class tab1ButtonSubmitActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      report.setDateTimeGroup(null);
      report.setLocation(tfLocation.getText());
      report.setSystemsAffected(null);
      report.setActionsTaken(tfActionsTaken.getText());
      System.out.println(report.toString());
      clearTextComponents();
    } 
  }

  private class tab1ButtonCancelActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      clearTextComponents();
    } 
  }

  public UserInterface() {
    frame = new JFrame();
    dimension = new Dimension(300,500);
    image = new ImageIcon("src/main/resources/ghost.png").getImage();
    tabbedPane = new JTabbedPane();
    tab1 = new JPanel();
    tab1Form = new JPanel();
    lbDateTimeGroup = new JLabel("Date-Time Group");
    lbLocation = new JLabel("Location");
    lbSystemsAffected = new JLabel("Systems Affected");
    lbActionsTaken = new JLabel("Actions Taken"); 
    tfDateTimeGroup = new JTextField();
    tfLocation = new JTextField();
    tfSystemsAffected = new JTextField();
    tfActionsTaken = new JTextArea(1,0);
    tfActionsTakenScrollPane = new JScrollPane(tfActionsTaken);
    report = new Report();
    tab1Buttons = new JPanel();
    tab1ButtonSubmit = new JButton("Submit");
    tab1ButtonCancel = new JButton("Cancel");
    tab1ButtonSubmitActionListener = new tab1ButtonSubmitActionListener();
    tab1ButtonCancelActionListener = new tab1ButtonCancelActionListener();
  }
  
  public void display() {
    tab1Form.setLayout(new GridLayout(5, 2, 5, 5)); 
    tab1Form.add(lbDateTimeGroup);
    tab1Form.add(tfDateTimeGroup);
    tab1Form.add(lbLocation);    
    tab1Form.add(tfLocation); 
    tab1Form.add(lbSystemsAffected);   
    tab1Form.add(tfSystemsAffected);
    tab1Form.add(lbActionsTaken); 
    //tfActionsTaken.setLineWrap(true);
    //tfActionsTaken.setWrapStyleWord(true);
    tab1Form.add(tfActionsTakenScrollPane);
    tab1Buttons.setLayout(new GridLayout(1, 2, 5, 5));
    tab1ButtonSubmit.addActionListener(tab1ButtonSubmitActionListener);
    tab1ButtonCancel.addActionListener(tab1ButtonCancelActionListener);
    tab1Buttons.add(tab1ButtonSubmit);
    tab1Buttons.add(tab1ButtonCancel);
    tab1.setLayout(new BorderLayout());
    tab1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    tab1.add(tab1Form, BorderLayout.NORTH);
    tab1.add(tab1Buttons, BorderLayout.SOUTH);
    tabbedPane.addTab("Report", null, tab1, "Submit a report");
    frame.setTitle("Spiritbox");
    frame.setIconImage(image);
    frame.add(tabbedPane);
    frame.setSize(dimension);
    frame.setMinimumSize(dimension);
    frame.setMaximumSize(dimension);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}