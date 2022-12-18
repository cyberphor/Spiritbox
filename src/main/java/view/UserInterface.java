package main.java.view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import main.java.model.Report;

public class UserInterface {
  private JFrame frame;
  private Image image;
  private JTabbedPane tabbedPane;
  private JPanel tab1; 
  private JPanel tab1Form; 
  private JLabel lbReportNumber;
  private JLabel lbDateTimeGroup;
  private JLabel lbLocation;
  private JLabel lbSystemsAffected;
  private JLabel lbActionsTaken;
  private JTextField tfReportNumber;
  private JTextField tfDateTimeGroup;
  private JTextField tfLocation;
  private JTextField tfSystemsAffected;
  private JTextArea tfActionsTaken;
  private Report report;
  private JPanel tab1Buttons;
  private JButton tab1ButtonOK;
  private JButton tab1ButtonCancel;
  private ActionListener tab1ButtonOKActionListener;
  private ActionListener tab1ButtonCancelActionListener;

  private void clearTextComponents() {
    for (Component c: tab1Form.getComponents()) {
      if (c instanceof JTextField) {
        ((JTextField) c).setText("");
      } else if (c instanceof JTextArea) {
        ((JTextArea) c).setText("");
      } 
    }
  }

  private class tab1ButtonOKActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      report.setReportNumber(tfReportNumber.getText());
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
    image = new ImageIcon("src/main/resources/ghost.png").getImage();
    tabbedPane = new JTabbedPane();
    tab1 = new JPanel();
    tab1Form = new JPanel();
    lbReportNumber = new JLabel("Report Number");
    lbDateTimeGroup = new JLabel("Date-Time Group");
    lbLocation = new JLabel("Location");
    lbSystemsAffected = new JLabel("Systems Affected");
    lbActionsTaken = new JLabel("Actions Taken"); 
    tfReportNumber = new JTextField();
    tfDateTimeGroup = new JTextField();
    tfLocation = new JTextField();
    tfSystemsAffected = new JTextField();
    tfActionsTaken = new JTextArea(1,0);
    report = new Report();
    tab1Buttons = new JPanel();
    tab1ButtonOK = new JButton("OK");
    tab1ButtonCancel = new JButton("Cancel");
    tab1ButtonOKActionListener = new tab1ButtonOKActionListener();
    tab1ButtonCancelActionListener = new tab1ButtonCancelActionListener();
  }
  
  public void display() {
    tab1Form.setLayout(new GridLayout(5, 2, 5, 5)); 
    tab1Form.add(lbReportNumber);
    tab1Form.add(tfReportNumber);
    tab1Form.add(lbDateTimeGroup);
    tab1Form.add(tfDateTimeGroup);
    tab1Form.add(lbLocation);    
    tab1Form.add(tfLocation); 
    tab1Form.add(lbSystemsAffected);   
    tab1Form.add(tfSystemsAffected);
    tab1Form.add(lbActionsTaken); 
    tfActionsTaken.setLineWrap(true);
    tfActionsTaken.setWrapStyleWord(true);
    tab1Form.add(tfActionsTaken);
    tab1Buttons.setLayout(new GridLayout(1, 2, 5, 5));
    tab1ButtonOK.addActionListener(tab1ButtonOKActionListener);
    tab1ButtonCancel.addActionListener(tab1ButtonCancelActionListener);
    tab1Buttons.add(tab1ButtonOK);
    tab1Buttons.add(tab1ButtonCancel);
    tab1.setLayout(new BorderLayout());
    tab1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    tab1.add(tab1Form, BorderLayout.NORTH);
    tab1.add(tab1Buttons, BorderLayout.SOUTH);
    tabbedPane.addTab("Create", null, tab1, "Create a report");
    frame.setTitle("Spiritbox");
    frame.setIconImage(image);
    frame.add(tabbedPane);
    frame.setSize(500,600);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}