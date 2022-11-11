import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import javax.swing.*;

class ThreatActor {
    String name;
    String[] aliases;
    String description;
    LocalDateTime firstSeen;
    LocalDateTime lastSeen;
    String motivation; // fame, politics, money, etc.
    String intent; // deny, disrupt, manipulate, etc.
    String orientation; // insider, nearsider, outsider  
    String sophistication; // low, medium, high 
    String[] resources; // ability to sustain ops; infrastructure
    Event[] activity;
}

class Event {
    String name;
    String description;
    Indicator[] indicator; 
}

class Indicator {
    String name; // what does this indicator do?
    String description;
    String type; // hash, ip, etc. 
    String pattern;
    String patternVersion;
    LocalDateTime validFrom;
    LocalDateTime validUntil;
    String[] techniques; // MITRE ATT&CK
}

class IndicatorForm extends JFrame { 
    public IndicatorForm() {
        Container container = this.getContentPane();  
        SpringLayout layout = new SpringLayout();  
        container.setLayout(layout);  

        JLabel reportNumberField = new JLabel("Report Number");
        layout.putConstraint(SpringLayout.WEST,reportNumberField,5,SpringLayout.WEST,container); 
        layout.putConstraint(SpringLayout.NORTH,reportNumberField,5,SpringLayout.NORTH,container); 
        container.add(reportNumberField);

        JTextField reportNumberValue = new JTextField(15);
        layout.putConstraint(SpringLayout.WEST,reportNumberValue,5,SpringLayout.EAST,reportNumberField); 
        layout.putConstraint(SpringLayout.NORTH,reportNumberValue,5,SpringLayout.NORTH,container); 
        container.add(reportNumberValue);

        JLabel locationField = new JLabel("Location");
        layout.putConstraint(SpringLayout.WEST,locationField,5,SpringLayout.WEST,container); 
        layout.putConstraint(SpringLayout.NORTH,locationField,5,SpringLayout.NORTH,container); 
        container.add(locationField);

        JTextField locationValue = new JTextField();
        layout.putConstraint(SpringLayout.WEST,locationValue,5,SpringLayout.EAST,locationField); 
        layout.putConstraint(SpringLayout.NORTH,locationValue,5,SpringLayout.NORTH,container); 
        container.add(locationValue);

        /*
        dateTimeGroup = new JLabel("Date Time Group");
        windowPane.add(dateTimeGroup);
        
        tactic = new JLabel("Tactic");
        windowPane.add(tactic);

        sourceIpAddress = new JLabel("Source IP Address");
        windowPane.add(sourceIpAddress);

        destinationIpAddress = new JLabel("Destination IP Address");
        windowPane.add(destinationIpAddress);

        destinationHostname = new JLabel("Destination Hostname");
        windowPane.add(destinationHostname);

        direction = new JLabel("Direction");
        windowPane.add(direction);

        numberOfSystemsAffected = new JLabel("Number of Systems Affected");
        windowPane.add(numberOfSystemsAffected);

        actionsTaken = new JLabel("Actions Taken");
        windowPane.add(actionsTaken);
        
        identificationMethod = new JLabel("Identification Method");        
        windowPane.add(identificationMethod);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> 
            System.out.println("Mouse clicked")
        );
        windowPane.add(addButton);

        */
    }
}

public class Main {
    public static void main(String[] args) {
        IndicatorForm indicatorForm = new IndicatorForm();
        indicatorForm.setTitle("Add Indicator");
        indicatorForm.pack();
        indicatorForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        indicatorForm.setVisible(true); 
    }
}

/*
    EXAMPLE USE CASE
        reportNumber: C9L-001
        location: Bunda, Ziwa
        dateTimeGroup: 26 SEPT 1000 2022 UTC
        tactic: Reconnaissance
        sourceIPAddress: 9.9.9.9
        sourceHostname: null
        destinationIPAddress: 1.1.1.1
        destinationHostname: XYZ5000SDC
        direction: Inbound
        numberofSystemsAffected: 1
        actionsTaken: Blocked IP address at perimeter firewall
        identificationMethod: Palo Alto firewall
        
    REFERENCES
        https://www.geeksforgeeks.org/java-swing-simple-user-registration-form/
*/
