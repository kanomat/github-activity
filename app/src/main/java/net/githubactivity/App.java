package net.githubactivity;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class App {
    private static String response(String username) throws IOException, InterruptedException {
        URI uri = URI.create("https://api.github.com/users/" + username + "/events");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == HttpURLConnection.HTTP_NOT_FOUND) {
            throw new InterruptedIOException("User not found");
        }
        return response.body();
    }

    public static void main(String[] args) {
        String username = args[0];
        try {
            String inJson = response(username);
            UserActivityData data = GithubParser.parseGithubJson(inJson);
            if (data == null) {
                System.out.println("No activity found");
            } else {
                System.out.println(data);
            }
        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
