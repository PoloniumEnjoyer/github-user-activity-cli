# GitHub User Activity CLI

A simple command line tool, written in plain Java, that fetches the recent public activity of any GitHub user and shows it in the terminal as readable sentences.

It uses only the Java standard library. No external libraries or frameworks are needed.

Based on the [GitHub User Activity](https://roadmap.sh/projects/github-user-activity) project idea from roadmap.sh.

## Features

- Takes a GitHub username as a command line argument
- Fetches recent events from the GitHub API (`https://api.github.com/users/<username>/events`) using Java's built-in `HttpClient`
- Shows each event as a friendly sentence (for example, `Starred owner/repo`)
- **Filter by event type** with `--type` (short names like `push` work too)
- **Group by repository** with `--group`
- **Caching:** the response is saved for 5 minutes, so repeated runs are fast and use fewer API requests
- Handles errors with clear messages:
  - user not found (404)
  - rate limit reached (403)
  - no internet connection
  - invalid username or wrong arguments
- Reads the JSON by hand, without any JSON library

## Requirements

- Java 11 or newer (needed for `java.net.http.HttpClient`)
- An internet connection (the first time you look up a user)

## How to run

Compile all the files:

```bash
javac *.java
```

Run the program:

```bash
java GithubActivity <username> [--type <EventType>] [--group]
```

### Examples

Show all recent activity:

```bash
java GithubActivity PoloniumEnjoyer
```

```
- Pushed to PoloniumEnjoyer/github-user-activity-cli
- Created a branch, tag or repository in PoloniumEnjoyer/github-user-activity-cli
- Pushed to PoloniumEnjoyer/task-tracker-cli
- Pushed to PoloniumEnjoyer/task-tracker-cli
- Pushed to PoloniumEnjoyer/task-tracker-cli
- Pushed to PoloniumEnjoyer/task-tracker-cli
- Created a branch, tag or repository in PoloniumEnjoyer/task-tracker-cli
```

Show only one type of event (`push` and `PushEvent` both work, capital letters don't matter):

```bash
java GithubActivity PoloniumEnjoyer --type push
```

Group the activity by repository:

```bash
java GithubActivity PoloniumEnjoeyr --group
```

```
PoloniumEnjoyer/github-user-activity-cli (2 events)
 - Pushed                                      
 - Created a branch, tag or repository         
                                               
PoloniumEnjoyer/task-tracker-cli (5 events)                                 
 - Pushed
 - Pushed
 - Pushed
 - Pushed
 - Created a branch, tag or repository
```

Options can be combined and given in any order:

```bash
java GithubActivity PoloniumEnjoyer --group --type push
```

### Options

| Option | Meaning |
|---|---|
| `--type <EventType>` | show only events of that type, for example `push`, `watch`, `issues`, `PullRequestEvent` |
| `--group` | group the events under each repository name |

### Supported event sentences

| Event type | Example output |
|---|---|
| `PushEvent` | Pushed to owner/repo |
| `WatchEvent` | Starred owner/repo |
| `ForkEvent` | Forked owner/repo |
| `IssuesEvent` | Opened a new issue in owner/repo |
| `PullRequestEvent` | Opened a new pull request in owner/repo |
| `IssueCommentEvent` | Commented on an issue in owner/repo |
| `CreateEvent` | Created a branch, tag or repository in owner/repo |
| anything else | the event type name, followed by `in owner/repo` |

## Caching

The first time you look up a user, the JSON response is saved in `.cache/<username>.json`. If you ask for the same user again within 5 minutes, the saved copy is used instead of calling GitHub, and the program prints `(showing saved result, less than 5 minutes old)`. After 5 minutes, fresh data is fetched and the file is replaced.

Failed requests (unknown user, no internet) are never cached.

## Project structure

| File | Purpose |
|---|---|
| `GithubActivity.java` | Main class: connects all the steps together |
| `CliOptions.java` | Reads and checks the command line arguments |
| `GithubClient.java` | Sends the request to the GitHub API and checks the response |
| `GithubException.java` | Custom exception carrying a friendly error message |
| `ActivityCache.java` | Saves and loads responses from the `.cache` folder |
| `EventParser.java` | Splits the JSON into events and extracts values from each one |
| `Event.java` | Holds the type, repository name and action of one event |
| `EventFilter.java` | Decides if an event passes the `--type` filter |
| `EventFormatter.java` | Turns an event into a friendly sentence |
| `GroupedPrinter.java` | Prints events grouped by repository |

## Notes and limitations

- The JSON is read with simple text searching, since external libraries are not allowed in this project. This works for GitHub's compact JSON format but is not a full JSON parser.
- GitHub removed commit counts and commit summaries from push events in the Events API, so a push event cannot show how many commits were pushed.
- The GitHub API returns at most 30 recent events by default, and requests without a login are rate limited. Caching helps with this.
- Only the event types in the table above have their own sentence. Other types still appear, using their type name.