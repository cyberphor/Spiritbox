import java.time.LocalDateTime;

/*
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
        //report.getReport();

        // show menu
        Menu menu = new Menu();
        menu.initialize();
    }
}
*/

import javax.swing.*;  
public class Main { 
    public static void main(String[] args) {  
        JFrame f = new JFrame();
        JTabbedPane tp = new JTabbedPane(); 
        JPanel p1 = new JPanel();  
        JPanel p2 = new JPanel();  
        JPanel p3 = new JPanel(); 
        JPanel p4 = new JPanel(); 

        // add stuff to pane 1
        // p1.add();

        // add stuff to pane 2
        // p2.add();

        // add stuff to pane 3
        // p3.add();

        // add stuff to pane 4
        // p4.add();

        // add the tabs to the pane
        tp.setBounds(50,50,200,200);  
        tp.add("Add", p1);  
        tp.add("Search", p2);  
        tp.add("Edit", p3);  
        tp.add("Monitor", p4); 

        // add the pane to the frame
        f.add(tp); 
        f.setSize(400,400);  
        f.setLayout(null);  
        f.setVisible(true);
    }
} 