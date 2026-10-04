import java.util.ArrayList;
import java.util.List;

public class GithubActivity {

    public static void main(String[] args) {

        CliOptions options = CliOptions.parse(args);

        if(options == null) {

            System.out.println("<Usage>: java GithubActivity <username> [--type <EventType>] [--group]");
            return;
        }

        String username = options.getUsername();

        GithubClient githubClient = new GithubClient();

        try {

            String json = githubClient.fetchEvents(username);

            EventParser parser = new EventParser();
            List<String> events = parser.splitEvents(json);

            if(events.isEmpty()) {

                System.out.println("No recent activity found for " + username);

                return;
            }

            List<Event> matching = new ArrayList<>();

            for(String eventText : events) {

                Event event = parser.parseEvent(eventText);

                if(EventFilter.matches(event, options.getFilterType())) {

                    matching.add(event);
                }

                if(matching.isEmpty()) {

                    System.out.println("No " + options.getFilterType() + " found for " + username);
                    return;
                }
            }

            if(options.isGroup()) {

                GroupedPrinter.print(matching);
            }

            else {

                for(Event event : matching) {

                    System.out.println("- " + EventFormatter.format(event));
                }
            }
        }

        catch(GithubException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }
}