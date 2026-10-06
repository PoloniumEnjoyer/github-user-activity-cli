public class Event {
    
    private final String type;
    private final String repoName;
    private final String action;

    public Event(String type, String repoName, String action) {

        this.type = type;
        this.repoName = repoName;
        this.action = action;
    }

    public String getType() {

        return type;
    }

    public String getRepoName() {

        return repoName;
    }

    public String getAction() {

        return action;
    }
}

