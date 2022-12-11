import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.WindowConstants;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.setTitle("Spiritbox");
        ui.setSize(500,600);
        ui.setMinimumSize(new Dimension(300,400));
        ui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ui.setIconImage(new ImageIcon("ghost.png").getImage());
        ui.setVisible(true);
    }
}

/*        
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
 */
