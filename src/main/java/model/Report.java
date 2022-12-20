package main.java.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Report {
  private String reportNumber;
  private String dateTimeGroup;
  private String location;
  private String organization;
  private String detectionMethod;
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
  public String getOrganization() { return organization;}
  public String getDetectionMethod() { return detectionMethod; }
  public String getTacticDetected() { return tacticDetected; }
  public String getAttackerAddress() { return attackerAddress; }
  public String getVictimAddress() { return victimAddress; }
  public String getActionsTaken() { return actionsTaken; }

  public Report setReportNumber() {
    String pattern = "yyMMddHHmmssSS";
    DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
    String number = LocalDateTime.now().format(format);
    reportNumber = String.format("C9L-%s", number);
    return this;
  }

  public Report setDateTimeGroup() {
    return this;
  }

  public Report setLocation() {
    return this;
  }

  public Report setOrganization() {
    return this;
  }
  
  public Report setDetectionMethod() {
    return this;
  } 
  
  public Report setTacticDetected() {
    return this;
  }

  public Report setAttackerAddress(String addr) {
    if (isIp4Address(addr)) {
        attackerAddress = addr;
    }
    return this;
  }

  public Report setVictimAddress(String addr) {
    if (isIp4Address(addr)) {
        victimAddress = addr;
    }
    return this;
  }

  public Report setActionsTaken() {
    return this;
  }
  
  @Override
  public String toString() {
    return String.format("""
      {
        "reportNumber": %s,
        "dateTimeGroup": %s,
        "location": %s,
        "organization": %s,
        "detectionMethod": %s,
        "tacticDetected": %s,
        "attackerAddress": %s,
        "victimAddress": %s,
        "actionsTaken": %s
      }""",
        reportNumber, 
        dateTimeGroup,
        location,
        organization,
        detectionMethod, 
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