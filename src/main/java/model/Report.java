package main.java.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Report {
  private String reportNumber;
  private String date;
  private String time;
  private String location;
  private String organization;
  private String activity;
  private String source;
  private String attackerAddress;
  private String victimAddress;
  private String actionsTaken;

  private Boolean isIp4Address(String address) {
    if (address.contains(".") && address.length() >= 7 && address.length() <= 15) {
      String[] octets = address.split("\\.");
      if (octets.length == 4) {
        // check if each octet is <= 255
        return true;
      }
    }
    return false;
  }

  public String getReportNumber() { return reportNumber; }
  public String getDate() { return date; }
  public String getTime() { return time; }
  public String getLocation() { return location; }
  public String getOrganization() { return organization; }
  public String getActivity() { return activity; }
  public String getSource() { return source; }
  public String getAttackerAddress() { return attackerAddress; }
  public String getVictimAddress() { return victimAddress; }
  public String getActionsTaken() { return actionsTaken; }

  public Report setReportNumber() {
    String pattern = "yyMMddHHmmssSS";
    DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
    String reportNumber = LocalDateTime.now().format(format);
    this.reportNumber = String.format("C9L-%s", reportNumber);
    return this;
  }

  public Report setDate(String date) {
    this.date = date;
    return this;
  }

  public Report setTime(String time) {
    this.time = time;
    return this;
  }

  public Report setLocation(String location) {
    this.location = location;
    return this;
  }

  public Report setOrganization(String organization) {
    this.organization = organization;
    return this;
  }
   
  public Report setActivity(String activity) {
    this.activity = activity;
    return this;
  }

  public Report setSource(String source) {
    this.source = source;
    return this;
  } 

  public Report setAttackerAddress(String attackerAddress) {
    if (isIp4Address(attackerAddress)) {
      this.attackerAddress = attackerAddress;
    }
    return this;
  }

  public Report setVictimAddress(String victimAddress) {
    if (isIp4Address(victimAddress)) {
      this.victimAddress = victimAddress;
    }
    return this;
  }

  public Report setActionsTaken(String actionsTaken) {
    this.actionsTaken = actionsTaken;
    return this;
  }
  
  @Override
  public String toString() {
    return String.format("""
      "{
        'reportNumber': '%s',
        'date': '%s',
        'time': '%s',
        'location': '%s',
        'organization': '%s',
        'activity': '%s',
        'source': '%s',
        'attackerAddress': '%s',
        'victimAddress': '%s',
        'actionsTaken': '%s'
      }"
      """,
        reportNumber, 
        date,
        time,
        location,
        organization,
        activity,
        source, 
        attackerAddress,
        victimAddress,
        actionsTaken);
  }

  public void print() {
    System.out.println(this.toString());
  }

  public Report() {
    setReportNumber();
  }
}