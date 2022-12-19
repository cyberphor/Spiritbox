package main.java.model;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Report {
  private String reportNumber;
  private LocalDateTime dateTimeGroup;
  private String location;
  private Integer systemsAffected;
  private ArrayList<Indicator> indicators = new ArrayList<Indicator>();
  private String actionsTaken;

  public void setReportNumber(String reportNumber) {
    // if reportNumber < 1000
    // if reportNumber does not already exist 
    reportNumber = "C9L-" + reportNumber; 
  }

  public void setDateTimeGroup(LocalDateTime dateTimeGroup) { 
    DateTimeGroup = dateTimeGroup; 
  }

  public void setLocation(String location) { 
    Location = location;
  }

  public void setSystemsAffected(Integer systemsAffected) { 
    SystemsAffected = systemsAffected; 
  }

  public void setActionsTaken(String actionsTaken) { 
    ActionsTaken = actionsTaken; 
  }
}
