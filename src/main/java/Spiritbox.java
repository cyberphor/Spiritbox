package main.java;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

// declares the "Spiritbox" class
class Spiritbox {

  // declares a window "Frame"
  private JFrame frame;
  private Dimension size;
  private Image image;

  // declares a "Fields" pane
  private JPanel fields;
  private JLabel dateLabel;
  private JTextField dateField;
  private class DateFocusHandler implements FocusListener {
    private String placeholder = "YYYY MMM DD";
    public DateFocusHandler() {
      dateField.setForeground(Color.GRAY);
      dateField.setFont(new Font("",Font.ITALIC,12)); // TODO: change this, it's ugly
      dateField.setText(placeholder);
    }
    @Override
    public void focusGained(FocusEvent e) {
      if (dateField.getText().equals(placeholder)) {
        dateField.setText("");
      }
    }
    @Override
    public void focusLost(FocusEvent e) {
      if (dateField.getText().equals("")) {
        dateField.setText(placeholder);
      }
    }
  };
  private DateFocusHandler dateFocusHandler;
  private JLabel timeLabel; 
  private JTextField timeField;
  private class TimeFocusHandler implements FocusListener {
    private String placeholder = "1000";
    public TimeFocusHandler() {
      timeField.setText(placeholder);
      timeField.setForeground(Color.GRAY);
      timeField.setFont(new Font("",Font.ITALIC,12)); // TODO: change this, it's ugly
    }
    @Override
    public void focusGained(FocusEvent e) {
      if (timeField.getText().equals(placeholder)) {
        timeField.setText("");
      }
    }
    @Override
    public void focusLost(FocusEvent e) {
      if (timeField.getText().equals("")) {
        timeField.setText(placeholder);
      }
    }
  };
  private TimeFocusHandler timeFocusHandler;
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

  // declares a method for clearing the text fields
  private void clearTextComponents() {
    actionsTakenField.setText(null);
    for (Component c: fields.getComponents()) {
      if (c instanceof JTextField) {
        ((JTextField) c).setText(null);
      }
    }
  }

  // declares the HTTP objects needed to send a Report
  private HttpClient httpClient;
  private BodyPublisher requestBodyHandler;
  private HttpRequest request;
  private BodyHandler<String> responseBodyHandler;
  private HttpResponse<?> response;

  // declares a "Buttons" pane
  private JPanel buttons;
  private JButton submitButton;
  private class SubmitHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      URI uri = URI.create("http://localhost:1337"); // TODO: change 
      try {
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
        requestBodyHandler = BodyPublishers.ofString(report.toString());
        request = HttpRequest.newBuilder()
          .uri(uri)
          .header("Content-Type", "application/json")
          .POST(requestBodyHandler)
          .build();
        responseBodyHandler = BodyHandlers.ofString();
        response = httpClient.send(request, responseBodyHandler);
        JOptionPane.showMessageDialog(null, response.body(), "Server Reply", JOptionPane.OK_OPTION);
        clearTextComponents();
      } catch(InterruptedException err) {
        JOptionPane.showMessageDialog(null, "Please wait 30 seconds and try again.", ("Error: " + err), JOptionPane.ERROR_MESSAGE);
      } catch(IOException err) {
        JOptionPane.showMessageDialog(null, "Server unavailable.", ("Error: " + err), JOptionPane.ERROR_MESSAGE);
      }
    } 
  }
  private SubmitHandler submitHandler;
  private JButton cancelButton;
  private class CancelHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      clearTextComponents();
    } 
  }
  private CancelHandler cancelHandler;

  // declares "Tab" panes
  private JPanel tab1;
  private JTabbedPane tabs;

  // constructor method for the "Spiritbox" class
  public Spiritbox() {

    // inits the window "Frame"
    frame = new JFrame();
    size = new Dimension(400,500);
    image = new ImageIcon("src/main/resources/ghost.png").getImage();

    // inits the "Fields" pane
    fields = new JPanel();
    dateLabel = new JLabel("Date");
    dateField = new JTextField();
    dateFocusHandler = new DateFocusHandler();
    timeLabel = new JLabel("Time");
    timeField = new JTextField();
    timeFocusHandler = new TimeFocusHandler();
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
    httpClient = HttpClient.newHttpClient();
    submitButton = new JButton("Submit");
    submitHandler = new SubmitHandler();
    cancelButton = new JButton("Cancel");
    cancelHandler = new CancelHandler();
    
    // inits "Tab" panes
    tab1 = new JPanel();
    tabs = new JTabbedPane();
  }
  
  // declares a "Display" method to present all panes to the user
  public void display() {

    // adds fields to the "Fields" pane
    fields.setLayout(new GridLayout(9, 2, 5, 5));
    fields.add(dateLabel);
    dateField.addFocusListener(dateFocusHandler);
    fields.add(dateField);
    fields.add(timeLabel);
    timeField.addFocusListener(timeFocusHandler);
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

    // adds the "Activity" field to the "Actions Taken" pane
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
    frame.setResizable(false);
    frame.setTitle("Spiritbox");
    frame.setIconImage(image);
    frame.add(tabs);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}