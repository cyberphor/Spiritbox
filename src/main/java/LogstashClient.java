package main.java;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;

public class LogstashClient {
  public static void send(Report report) {
    HttpClient client;
    URI uri;
    BodyPublisher requestBodyHandler;
    HttpRequest request;
    BodyHandler<String> responseBodyHandler;
    HttpResponse<?> response;

    client = HttpClient.newHttpClient();
    uri = URI.create("http://localhost:1337");
    requestBodyHandler = BodyPublishers.ofString(report.toString());
    request = HttpRequest.newBuilder()
      .uri(uri)
      .header("Content-Type", "application/json")
      .POST(requestBodyHandler)
      .build();
    responseBodyHandler = BodyHandlers.ofString();

    try {
      response = client.send(request, responseBodyHandler);
      System.out.println(response.body()); // TODO: log response
    } catch (Exception e) {
      // TODO: ???
    }
  }
}
