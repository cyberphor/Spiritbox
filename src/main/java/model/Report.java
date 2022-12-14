package main.java.model;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Report {
  private String ReportNumber;
  public String getReportNumber() { return ReportNumber; }
  public void setReportNumber(String reportNumber) {
    // if reportNumber < 1000
    // if reportNumber does not already exist 
    this.ReportNumber = "C9L-" + reportNumber; 
  }
  
  private LocalDateTime DateTimeGroup;
  public LocalDateTime getDateTimeGroup() { return DateTimeGroup; }
  public void setDateTimeGroup(LocalDateTime dateTimeGroup) { 
    this.DateTimeGroup = dateTimeGroup; 
  }
  
  private String Location;
  public String getLocation() { return Location; }
  public void setLocation(String location) { 
    this.Location = location;
  }
  
  private Integer SystemsAffected;
  public Integer getSystemsAffected() { return SystemsAffected; }
  public void setSystemsAffected(Integer systemsAffected) { 
    this.SystemsAffected = systemsAffected; 
  }
  
  private String ActionsTaken;
  public String getActionsTaken() { return ActionsTaken; }
  public void setActionsTaken(String actionsTaken) { 
    this.ActionsTaken = actionsTaken; 
  }

  ArrayList<Indicator> indicators = new ArrayList<Indicator>();
  public void getIndicators() {
    for (Indicator indicator: indicators) {
      indicator.getSource();
      indicator.getValue();
      indicator.getDirection();
      indicator.getLastSeen();
    }
  }

  public void getReport() {
    getReportNumber();
    getDateTimeGroup();
    getIndicators();
    getLocation();
    getSystemsAffected();
    getActionsTaken();
  }
}
