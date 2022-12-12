package model;
import java.time.LocalDateTime;

public class Indicator {
  private String Source;
  public String getSource() { return Source; }
  public void setSource(String source) { 
    this.Source = source; 
  }

  private String Value;
  public String getValue() { return Value; }
  public void setValue(String value) { 
    this.Value = value;
  }

  enum Direction { Inbound, Outbound };
  private Direction Direction;
  public Direction getDirection() { return Direction; }
  public void setDirection(Direction direction) { 
    this.Direction = direction; 
  }

  private LocalDateTime LastSeen;
  public LocalDateTime getLastSeen() { return LastSeen; }
  public void setLastSeen(LocalDateTime lastSeen) { 
    this.LastSeen = lastSeen;
  }
}