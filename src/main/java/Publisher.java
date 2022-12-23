package main.java;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

public class Publisher {
  public static void toLogstash(Report report) {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create("https://httpbin.org/post"))
      .POST(BodyPublishers.ofString(report.toString()))
      .build();

    try {
      HttpResponse<String> response = client.send(
        request, 
        HttpResponse.BodyHandlers.ofString());
      System.out.println(response.body());
    } catch (Exception e) {
      // TODO: ???
    }
  }
}
