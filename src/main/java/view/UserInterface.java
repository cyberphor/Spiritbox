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
  private JPanel tab1Fields;
  private JLabel lbDateTimeGroup;
  private JLabel lbLocation;
  private JLabel lbOrganization;
  private JLabel lbIndicatorSource;
  private JLabel lbTacticDetected;
  private JLabel lbAttackerAddress;
  private JLabel lbVictimAddress;
  private JTextField tfDateTimeGroup; // TODO: change to date/time picker
  private JTextField tfLocation;
  private JTextField tfOrganization;
  private JComboBox<String> cbIndicatorSource;
  private JComboBox<String> cbTacticDetected;
  private JTextField tfAttackerAddress;
  private JTextField tfVictimAddress;
  private JScrollPane tab1Area;
  private JLabel lbActionsTaken;
  private JTextArea taActionsTaken;
  private JPanel tab1Buttons;
  private JButton tab1ButtonSubmit;
  private JButton tab1ButtonCancel;
  private ActionListener tab1ButtonSubmitActionListener;
  private ActionListener tab1ButtonCancelActionListener;
  private String[] sources = {
    "Alert - Security Incident & Event Management Server",
    "Alert - Intrusion Detection System",
    "Alert - Anti-Virus Program",
    "Alert - File Integrity Checking Program",
    "Alert - Third-Party Service",
    "Log - Network Firewall",
    "Log - Network Flow",
    "Log - Operating System",
    "Log - Application",
    "People - Insider",
    "People - Outsider"
  };
  private String[] tactics = { 
    "Reconnaissance",
    "Initial Access",
    "Execution",
    "Persistence",
    "Privilege Escalation",
    "Defense Evasion",
    "Credential Access",
    "Discovery",
    "Lateral Movement",
    "Collection",
    "Command and Control",
    "Exfiltration",
    "Impact"
  };


  private void clearTextComponents() {
    for (Component c: tab1Fields.getComponents()) {
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
      // TODO: add submission error handling
      Report report = new Report()
        .setDateTimeGroup(tfDateTimeGroup.getText())
        .setLocation(tfLocation.getText())
        .setOrganization(tfOrganization.getText())
        .setIndicatorSource(cbIndicatorSource.getSelectedItem().toString()) 
        .setTacticDetected(cbTacticDetected.getSelectedItem().toString())
        .setAttackerAddress(tfAttackerAddress.getText())
        .setVictimAddress(tfVictimAddress.getText())
        .setActionsTaken(taActionsTaken.getText());
      report.print();
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
    tab1Fields = new JPanel();
    lbDateTimeGroup = new JLabel("Date-Time Group");
    lbLocation = new JLabel("Location");
    lbOrganization = new JLabel("Organization");
    lbIndicatorSource = new JLabel("Indicator Source");
    lbTacticDetected = new JLabel("Tactic Detected");
    lbAttackerAddress = new JLabel("Attacker IP Address");
    lbVictimAddress = new JLabel("Victim IP Address");
    tfDateTimeGroup = new JTextField();
    tfLocation = new JTextField();
    tfOrganization = new JTextField();
    cbIndicatorSource = new JComboBox<String>(sources);
    cbTacticDetected = new JComboBox<String>(tactics);
    tfAttackerAddress = new JTextField();
    tfVictimAddress = new JTextField();
    tab1Area = new JScrollPane();
    lbActionsTaken = new JLabel("Actions Taken");
    taActionsTaken = new JTextArea();
    tab1Buttons = new JPanel();
    tab1ButtonSubmit = new JButton("Submit");
    tab1ButtonCancel = new JButton("Cancel");
    tab1ButtonSubmitActionListener = new tab1ButtonSubmitActionListener();
    tab1ButtonCancelActionListener = new tab1ButtonCancelActionListener();
  }
  
  public void display() {
    tab1Fields.setLayout(new GridLayout(8, 2, 5, 5)); 
    tab1Fields.add(lbDateTimeGroup);
    tab1Fields.add(tfDateTimeGroup);
    tab1Fields.add(lbLocation);    
    tab1Fields.add(tfLocation); 
    tab1Fields.add(lbOrganization);
    tab1Fields.add(tfOrganization);
    tab1Fields.add(tfOrganization);
    tab1Fields.add(lbIndicatorSource);
    tab1Fields.add(cbIndicatorSource);
    tab1Fields.add(lbTacticDetected);
    tab1Fields.add(cbTacticDetected);
    tab1Fields.add(lbAttackerAddress);
    tab1Fields.add(tfAttackerAddress);
    tab1Fields.add(lbVictimAddress);
    tab1Fields.add(tfVictimAddress);
    tab1Fields.add(lbActionsTaken); 
    taActionsTaken.setLineWrap(true);
    taActionsTaken.setWrapStyleWord(true);
    tab1Area.setViewportView(taActionsTaken);
    tab1Buttons.setLayout(new GridLayout(1, 2, 5, 5));
    tab1ButtonSubmit.addActionListener(tab1ButtonSubmitActionListener);
    tab1ButtonCancel.addActionListener(tab1ButtonCancelActionListener);
    tab1Buttons.add(tab1ButtonSubmit);
    tab1Buttons.add(tab1ButtonCancel);
    tab1.setLayout(new BorderLayout());
    tab1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    tab1.add(tab1Fields, BorderLayout.NORTH);
    tab1.add(tab1Area, BorderLayout.CENTER);
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