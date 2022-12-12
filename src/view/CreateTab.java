package view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class createTabForm extends JPanel {
  public createTabForm() {
    setLayout(new GridLayout(5, 2, 5, 5)); 

    JLabel lbReportNumber = new JLabel("Report Number");
    add(lbReportNumber);

    JTextField tfReportNumber = new JTextField();
    add(tfReportNumber);

    JLabel lbDateTimeGroup = new JLabel("Date-Time Group");
    add(lbDateTimeGroup);

    JTextField tfDateTimeGroup = new JTextField();
    add(tfDateTimeGroup);

    JLabel lbLocation = new JLabel("Location");
    add(lbLocation);    

    JTextField tfLocation = new JTextField();   
    add(tfLocation); 

    JLabel lbSystemsAffected = new JLabel("Systems Affected");
    add(lbSystemsAffected); 

    JTextField tfSystemsAffected = new JTextField();  
    add(tfSystemsAffected);

    JLabel lbActionsTaken = new JLabel("Actions Taken"); 
    add(lbActionsTaken); 

    JTextArea tfActionsTaken = new JTextArea(1,0);
    tfActionsTaken.setLineWrap(true);
    tfActionsTaken.setWrapStyleWord(true);
    add(new JScrollPane(tfActionsTaken)); 
  }
}

class createTabButtons extends JPanel {
  public createTabButtons(JPanel form) {
    setLayout(new GridLayout(1, 2, 5, 5));

    JButton buttonOK = new JButton("OK");
    buttonOK.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (Component c: form.getComponents()) {
          if (c instanceof JTextField) {
            JTextField field = (JTextField) c;
            System.out.println(field.getText());
          } else if (c instanceof JTextArea) {
            JTextArea field = (JTextArea) c;
            System.out.println(field.getText());
          }
        }
      }
    }); 
    add(buttonOK);

    JButton buttonCancel = new JButton("Cancel");
    buttonCancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (Component c: form.getComponents()) {
          if (c instanceof JTextField) {
            JTextField field = (JTextField) c;
            field.setText("");
          } else if (c instanceof JTextArea) {
            JTextArea field = (JTextArea) c;
            field.setText("");
          }
        }
      }
    });    
    add(buttonCancel); 
  }
}

public class CreateTab extends JPanel {
  public CreateTab() {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    
    JPanel form = new createTabForm();
    add(form, BorderLayout.NORTH);

    JPanel buttons = new createTabButtons(form);
    add(buttons, BorderLayout.SOUTH);
  }
}