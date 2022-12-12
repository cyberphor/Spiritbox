package model;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Report {
    private String Number;
    public void getNumber() { System.out.println(this.Number); }
    public void setNumber(String number) { this.Number = "C9L-" + number; }

    private LocalDateTime DateTimeGroup;
    public void getDateTimeGroup() { System.out.println(this.DateTimeGroup); }
    public void setDateTimeGroup(LocalDateTime dateTimeGroup) { this.DateTimeGroup = dateTimeGroup; }

    private String Location;
    public void getLocation() { System.out.println(this.Location); }
    public void setLocation(String location) { this.Location = location; }

    private Integer SystemsAffected;
    public void getSystemsAffected() { System.out.println(this.SystemsAffected); }
    public void setSystemsAffected(Integer systemsAffected) { this.SystemsAffected = systemsAffected; }

    private String ActionsTaken;
    public void getActionsTaken() { System.out.println(this.ActionsTaken); }
    public void setActionsTaken(String actionsTaken) { this.ActionsTaken = actionsTaken; }

    ArrayList<Indicator> indicators = new ArrayList<Indicator>();
    // print the value of each indicator in indicators
    public void getIndicators() {
        for (Indicator indicator: indicators) {
            indicator.getSource();
            indicator.getValue();
            indicator.getDirection();
            indicator.getLastSeen();
        }
    }

    public void getReport() {
        this.getNumber();
        this.getDateTimeGroup();
        this.getIndicators();
        this.getLocation();
        this.getSystemsAffected();
        this.getActionsTaken();
    }
}