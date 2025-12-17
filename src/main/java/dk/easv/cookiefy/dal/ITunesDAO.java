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

        if (root == null || !root.isArray()) return songs;

        for (JsonNode item : root) {
            String type = item.path("wrapperType").asText("");
            String kind = item.path("kind").asText("");
            String trackName =  item.path("trackName").asText(null);
            String previewUrl = item.path("previewUrl").asText(null);

            if ("track".equals(type) && "song".equals(kind) && trackName != null && !trackName.isBlank() && previewUrl != null && !previewUrl.isBlank()) {
                songs.add(new Song(trackName, item.path("artistName").asText("Unknown Artist"), item.path("artworkUrl100").asText(""), previewUrl));
            }

        }
        return songs;
    }

}
