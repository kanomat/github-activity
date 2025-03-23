package net.githubactivity;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GithubParser {
    private static PushData parsePushData(JsonObject json) {
        String repo = json.getJsonObject("repo").getString("name");
        ZonedDateTime time = ZonedDateTime.parse(json.getString("created_at"), DateTimeFormatter.ISO_DATE_TIME);

        return new PushData(repo, time);
    }

    private static CreateData parseCreateData(JsonObject json) {
        String repo = json.getJsonObject("repo").getString("name");
        ZonedDateTime time = ZonedDateTime.parse(json.getString("created_at"), DateTimeFormatter.ISO_DATE_TIME);

        return new CreateData(repo, time);
    }

    private static StarData parseStarData(JsonObject json) {
        String repo = json.getJsonObject("repo").getString("name");
        ZonedDateTime time = ZonedDateTime.parse(json.getString("created_at"), DateTimeFormatter.ISO_DATE_TIME);

        return new StarData(repo, time);
    }

    private static IssueCommentData parseIssueCommentData(JsonObject json) {
        String repo = json.getJsonObject("repo").getString("name");
        ZonedDateTime time = ZonedDateTime.parse(json.getString("created_at"), DateTimeFormatter.ISO_DATE_TIME);
        int number = json.getJsonObject("payload").getJsonObject("issue").getInt("number");

        return new IssueCommentData(repo, number, time);
    }

    private static PullRequestData parsePullRequestData(JsonObject json) {
        String repo = json.getJsonObject("repo").getString("name");
        ZonedDateTime time = ZonedDateTime.parse(json.getString("created_at"), DateTimeFormatter.ISO_DATE_TIME);
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
                default -> ActivityLogger.logger.warning("Unknown event type: " + event.getString("type"));
            }
        }
        return userData;
    }
}
