import java.util.ArrayList;
import java.util.List;

public class EventParser {
    
    public List<String> splitEvents(String json) {

        List<String> events = new ArrayList<>();

        int depth = 0;
        int start = 0;
        boolean inString = false;

        for(int i = 0; i < json.length(); i++) {

            char c = json.charAt(i);

            if(inString) {

                if(c == '\\') {

                    i++;
                }

                else if(c == '"') {

                    inString = false;
                }
            }

            else {

                if(c == '"') {

                    inString = true;
                }

                else if(c == '{') {

                    if(depth == 0) {

                        start = i;
                    }
                    
                    depth++;
                }

                else if(c == '}') {

                    depth--;

                    if(depth == 0) {

                        events.add(json.substring(start, i+1));
                    }
                }
            }
        }

        return events;
    }

    public String findValue(String text, String key, int from) {

        String pattern  = "\"" + key + "\":\"";
        int start = text.indexOf(pattern, from);

        if(start == -1) {

            return null;
        }

        // MOVE TO WHERE THE VALUE BEGINS
        start = start + pattern.length();

        int end = text.indexOf("\"", start);

        return text.substring(start, end);
    }

    public Event parseEvent(String eventText) {

        String type = findValue(eventText, "type", 0);

        int repoPos = eventText.indexOf("\"repo\":");
        String repoName = findValue(eventText, "name", repoPos);

        int payloadPos = eventText.indexOf("\"payload\":");
        String action = findValue(eventText, "action", payloadPos);

        return new Event(type, repoName, action);
    }
}
