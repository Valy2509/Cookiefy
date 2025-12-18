package dk.easv.cookiefy.be;

/**
 * Represents a Playlist entity
 * Holds data to be used between layers
 */

public class Playlist {
    private int id;
    private String name;

    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }
}
