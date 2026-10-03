import java.util.List;

public class GithubActivity {

    public static void main(String[] args) {

        if(args.length == 0) {

            System.out.println("<Usage>: java GithubActivity <username>");

            return;
        }

        String username = args[0];

        GithubClient githubClient = new GithubClient();

        try {

            String json = githubClient.fetchEvents(username);

            EventParser parser = new EventParser();
            List<String> events = parser.splitEvents(json);

            if(events.isEmpty()) {

                System.out.println("No recent activity found for " + username);

                return;
            }

            System.out.println("Number of events: " + events.size());

            for(String eventText : events) {

                Event event = parser.parseEvent(eventText);

                System.out.println(event.getType() + "  |  " + event.getRepoName() + "  |  " + event.getAction());
            }
        }

        catch(GithubException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }
}