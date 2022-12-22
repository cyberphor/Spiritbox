package main.java.view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import main.java.model.Report;

public class UserInterface {
  private JFrame frame;
  private Dimension dimension;
  private Image image;
  private JTabbedPane tabs;
  private JPanel tab1; 
  private JPanel fields;
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
  private JPanel buttons;
  private JButton btnSubmit;
  private JButton btnCancel;
  private ActionListener alSubmit;
  private ActionListener alCancel;
  private String[] sources = {
    "Alert - SIEM Server",
    "Alert - IDS",
    "Alert - Anti-Virus",
    "Log - Network Firewall",
    "Log - Network Flow",
    "Log - Operating System",
    "Log - Application",
    "People - Internal",
    "People - External"
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
    for (Component c: fields.getComponents()) {
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

  private class alSubmit implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
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

  private class alCancel implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      clearTextComponents();
    } 
  }

  public UserInterface() {
    frame = new JFrame();
    dimension = new Dimension(350,500);
    image = new ImageIcon("src/main/resources/ghost.png").getImage();
    tabs = new JTabbedPane();
    tab1 = new JPanel();
    fields = new JPanel();
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
    buttons = new JPanel();
    btnSubmit = new JButton("Submit");
    btnCancel = new JButton("Cancel");
    alSubmit = new alSubmit();
    alCancel = new alCancel();
  }
  
  public void display() {
    fields.setLayout(new GridLayout(8, 2, 5, 5));
    fields.add(lbDateTimeGroup);
    fields.add(tfDateTimeGroup);
    fields.add(lbLocation);    
    fields.add(tfLocation); 
    fields.add(lbOrganization);
    fields.add(tfOrganization);
    fields.add(tfOrganization);
    fields.add(lbIndicatorSource);
    fields.add(cbIndicatorSource);
    fields.add(lbTacticDetected);
    fields.add(cbTacticDetected);
    fields.add(lbAttackerAddress);
    fields.add(tfAttackerAddress);
    fields.add(lbVictimAddress);
    fields.add(tfVictimAddress);
    fields.add(lbActionsTaken); 
    taActionsTaken.setLineWrap(true);
    taActionsTaken.setWrapStyleWord(true);
    tab1Area.setViewportView(taActionsTaken);
    buttons.setLayout(new GridLayout(1, 2, 5, 5));
    btnSubmit.addActionListener(alSubmit);
    btnCancel.addActionListener(alCancel);
    buttons.add(btnSubmit);
    buttons.add(btnCancel);
    tab1.setLayout(new BorderLayout());
    tab1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    tab1.add(fields, BorderLayout.NORTH);
    tab1.add(tab1Area, BorderLayout.CENTER);
    tab1.add(buttons, BorderLayout.SOUTH);
    tabs.addTab("Report", null, tab1, "Submit a report");
    frame.setTitle("Spiritbox");
    frame.setIconImage(image);
    frame.add(tabs);
    frame.setSize(dimension);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}