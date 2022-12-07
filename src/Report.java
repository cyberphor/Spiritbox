import java.time.LocalDateTime;

public class Report {
    private String Number;
    public void getNumber() { System.out.println(this.Number); }
    public void setNumber(String number) { this.Number = "C9L-" + number; }

    private LocalDateTime DateTimeGroup;
    public void getDateTimeGroup() { System.out.println(this.Number); }
    public void setDateTimeGroup(LocalDateTime dateTimeGroup) { this.DateTimeGroup = dateTimeGroup; }

    public Indicator indicator = new Indicator();

    private String Location;
    public void getLocation() { System.out.println(this.Location); }
    public void setLocation(String location) { this.Location = location; }

    private Integer NumberOfSystemsAffected;
    public void getNumberOfSystemsAffected() { System.out.println(this.NumberOfSystemsAffected); }
    public void setNumberOfSystemsAffected(Integer numberOfSystemsAffected) { this.NumberOfSystemsAffected = numberOfSystemsAffected; }

    private String ActionsTaken;
    public void getActionsTaken() { System.out.println(this.ActionsTaken); }
    public void setActionsTaken(String actionsTaken) { this.ActionsTaken = actionsTaken; }
}