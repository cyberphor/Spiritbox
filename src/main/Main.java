import java.time.LocalDateTime;

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

public class Main {
    public static void main(String[] args) {
        //    
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
*/