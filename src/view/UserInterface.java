package view;
import java.awt.Dimension;
import javax.swing.*;

class tabbedPane extends JTabbedPane {
  public tabbedPane() {
    addTab("Create", null, new CreateTab(), "Create a report");
    addTab("Search", null, new SearchTab(), "Search for reports");
    addTab("Edit", null, new EditTab(), "Edit a report");
    addTab("Inbox", null, new InboxTab(), "Check for reports");
  }
}

public class UserInterface extends JFrame {
  public UserInterface() {
    setTitle("Spiritbox");
    setIconImage(new ImageIcon("src/view/ghost.png").getImage());
    setMinimumSize(new Dimension(300,400));
    setSize(500,600);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    add(new tabbedPane());
  }
}
