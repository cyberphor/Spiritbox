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
        setTitle("Add Indicator");
        setBounds(300, 90, 900, 600);
        JPanel windowPane = new JPanel();
        this.add(windowPane);

        JLabel reportNumberField = new JLabel("Report Number");
        reportNumberField.setLocation(50, 50);
        windowPane.add(reportNumberField);

        JTextField reportNumberValue = new JTextField();
        reportNumberValue.setColumns(20);
        reportNumberValue.setLocation(100, 100);
        windowPane.add(reportNumberValue);

        /*
        JLabel locationField = new JLabel("Location");
        windowPane.add(locationField);

        JTextField locationValue = new JTextField();
        windowPane.add(locationValue);

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
        */

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> 
            System.out.println("Mouse clicked")
        );
        // windowPane.add(addButton);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

}

public class Main {
    public static void main(String[] args) {
        IndicatorForm indicatorForm = new IndicatorForm();
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