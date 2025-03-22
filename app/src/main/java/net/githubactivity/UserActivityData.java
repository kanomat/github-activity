package net.githubactivity;

import java.util.ArrayList;

public class UserActivityData {
    private ArrayList<ActivityData> pushes;
    private ArrayList<ActivityData> stars;
    private String user;

    public UserActivityData(String user) {
        this.user = user;
        pushes = new ArrayList<>();
        stars = new ArrayList<>();
    }

    public void AddPushData(ActivityData push) {
        pushes.add(push);
    }

    public void AddStarData(ActivityData star) {
        stars.add(star);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var push : pushes) {
            sb.append(user).append(" pushed to ").append(push.repo()).append(" at ").append(push.time()).append("\n");
        }
        for (var star : stars) {
            sb.append(user).append(" starred ").append(star.repo()).append(" at ").append(star.time()).append("\n");
        }
        return sb.toString();
    }
}
