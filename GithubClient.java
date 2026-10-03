import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GithubClient {

    private final HttpClient client = HttpClient.newHttpClient();

    public String fetchEvents(String username) throws GithubException {

        try {

            String url = "https://api.github.com/users/" + username + "/events";

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Accept", "application/vnd.github+json").build();

            // client.send() CAN THROW THREE TYPES OF EXCEPTION ALL OF WHICH ARE BEING HANDELED IN THE 3 DIFF CATCH BLOCKS
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int status = response.statusCode();

            if(status == 200) {

                return response.body();
            }

            else if(status == 404) {

                throw new GithubException("User '" + username + "' was not found");
            }

            else if(status == 403) {

                throw new GithubException("Github rate limit reached. Try again later");
            }

            else {

                throw new GithubException("Github returned an error. Status code: " + status);
            }
        }

        catch(IOException e) {

            throw new GithubException("Could not connect to Github. Check your internet connection.");
        }

        catch(InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new GithubException("The request was interrupted");
        }

        catch(IllegalArgumentException e) {

            throw new GithubException("Invalid username");
        }
    }
}