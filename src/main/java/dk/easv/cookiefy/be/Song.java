package dk.easv.cookiefy.be;

/**
 * Represents a Song entity
 * Holds data to be used between layers
 */

public class Song {
    private int id;
    private String path;
    private double duration;
    private int userId;

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

    public Song(int id, String trackName, String artistName, String path, double duration, int userId) {
        this.id = id;
        this.trackName = trackName;
        this.artistName = artistName;
        this.path = path;
        this.duration = duration;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return trackName + "\nArtist: " + artistName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public String getPath(){
        return path;
    }

    public double getDuration() {
        return duration;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }
}
