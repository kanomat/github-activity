package net.githubactivity;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class ActivityData {
    protected final String repo;
    protected final ZonedDateTime time;
    protected final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ssZ");

    public ActivityData(String repo, ZonedDateTime time) {
        this.repo = repo;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Activity " + " for " + repo + " at " + time.format(formatter);
    }
}

class PushData extends ActivityData {
    public PushData(String repo, ZonedDateTime time) {
        super(repo, time);
    }

    @Override
    public String toString() {
        return "Pushed" + " to " + repo + " at " + time.format(formatter);
    }
}

class StarData extends ActivityData {
    public StarData(String repo, ZonedDateTime time) {
        super(repo, time);
    }

    @Override
    public String toString() {
        return "Starred " + repo + " at " + time.format(formatter);
    }
}

class CreateData extends ActivityData {
    public CreateData(String repo, ZonedDateTime time) {
        super(repo, time);
    }

    @Override
    public String toString() {
        return "Created " + repo + " at " + time.format(formatter);
    }
}

class PullRequestData extends ActivityData {
    private final String dest;
    private final String src;

    public PullRequestData(String repo, String dest, String src, ZonedDateTime time) {
        super(repo, time);
        this.dest = dest;
        this.src = src;
    }

    @Override
    public String toString() {
        return "Requested merge " + src + " to " + dest + " at " + time.format(formatter);
    }
}

class IssueCommentData extends ActivityData {
    private final int number;

    public IssueCommentData(String repo, int number, ZonedDateTime time) {
        super(repo, time);
        this.number = number;
    }

    @Override
    public String toString() {
        return "Commented issue #" + number + " for " + repo + " at " + time.format(formatter);
    }
}
