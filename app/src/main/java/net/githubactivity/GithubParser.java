package net.githubactivity;

import javax.json.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GithubParser {
    public static UserActivityData parseGithubJson(String jsonString) throws IOException {
        Logger logger = Logger.getLogger("GithubParser");
        FileHandler fileHandler = new FileHandler("app.log");
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);

        JsonReader reader = Json.createReader(new StringReader(jsonString));
        JsonArray events = reader.readArray();
        if (events.isEmpty())
            return new UserActivityData("");
        String username = events.getJsonObject(0)
                .getJsonObject("actor")
                .getString("login");

        var userData = new UserActivityData(username);
        for (var event : events.getValuesAs(JsonObject.class)) {
            String repo = event.getJsonObject("repo").getString("name");
            String time = event.getString("created_at");
            switch (event.getString("type")) {
                case "PushEvent" -> {
                    var activityData = new ActivityData(repo, time);
                    userData.addPushData(activityData);
                }
                case "WatchEvent" -> {
                    var activityData = new ActivityData(repo, time);
                    userData.addStarData(activityData);
                }
                default -> logger.warning("Unknown event type: " + event.getString("type"));
            }
        }
        return userData;
    }
}
