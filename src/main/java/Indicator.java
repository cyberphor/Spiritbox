package main.java;
import java.time.LocalDateTime;

public class Indicator {
  private String Source;
  private String Value;
  private LocalDateTime LastSeen;

  public String getSource() { return Source; }
  public String getValue() { return Value; }
  public LocalDateTime getLastSeen() { return LastSeen; }

  public Indicator setSource(String source) { 
    this.Source = source;
    return this;
  }

  public Indicator setValue(String value) { 
    this.Value = value;
    return this;
  }

  public Indicator setLastSeen(LocalDateTime lastSeen) { 
    this.LastSeen = lastSeen;
    return this;
  }
}