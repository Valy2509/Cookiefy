package dk.easv.cookiefy.dal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.easv.cookiefy.be.Song;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ITunesDAO {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public List<Song> search(String query) throws Exception {
        String url = "https://itunes.apple.com/search?term=" + query.replace(" ", "+") + "&limit=25";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return parse(response.body());
    }

    public List<Song> parse(String json) throws Exception {
        List<Song> songs = new ArrayList<>();
        JsonNode root = mapper.readTree(json).get("results");

        for (JsonNode item : root) {
            if(item.path("trackName").asText(null) == null || item.path("trackName").asText(null).isBlank()) continue;
            songs.add(new Song(item.path("trackName").asText(""), item.path("artistName").asText(""), item.path("artworkUrl100").asText(""), item.path("previewUrl").asText("")));
        }
        return songs;
    }

}
