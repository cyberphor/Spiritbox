import javax.swing.*;

public class UserInterface extends JFrame {
    public UserInterface() {
        JTabbedPane tabbedPane = new JTabbedPane();

        ReportTab tab1 = new ReportTab();
        SearchTab tab2 = new SearchTab();
        EditTab tab3 = new EditTab();
        MonitorTab tab4 = new MonitorTab();

        tabbedPane.add("Report", tab1);
        tabbedPane.add("Search", tab2);
        tabbedPane.add("Edit", tab3);
        tabbedPane.add("Monitor", tab4);  
    
        add(tabbedPane);
    }
}
