import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Report report = new Report();
        report.setNumber("1");
        report.getNumber();
        report.indicator.setValue("1.1.1.1");
        report.indicator.getValue();
    }
}