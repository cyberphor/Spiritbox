import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // declare report
        Report report = new Report();

        // edit report
        report.setNumber("1");
        report.setDateTimeGroup(LocalDateTime.now());
        report.setLocation("Ziwa");
        report.setSystemsAffected(1);
        report.setActionsTaken("None");

        // declare indicator(s)
        Indicator indicator = new Indicator();
        indicator.setSource("Firewall");
        indicator.setValue("1.1.1.1");
        indicator.setDirection(Direction.Inbound);
        indicator.setLastSeen(LocalDateTime.now());
        report.indicators.add(indicator);

        // get report
        report.getReport();
        Frame frame = new Frame();
        frame.init();
    }
}