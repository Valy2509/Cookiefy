package dk.easv.cookiefy.dal;

import dk.easv.cookiefy.be.Playlist;
import dk.easv.cookiefy.be.Song;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    private ConnectionManager connectionManager =  new ConnectionManager();

    public void createPlaylist(String name, int userId) {
        try(Connection con = connectionManager.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO Playlists (User_ID, Name) values (?, ?)");
            ps.setInt(1, userId);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Playlist> getAllPlaylists(int userId) {
        List<Playlist> playlists = new ArrayList<>();
        try(Connection con = connectionManager.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Playlists WHERE User_ID = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                playlists.add(new Playlist(rs.getInt("Playlist_ID"), rs.getString("Name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playlists;
    }

    public void updatePlaylist(Playlist chosenPlaylist) {
        try(Connection con = connectionManager.getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE Playlists SET Name = ? WHERE Playlist_ID = ?");
            ps.setString(1, chosenPlaylist.getName());
            ps.setInt(2, chosenPlaylist.getId());
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Song> getSongsFromPl(int id) {
        List<Song> songs = new ArrayList<>();
        try(Connection con = connectionManager.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT s.* FROM Songs s JOIN Playlist_Songs ps ON s.Song_ID = ps.Song_ID WHERE ps.Playlist_ID = ? ORDER BY ps.Position");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                songs.add(new Song(rs.getInt("Song_ID"), rs.getString("Title"), rs.getString("Artist"), rs.getString("File_Path"), rs.getDouble("Duration_Seconds"), rs.getInt("User_ID")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return songs;
    }

    public void addSongToPl(int plId, int songId1, int pos) {
        try(Connection con = connectionManager.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO Playlist_Songs (Playlist_ID, Song_ID, Position) VALUES (?, ?, ?)");
            ps.setInt(1, plId);
            ps.setInt(2, songId1);
            ps.setInt(3, pos);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSongPl(Playlist selectedPlaylist, Song selectedSong) {
        try(Connection con = connectionManager.getConnection()){
            PreparedStatement ps = con.prepareStatement("DELETE FROM Playlist_Songs WHERE Playlist_ID = ? AND Song_ID = ?");
            ps.setInt(1, selectedPlaylist.getId());
            ps.setInt(2, selectedSong.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePlaylist(Playlist selectedPlayList) {
        try(Connection con = connectionManager.getConnection()){
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM Playlist_Songs WHERE Playlist_ID = ?")) {
                ps.setInt(1, selectedPlayList.getId());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement("DELETE FROM Playlists WHERE Playlist_ID = ?")) {
                ps.setInt(1, selectedPlayList.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePlOrder(Playlist selectedPlaylist, ObservableList<Song> items) {
        try(Connection con = connectionManager.getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE Playlist_Songs SET Position = ? WHERE Playlist_ID = ? AND Song_ID = ?");
            for (int i = 0; i < items.size(); i++) {
                Song song = items.get(i);
                ps.setInt(1, i);
                ps.setInt(2, selectedPlaylist.getId());
                ps.setInt(3, song.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
