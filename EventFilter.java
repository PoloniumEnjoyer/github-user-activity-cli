public class EventFilter {
    
    public static boolean matches(Event event, String filterType) {

        if(filterType == null) {

            return true;
        }

        return filterType.equalsIgnoreCase(event.getType());
    }
}
