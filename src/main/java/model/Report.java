package main.java.model;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class Report {
  private String reportNumber;
  private String dateTimeGroup;
  private String location;
  private String organization;
  private String detectionMethod;
  private String tacticDetected;
  private String attackerAddress;
  private String victimAddress;
  private String actionsTaken;

  public void setReportNumber() {
    reportNumber = new Random().toString();
  }

  public void setDateTimeGroup() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd HHmm 'T' MMM yy");
    LocalDateTime dt = new LocalDateTime();
    dt.now();
    dt.format(dtf);
    dateTimeGroup = dt.toString();
  }

  public Report() {
    setReportNumber();
    setDateTimeGroup();
    System.out.println(reportNumber);
    System.out.println(dateTimeGroup);
  }
}