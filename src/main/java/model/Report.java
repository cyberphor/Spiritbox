package main.java.model;
import java.time.LocalDateTime;

public class Report {
  private String reportNumber;
  private LocalDateTime dateTimeGroup;
  private String location;
  private String organization;
  private String detectionMethod;
  private String tacticDetected;
  private String attackerAddress;
  private String victimAddress;
  private String actionsTaken;

  public void setReportNumber() {
    reportNumber = LocalDateTime.now().toString();
  }

  public Report() {
    setReportNumber();
    System.out.println(reportNumber);
  }
}