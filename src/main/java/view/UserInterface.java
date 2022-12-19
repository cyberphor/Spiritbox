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
  private JLabel lbOrganization;
  private JLabel lbDetectionMethod;
  private JLabel lbTacticDetected;
  private JLabel lbAttackerAddress;
  private JLabel lbVictimAddress;
  private JLabel lbActionsTaken;
  private JTextField tfDateTimeGroup;
  private JTextField tfLocation;
  private JTextField tfOrganization;
  private JTextField tfDetectionMethod;
  private JTextField tfTacticDetected;
  private JTextField tfAttackerAddress;
  private JTextField tfVictimAddress;
  private JTextArea taActionsTaken;
  private JScrollPane taActionsTakenScrollPane;
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

  private void displaySubmissionError(Exception error) {
    JOptionPane.showMessageDialog(
      new JFrame(),
      error.toString(),
      "Submission Error",
      JOptionPane.ERROR_MESSAGE
    );
  }

  private class tab1ButtonSubmitActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      Report report = new Report();
      System.out.println(report);
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
    lbOrganization = new JLabel("Organization");
    lbDetectionMethod = new JLabel("Detection Method");
    lbTacticDetected = new JLabel("MITRE ATT&CK Tactic Detected");
    lbAttackerAddress = new JLabel("Attacker IP Address");
    lbVictimAddress = new JLabel("Victim IP Address");
    lbActionsTaken = new JLabel("Actions Taken");
    tfDateTimeGroup = new JTextField();
    tfLocation = new JTextField();
    tfOrganization = new JTextField();
    tfDetectionMethod = new JTextField();
    tfTacticDetected = new JTextField();
    tfAttackerAddress = new JTextField();
    tfVictimAddress = new JTextField();
    taActionsTaken = new JTextArea(5,1);
    taActionsTaken.setLineWrap(true);
    taActionsTaken.setWrapStyleWord(true);
    taActionsTakenScrollPane = new JScrollPane(taActionsTaken);
    report = new Report();
    tab1Buttons = new JPanel();
    tab1ButtonSubmit = new JButton("Submit");
    tab1ButtonCancel = new JButton("Cancel");
    tab1ButtonSubmitActionListener = new tab1ButtonSubmitActionListener();
    tab1ButtonCancelActionListener = new tab1ButtonCancelActionListener();
  }
  
  public void display() {
    tab1Form.setLayout(new GridLayout(9, 2, 5, 5)); 
    tab1Form.add(lbDateTimeGroup);
    tab1Form.add(tfDateTimeGroup);
    tab1Form.add(lbLocation);    
    tab1Form.add(tfLocation); 
    tab1Form.add(lbOrganization);
    tab1Form.add(tfOrganization);
    tab1Form.add(lbDetectionMethod);
    tab1Form.add(tfDetectionMethod);
    tab1Form.add(lbTacticDetected);
    tab1Form.add(tfTacticDetected);
    tab1Form.add(lbAttackerAddress);
    tab1Form.add(tfAttackerAddress);
    tab1Form.add(lbVictimAddress);
    tab1Form.add(tfVictimAddress);
    tab1Form.add(lbActionsTaken); 
    tab1Form.add(taActionsTakenScrollPane);
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
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}