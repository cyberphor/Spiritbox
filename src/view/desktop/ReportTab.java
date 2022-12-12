package view.desktop;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ReportTab extends JPanel {
    public ReportTab() {
        JPanel form = new JPanel(); 
        form.setLayout(new GridLayout(5, 2, 5, 5)); 

        JLabel lbReportNumber = new JLabel("Report Number");
        form.add(lbReportNumber);

        JTextField tfReportNumber = new JTextField();
        form.add(tfReportNumber);

        JLabel lbDateTimeGroup = new JLabel("Date-Time Group");
        form.add(lbDateTimeGroup);

        JTextField tfDateTimeGroup = new JTextField();
        form.add(tfDateTimeGroup);

        JLabel lbLocation = new JLabel("Location");
        form.add(lbLocation);

        JTextField tfLocation = new JTextField();
        form.add(tfLocation);

        JLabel lbSystemsAffected = new JLabel("Systems Affected");
        form.add(lbSystemsAffected);

        JTextField tfSystemsAffected = new JTextField();
        form.add(tfSystemsAffected);
 
        JLabel lbActionsTaken = new JLabel("Actions Taken");
        form.add(lbActionsTaken);

        JTextArea tfActionsTaken = new JTextArea(1,0);
        tfActionsTaken.setLineWrap(true);
        tfActionsTaken.setWrapStyleWord(true);
        form.add(new JScrollPane(tfActionsTaken));

        JButton buttonOK = new JButton("OK");
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                String number = tfNumber.getText();
                String dateTimeGroup = tfDateTimeGroup.getText();
                String location = tfLocation.getText();
                lbWelcome.setText(number);
                */
            }
        });

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfReportNumber.setText("");
                tfDateTimeGroup.setText("");
                tfLocation.setText("");
            }
        });

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, 5, 5));
        buttons.add(buttonOK);
        buttons.add(buttonCancel);

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(form, BorderLayout.NORTH);
        add(buttons, BorderLayout.SOUTH);
    }
}
