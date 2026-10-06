import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

public class ActivityCache {
    
    private static final Path CACHE_DIR = Path.of(".cache");
    private static final Duration MAX_AGE = Duration.ofMinutes(5);

    public static Path fileFor(String username) {

        if(!username.matches("[A-Za-z0-9-]+")) {

            return null;
        }

        return CACHE_DIR.resolve(username.toLowerCase() + ".json");
    }

    public static void save(String username, String json) {

        Path file = fileFor(username);

        if(file == null) {

            return;
        }

        try {

            Files.createDirectories(CACHE_DIR);
            Files.writeString(file, json);
        }

        catch(IOException e) {

            // SAVING IS OPTIONAL SO FAILURE IS IGNORED ON PURPOSE
        }
    }

    public static String load(String username) {

        Path file = fileFor(username);

        if(file == null || !Files.exists(file)) {

            return null;
        }

        try {

            Instant savedAt = Files.getLastModifiedTime(file).toInstant();
            Duration age = Duration.between(savedAt, Instant.now());

            if(age.compareTo(MAX_AGE) > 0) {

                return null;
            }

            return Files.readString(file);
        }

        catch(IOException e) {

            return null;
        }
    }
}
