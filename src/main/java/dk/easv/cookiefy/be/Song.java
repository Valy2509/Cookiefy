package dk.easv.cookiefy.be;

public class Song {
    private String trackName;
    private String artistName;
    private String artworkUrl100;
    private String previewUrl;

    public Song(String trackName, String artistName, String artworkUrl100, String previewUrl) {
        this.trackName = trackName;
        this.artistName = artistName;
        this.artworkUrl100 = artworkUrl100;
        this.previewUrl = previewUrl;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }
}
