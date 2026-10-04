import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GroupedPrinter {
 
    
    public static void print(List<Event> events) {

        Map<String, List<Event>> groups = new LinkedHashMap<>();

        for(Event event : events) {

            String repo = event.getRepoName();

            if(repo == null) {

                repo = "Unknown repository";
            }

            if(!groups.containsKey(repo)) {

                groups.put(repo, new ArrayList<>());
            }

            groups.get(repo).add(event);
        }

        for(Map.Entry<String, List<Event>> entry : groups.entrySet()) {

            String repo = entry.getKey();
            List<Event> repoEvents = entry.getValue();

            String word = repoEvents.size() == 1 ? "event" : "events";

            System.out.println(repo + " (" + repoEvents.size() + " " + word + ")");

            for(Event event : repoEvents) {

                System.out.println(" - " + EventFormatter.formatShort(event));
            }

            System.out.println();
        }
    }
}
