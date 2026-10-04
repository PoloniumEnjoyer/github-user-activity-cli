public class CliOptions {
    
    private final String username;
    private final String filterType;
    private final boolean group;

    private CliOptions(String username, String filterType, boolean group) {

        this.username = username;
        this.filterType = filterType;
        this.group = group;
    }

    public static CliOptions parse(String[] args) {

        if(args.length == 0 || args[0].startsWith("--")) {

            return null;
        }

        String username = args[0];
        String filterType = null;
        boolean group = false;

        int i = 1;

        while(i < args.length) {

            String arg = args[i];

            if(arg.equals("--type")) {

                if(i + 1 >= args.length) {

                    return null;
                }

                filterType = args[i + 1];

                if(!filterType.toLowerCase().endsWith("event")) {

                    filterType += "Event";
                }

                i = i + 2;
            }

            else if(arg.equals("--group")) {

                group = true;

                i = i + 1;
            }

            else {

                return null;
            }
        }

        return new CliOptions(username, filterType, group);
    }

    public String getUsername() {

        return username;
    }

    public String getFilterType() {

        return filterType;
    }

    public boolean isGroup() {

        return group;
    }
}
