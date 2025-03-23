package net.githubactivity;

public abstract class ActivityData {
    protected final String repo;
    protected final String time;

    public ActivityData(String repo, String time) {
        this.repo = repo;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Activity at " + time + " for " + repo;
    }
}

class PushData extends ActivityData {
    public PushData(String repo, String time) {
        super(repo, time);
    }

    @Override
    public String toString() {
        return "Pushed at " + time + " to " + repo;
    }
}

class StarData extends ActivityData {
    public StarData(String repo, String time) {
        super(repo, time);
    }

    @Override
    public String toString() {
        return "Starred " + repo + " at " + time;
    }
}

class CreateData extends ActivityData {
    public CreateData(String repo, String time) {
        super(repo, time);
    }

    @Override
    public String toString() {
        return "Created " + repo + " at " + time;
    }
}

class PullRequestData extends ActivityData {
    private final String dest;
    private final String src;

    public PullRequestData(String repo, String dest, String src, String time) {
        super(repo, time);
        this.dest = dest;
        this.src = src;
    }

    @Override
    public String toString() {
        return "Requested merge " + src + " to " + dest + " at " + time;
    }
}

class IssueCommentData extends ActivityData {
    private int number;

    public IssueCommentData(String repo, int number, String time) {
        super(repo, time);
        this.number = number;
    }

    @Override
    public String toString() {
        return "Commented issue #" + number + " for " + repo + " at " + time;
    }
}
