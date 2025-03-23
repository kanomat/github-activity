package net.githubactivity;

import net.githubactivity.HttpConnection;
import java.io.IOException;


public class App {
    public static void main(String[] args) {
        String username = args[0];
        try {
            String inJson = HttpConnection.response(username);
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
