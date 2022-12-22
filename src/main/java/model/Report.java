package main.java.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Report {
  private String reportNumber;
  private String dateTimeGroup;
  private String location;
  private String organization;
  private String indicatorSource;
  private String tacticDetected;
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
  public String getDateTimeGroup() { return dateTimeGroup; }
  public String getLocation() { return location; }
  public String getOrganization() { return organization; }
  public String getIndicatorSource() { return indicatorSource; }
  public String getTacticDetected() { return tacticDetected; }
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

  public Report setDateTimeGroup(String dateTimeGroup) {
    this.dateTimeGroup = dateTimeGroup;
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
  
  public Report setIndicatorSource(String indicatorSource) {
    this.indicatorSource = indicatorSource;
    return this;
  } 
  
  public Report setTacticDetected(String tacticDetected) {
    this.tacticDetected = tacticDetected;
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
        'dateTimeGroup': '%s',
        'location': '%s',
        'organization': '%s',
        'indicatorSource': '%s',
        'tacticDetected': '%s',
        'attackerAddress': '%s',
        'victimAddress': '%s',
        'actionsTaken': '%s'
      }"
      """,
        reportNumber, 
        dateTimeGroup,
        location,
        organization,
        indicatorSource, 
        tacticDetected,
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