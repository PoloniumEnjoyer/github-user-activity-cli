public class EventFormatter {

    public static String capitalize(String word) {

        if(word == null || word.isEmpty()) {

            return "Updated";
        }

        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
    
    public static String format(Event event) {

        String type = event.getType();
        String repo = event.getRepoName();
        String action = event.getAction();

        if(type == null || repo == null) {

            return "Unknown activity";
        }

        switch(type) {

            case "PushEvent":

                return "Pushed to " + repo;

            case "WatchEvent":

                return "Starred " + repo;

            case "ForkEvent":

                return "Forked " + repo;

            case "IssuesEvent":

                if("opened".equals(action)) {

                    return "Opened a new issue in " + repo;
                }

                return capitalize(action) + " an issue in " + repo;

            case "PullRequestEvent": 

                if("opened".equals(action)) {

                    return "Opened a pull request in " + repo;
                }

                return capitalize(action) + " an issue in " + repo;

            case "IssueCommentEvent":

                return "Commented on an issue in " + repo;

            case "CreateEvent":

                return "Created a branch, tag or repository in " + repo;

            default:

                return type + " in " + repo;
        }
    }

    public static String formatShort(Event event) {

        String type = event.getType();
        String action = event.getAction();

        if(type == null) {

            return "Unkown activity";
        }

        switch(type) {

            case "PushEvent":

                return "Pushed";

            case "WatchEvent":

                return "Starred";

            case "ForkEvent":

                return "Forked";

            case "IssuesEvent":

                if("opened".equals(action)) {

                    return "Opened a new issue";
                }

                return capitalize(action) + " an issue";

            case "PullRequestEvent":

                if("opened".equals(action)) {

                    return "Opened a new pull request";
                }

                return capitalize(action) + " a pull request";

            case "IssueCommentEvent":

                return "Commented on an issue";

            case "CreateEvent":

                return "Created a branch, tag or repository";

            default:

                return type;
        }
    }
}
