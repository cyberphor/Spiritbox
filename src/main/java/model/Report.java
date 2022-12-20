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

  public void setReportNumber() {
    String dtfPattern = "yyMMddHHmmssSS";
    DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern(dtfPattern);
    String number = LocalDateTime.now().format(dtFormat);
    reportNumber = String.format("C9L-%s",number);
  }

  public void setDateTimeGroup() {
    
  }

  public void setLocation() {}
  public void setOrganization() {}
  public void setDetectionMethod() {} 
  public void setTacticDetected() {}
  public void setAttackerAddress() {}
  public void setVictimAddress() {}
  public void setActionsTaken() {}

  public String getReportNumber() { return reportNumber; }
  public String getDateTimeGroup() { return dateTimeGroup; }
  public String getLocation() { return location; }
  public String getOrganization() { return organization;}
  public String getDetectionMethod() { return detectionMethod; }
  public String getTacticDetected() { return tacticDetected; }
  public String getAttackerAddress() { return attackerAddress; }
  public String getVictimAddress() { return victimAddress; }
  public String getActionsTaken() { return actionsTaken; }
  
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