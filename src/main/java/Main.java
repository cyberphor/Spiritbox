package main.java;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Spiritbox {

  // declares a window "Frame"
  private JFrame frame;
  private Dimension size;
  private Image image;

  // declares a "Fields" pane
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
  private JLabel attackerAddressLabel; // TODO: replace w/an indicators option
  private JTextField attackerAddressField; // TODO: replace w/an indicators option
  private JLabel victimAddressLabel; // TODO: replace w/an indicators option
  private JTextField victimAddressField; // TODO: replace w/an indicators option

  // declares an "Actions Taken" pane
  private JScrollPane actionsTaken;
  private JLabel actionsTakenLabel;
  private JTextArea actionsTakenField; 

  // declares a "Buttons" pane
  private JPanel buttons;
  private void clearTextComponents() {
    actionsTakenField.setText(null);
    for (Component c: fields.getComponents()) {
      if (c instanceof JTextField) {
        ((JTextField) c).setText(null);
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
      report.send();
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

  // declares "Tab" panes
  private JPanel tab1;
  private JTabbedPane tabs;

  // constructor method for the "Spiritbox" class
  public Spiritbox() {

    // inits the window "Frame"
    frame = new JFrame();
    size = new Dimension(350,500);
    image = new ImageIcon("src/main/resources/ghost.png").getImage();

    // inits the "Fields" pane
    fields = new JPanel();
    dateLabel = new JLabel("Date");
    dateField = new JTextField();
    timeLabel = new JLabel("Time");
    timeField = new JTextField();
    locationLabel = new JLabel("Location");
    locationField = new JTextField();
    organizationLabel = new JLabel("Organization");
    organziationField = new JTextField();
    activityLabel = new JLabel("Activity");
    activityField = new JComboBox<String>(activities);
    sourceLabel = new JLabel("Source");
    sourceField = new JComboBox<String>(sources);
    attackerAddressLabel = new JLabel("Attacker IP Address"); 
    attackerAddressField = new JTextField();
    victimAddressLabel = new JLabel("Victim IP Address");
    victimAddressField = new JTextField();
    actionsTakenLabel = new JLabel("Actions Taken");

    // inits "Actions Taken" pane
    actionsTaken = new JScrollPane();
    actionsTakenField = new JTextArea();

    // inits "Buttons" pane
    buttons = new JPanel();
    submitButton = new JButton("Submit");
    submitHandler = new submitHandler();
    cancelButton = new JButton("Cancel");
    cancelHandler = new cancelHandler();
    
    // inits "Tab" panes
    tab1 = new JPanel();
    tabs = new JTabbedPane();
  }
  
  // declares a "Display" method to present all panes to the user
  public void display() {

    // adds fields to "Fields" pane
    fields.setLayout(new GridLayout(9, 2, 5, 5));
    fields.add(dateLabel);
    fields.add(dateField);
    fields.add(timeLabel);
    fields.add(timeField);
    fields.add(locationLabel);
    fields.add(locationField);
    fields.add(organizationLabel);
    fields.add(organziationField);
    fields.add(activityLabel);
    fields.add(activityField);
    fields.add(sourceLabel);
    fields.add(sourceField);
    fields.add(attackerAddressLabel);
    fields.add(attackerAddressField);
    fields.add(victimAddressLabel);
    fields.add(victimAddressField);
    fields.add(actionsTakenLabel);

    // adds fields to the "Fields" pane
    activityField.add(actionsTakenField);
    actionsTakenField.setLineWrap(true);
    actionsTakenField.setWrapStyleWord(true);
    actionsTaken.setViewportView(actionsTakenField);

    // adds buttons to the "Buttons" pane
    buttons.setLayout(new GridLayout(1, 2, 5, 5));
    submitButton.addActionListener(submitHandler);
    buttons.add(submitButton);
    cancelButton.addActionListener(cancelHandler);
    buttons.add(cancelButton);

    // adds all panes to "Tab 1" and then adds it the tabbed pane
    tab1.setLayout(new BorderLayout());
    tab1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    tab1.add(fields, BorderLayout.NORTH);
    tab1.add(actionsTaken, BorderLayout.CENTER);
    tab1.add(buttons, BorderLayout.SOUTH);
    tabs.addTab("Report", null, tab1, "Submit a report");

    // adds the tabbed pane to the window "Frame"
    frame.setSize(size);
    frame.setTitle("Spiritbox");
    frame.setIconImage(image);
    frame.add(tabs);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}

public class Main {
  public static void main(String[] args) {
    Spiritbox spiritbox = new Spiritbox();
    spiritbox.display();
  }
}