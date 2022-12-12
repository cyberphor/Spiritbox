package view.desktop;
import java.awt.Dimension;

import javax.swing.*;

public class UserInterface extends JFrame {
    public UserInterface() {
        JTabbedPane tabbedPane = new JTabbedPane();
        ReportTab tab1 = new ReportTab();
        SearchTab tab2 = new SearchTab();
        EditTab tab3 = new EditTab();
        MonitorTab tab4 = new MonitorTab();

        tabbedPane.addTab("Report", null, tab1, "Add a report");
        tabbedPane.addTab("Search", null, tab2, "Search for intel");
        tabbedPane.addTab("Edit", null, tab3, "Edit an indicator");
        tabbedPane.addTab("Monitor", null, tab4, "Monitor for intel");  
        
        setTitle("Spiritbox");
        setIconImage(new ImageIcon("src/view/desktop/ghost.png").getImage());
        setMinimumSize(new Dimension(300,400));
        setSize(500,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(tabbedPane);
    }
}
