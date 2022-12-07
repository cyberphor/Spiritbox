import java.time.LocalDateTime;

public class Indicator {
    private String Source;
    public void getSource() { System.out.println(this.Source); }
    public void setSource(String source) { this.Source = source; }

    private String Value;
    public void getValue() { System.out.println(this.Value); }
    public void setValue(String value) { this.Value = value; }

    private enum Direction { INBOUND, OUTBOUND };

    private LocalDateTime LastSeen;
    public void getLastSeen() { System.out.println(this.LastSeen); }
    public void setLastSeen(LocalDateTime lastSeen) { this.LastSeen = lastSeen; }
}