# GitHub User Activity CLI

A simple command line tool, written in plain Java, that fetches the recent public activity of any GitHub user and shows it in the terminal.

It uses only the Java standard library. No external libraries or frameworks are needed.

Based on the [GitHub User Activity](https://roadmap.sh/projects/github-user-activity) project idea from roadmap.sh.

## Features

- Takes a GitHub username as a command line argument
- Fetches the user's recent events from the GitHub API (`https://api.github.com/users/<username>/events`) using Java's built-in `HttpClient`
- Handles errors with clear messages:
  - user not found (404)
  - rate limit reached (403)
  - no internet connection
  - invalid username
- Splits the JSON response into separate events and reads the event type, repository name and action from each one, without any JSON library

## Planned

- [ ] Print each event as a friendly sentence (for example, `Starred owner/repo`)
- [ ] Filter activity by event type
- [ ] Show the activity in a more structured format
- [ ] Cache fetched data to avoid repeated API calls

## Requirements

- Java 11 or newer (needed for `java.net.http.HttpClient`)
- An internet connection

## How to run

Compile all the files:

```bash
javac *.java
```

Run the program with a GitHub username:

```bash
java Main <username>
```

Example:

```bash
java Main kamranahmedse
```

Current output (one line per event: type, repository, action):

```
WatchEvent | kamranahmedse/developer-roadmap | started
IssuesEvent | someuser/some-repo | opened
PushEvent | someuser/some-repo | null
```

`null` means the event type has no action (push events, for example).

## Notes and limitations

- The JSON is read with simple text searching, since external libraries are not allowed in this project. This works for GitHub's compact JSON format but is not a full JSON parser.
- GitHub removed commit counts and commit summaries from push events in the Events API, so a push event cannot show how many commits were pushed.
- The GitHub API returns at most 30 recent events by default, and unauthenticated requests are rate limited.