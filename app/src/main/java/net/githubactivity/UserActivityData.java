package net.githubactivity;

import java.util.ArrayList;

public class UserActivityData {
    private final String username;
    private final ArrayList<ActivityData> activities;

    public UserActivityData(String username) {
        this.username = username;
        activities = new ArrayList<>();
    }

    public void addActivity(ActivityData activity) {
        activities.add(activity);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("User: ").append(username).append("\n");
        for (var activity : activities) {
            sb.append(activity.toString()).append("\n");
        }
        return sb.toString();
    }
}
