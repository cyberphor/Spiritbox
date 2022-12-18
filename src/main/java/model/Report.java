package main.java.model;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Report {
  private String ReportNumber;
  private LocalDateTime DateTimeGroup;
  private String Location;
  private Integer SystemsAffected;
  private String ActionsTaken;
  private ArrayList<Indicator> indicators = new ArrayList<Indicator>();

  public void setReportNumber(String reportNumber) {
    // if reportNumber < 1000
    // if reportNumber does not already exist 
    ReportNumber = "C9L-" + reportNumber; 
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

  @Override
  public String toString() {
		String string = String.format("""
      { 
        "ReportNumber": "%s",
        "DateTimeGroup": "null", 
        "Location": "%s", 
        "SystemsAffected": "null", 
        "ActionsTaken": "null"
      }
      """, ReportNumber, DateTimeGroup, Location, SystemsAffected, ActionsTaken);
    return string;
	}
}
