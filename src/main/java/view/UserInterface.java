package main.java.view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import main.java.model.Report;

public class UserInterface extends JFrame {
  // Frame properties
  private Dimension size;
  private Image image;
  private JTabbedPane tabs;
  private JPanel tab1;

  // "Fields" pane properties
  private JPanel fields;

  private JLabel dateLabel;
  private JTextField dateField;

  private JLabel timeLabel;
  private JTextField timeField;

  private JLabel locationLabel;
  private JTextField locationField;

  private JLabel organizationLabel;
  private JTextField organziationField;

  private JLabel activityLabel;
  private JComboBox<String> activityField;
  private String[] activities = { 
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

  private JLabel sourceLabel;
  private JComboBox<String> sourceField;
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

  private JLabel attackerAddressLabel;
  private JTextField attackerAddressField;
  
  private JLabel victimAddressLabel;
  private JTextField victimAddressField;

  // "Actions Taken" pane properties
  private JScrollPane actionsTaken;
  private JLabel actionsTakenLabel;
  private JTextArea actionsTakenField; 

  // "Buttons" pane properties
  private JPanel buttons;

  private void clearTextComponents() {
    for (Component c: this.getComponents()) {
      if (c instanceof JTextField) {
        ((JTextField) c).setText(null);
      } else if (c instanceof JTextArea) {
        ((JTextArea) c).setText(null);
      } 
    }
  }

  private JButton submitButton;
  private ActionListener submitHandler;
  private class submitHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      Report report = new Report()
        .setDate(dateField.getText())
        .setTime(timeField.getText())
        .setLocation(locationField.getText())
        .setOrganization(organziationField.getText())
        .setSource(sourceField.getSelectedItem().toString()) 
        .setActivity(activityField.getSelectedItem().toString())
        .setAttackerAddress(attackerAddressField.getText())
        .setVictimAddress(victimAddressField.getText())
        .setActionsTaken(actionsTakenField.getText());
      report.print();
      clearTextComponents();
    } 
  }

  private JButton cancelButton;
  private ActionListener cancelHandler;
  private class cancelHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      clearTextComponents();
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

  public UserInterface() {
    size = new Dimension(350,500);
    image = new ImageIcon("src/main/resources/ghost.png").getImage();
    tabs = new JTabbedPane();

    tab1 = new JPanel();
    fields = new JPanel();
    actionsTaken = new JScrollPane();
    buttons = new JPanel();

    dateLabel = new JLabel("Date");
    fields.add(dateLabel);
    dateField = new JTextField();
    fields.add(dateLabel);

    locationLabel = new JLabel("Location");
    fields.add(locationLabel);
    locationField = new JTextField();
    fields.add(locationField);

    organizationLabel = new JLabel("Organization");
    fields.add(organizationLabel);
    organziationField = new JTextField();
    fields.add(organziationField);

    activityLabel = new JLabel("Activity");
    fields.add(activityLabel);
    activityField = new JComboBox<String>(activities);
    activityField.add(actionsTakenField);
    
    sourceLabel = new JLabel("Source");
    fields.add(sourceLabel);
    sourceField = new JComboBox<String>(sources);
    fields.add(sourceField);

    attackerAddressLabel = new JLabel("Attacker IP Address");
    fields.add(attackerAddressLabel);
    attackerAddressField = new JTextField();
    fields.add(attackerAddressField);

    victimAddressLabel = new JLabel("Victim IP Address");
    fields.add(victimAddressLabel);
    victimAddressField = new JTextField();
    fields.add(victimAddressField);

    actionsTakenLabel = new JLabel("Actions Taken");
    fields.add(actionsTakenLabel);
    actionsTakenField = new JTextArea();
    actionsTakenField.setLineWrap(true);
    actionsTakenField.setWrapStyleWord(true);
    actionsTaken.setViewportView(actionsTakenField);

    submitButton = new JButton("Submit");
    submitHandler = new submitHandler();
    submitButton.addActionListener(submitHandler);
    buttons.add(submitButton);
    cancelButton = new JButton("Cancel");
    cancelHandler = new cancelHandler();
    cancelButton.addActionListener(cancelHandler);
    buttons.add(cancelButton);
  }
  
  public void display() {
    fields.setLayout(new GridLayout(8, 2, 5, 5));
    buttons.setLayout(new GridLayout(1, 2, 5, 5));
    tab1.setLayout(new BorderLayout());
    tab1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    
    tab1.add(fields, BorderLayout.NORTH);
    tab1.add(actionsTaken, BorderLayout.CENTER);
    tab1.add(buttons, BorderLayout.SOUTH);
    tabs.addTab("Report", null, tab1, "Submit a report");

    this.setTitle("Spiritbox");
    this.setIconImage(image);
    this.add(tabs);
    this.setSize(size);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
}