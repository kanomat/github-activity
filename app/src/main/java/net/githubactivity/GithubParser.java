package net.githubactivity;

import javax.json.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GithubParser {
    private static PushData parsePushData(JsonObject json) {
        String repo = json.getJsonObject("repo").getString("name");
        String time = json.getString("created_at");

        return new PushData(repo, time);
    }

    private static CreateData parseCreateData(JsonObject json) {
        String repo = json.getJsonObject("repo").getString("name");
        String time = json.getString("created_at");

        return new CreateData(repo, time);
    }

    private static StarData parseStarData(JsonObject json) {
        String repo = json.getJsonObject("repo").getString("name");
        String time = json.getString("created_at");

        return new StarData(repo, time);
    }

    private static IssueCommentData parseIssueCommentData(JsonObject json) {
        String repo = json.getJsonObject("repo").getString("name");
        String time = json.getString("created_at");
        int number = json.getJsonObject("payload").getJsonObject("issue").getInt("number");

        return new IssueCommentData(repo, number, time);
    }

    private static PullRequestData parsePullRequestData(JsonObject json) {
        String repo = json.getJsonObject("repo").getString("name");
        String time = json.getString("created_at");
        String src = json.getJsonObject("payload")
                .getJsonObject("pull_request")
                .getJsonObject("head")
                .getString("label");
        String dest = json.getJsonObject("payload")
                .getJsonObject("pull_request")
                .getJsonObject("base")
                .getString("label");

        return new PullRequestData(repo, dest, src, time);
    }

    public static UserActivityData parseGithubJson(String jsonString) throws IOException {
        Logger logger = Logger.getLogger("GithubParser");
        FileHandler fileHandler = new FileHandler("app.log");
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);

        JsonReader reader = Json.createReader(new StringReader(jsonString));
        JsonArray events = reader.readArray();
        if (events.isEmpty())
            return null;
        String username = events.getJsonObject(0)
                .getJsonObject("actor")
                .getString("login");

        var userData = new UserActivityData(username);
        for (var event : events.getValuesAs(JsonObject.class)) {
            switch (event.getString("type")) {
                case "PushEvent" -> userData.addActivity(parsePushData(event));
                case "WatchEvent" -> userData.addActivity(parseStarData(event));
                case "PullRequestEvent" -> userData.addActivity(parsePullRequestData(event));
                case "IssueCommentEvent" -> userData.addActivity(parseIssueCommentData(event));
                case "CreateEvent" -> userData.addActivity(parseCreateData(event));
                default -> logger.warning("Unknown event type: " + event.getString("type"));
            }
        }
        return userData;
    }
}
