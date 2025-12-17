package dk.easv.cookiefy.bll;

import dk.easv.cookiefy.be.Playlist;
import dk.easv.cookiefy.be.Song;
import dk.easv.cookiefy.be.User;
import dk.easv.cookiefy.dal.ITunesDAO;
import dk.easv.cookiefy.dal.PlaylistDAO;
import dk.easv.cookiefy.dal.SongDAO;
import dk.easv.cookiefy.dal.UserDAO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class Logic {
    private ITunesDAO iTunesDAO = new ITunesDAO();
    private UserDAO userDAO = new UserDAO();
    private SongDAO songDAO = new SongDAO();
    private PlaylistDAO playlistDAO = new PlaylistDAO();

    private MediaPlayer mediaPlayer;
    private ObjectProperty<Song> currSong = new SimpleObjectProperty<Song>();
    private List<Song> queue;
    private int currIndex = -1;
    private double volume = .1;

    public void setVolume(double volume){
        this.volume = volume;

        if(mediaPlayer != null){
            mediaPlayer.setVolume(volume);
        }
    }

    public ObjectProperty<Song> getSong(){
        return currSong;
    }

    public List<Song> searchSongs(String query) {
        try{
            if (query.isBlank() || query == null) return List.of();
            return iTunesDAO.search(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void playQueue(List<Song> songs, int startIndex) {
        this.currIndex = startIndex;
        this.queue = songs;
        playInternal();
    }

    public void nextSong() {
        if (queue != null && currIndex < queue.size() - 1) {
            currIndex++;
            playInternal();
        }
    }

    public void prevSong() {
        if (queue != null && currIndex > 0) {
            currIndex--;
            playInternal();
        }else{
            if(mediaPlayer != null){
                mediaPlayer.seek(Duration.ZERO);
            }
        }
    }

    public void playInternal(){
        if (queue == null || currIndex < 0 || currIndex >= queue.size()) return;
        Song song = queue.get(currIndex);
        currSong.set(song);
        playAudio(song.getPath());
        if(mediaPlayer != null){
            mediaPlayer.setOnEndOfMedia(()->{
               currIndex++;
               playInternal();
            });
        }
    }

    public void playSingleSong(Song song){
        this.queue = null;
        this.currIndex = -1;
        this.currSong.set(song);

        String url = "";

        if (song.getPreviewUrl() != null && !song.getPreviewUrl().isEmpty()){
            url = song.getPreviewUrl();
        }else if(song.getPath() != null && !song.getPath().isEmpty()){
            url = song.getPath();
        }

        if (!url.isEmpty()){
            playAudio(url);
        }

        if (mediaPlayer != null){
            mediaPlayer.setOnEndOfMedia(null);
        }
    }

    public void playAudio(String url) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
            mediaPlayer = new MediaPlayer(new Media(url));
        }else{
            mediaPlayer = new MediaPlayer(new Media(new File(url).toURI().toString()));

        }

        mediaPlayer.setVolume(volume);
        mediaPlayer.play();
    }

    public void pauseSong(){
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void resumeSong(){
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public boolean isPlayingSong(){
        if (mediaPlayer != null) {
            return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
        }
        return false;
    }



    public void RegisterNewUser(String username, String email, String password) throws Exception {
        if (username == null || !username.matches("^[A-Za-z0-9]+$")){
            throw new Exception("Invalid username. Must be letters/numbers only, no spacing");
        }
        if(email == null || !email.matches("^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]+$")){
            throw new Exception("Invalid email address. (f.ex user@example.com)");
        }
        if (password == null || password.length() < 8){
            throw new Exception("Password should be at least 8 characters");
        }
        String hashPass = BCrypt.hashpw(password, BCrypt.gensalt());
        userDAO.Register(username, email, hashPass);
    }

    public User Login(String email, String password) throws Exception {
        if (email == null || email.isBlank() || password == null || password.isBlank()){
            throw new Exception("Please provide credentials");
        }
        User user = userDAO.Login(email);
        if (user == null){
            throw new Exception("User doesn't exists!");
        }

        if (!BCrypt.checkpw(password, user.getPassword())){
            throw new Exception("Wrong password!");
        }

        return user;
    }

    public Song saveLocalSong(String title, String artist, double duration, String path, int userId) throws Exception {
        if(title == null || artist == null || duration == 0){
            throw new Exception("Title/artist is empty");
        }
        return songDAO.saveSong(title,  artist, duration, path, userId);
    }

    public void updateSong(Song song) throws SQLException {
        if (song == null) return;
        songDAO.updateSong(song);
    }

    public List<Song> getAllSongs(int userId) throws SQLException {
        return songDAO.getAllSongs(userId);
    }

    public void newPlaylist(String name, int userId) {
        playlistDAO.createPlaylist(name, userId);
    }

    public List<Playlist> getAllPlaylists(int userId) {
        return playlistDAO.getAllPlaylists(userId);
    }

    public void updatePlaylist(Playlist chosenPlaylist) {
        if (chosenPlaylist == null) return;
        playlistDAO.updatePlaylist(chosenPlaylist);
    }

    public void addSongToPlaylist(Playlist playlist, Song song) {
        try{
            List<Song> songs = playlistDAO.getSongsFromPl(playlist.getId());
            int position = songs.size();
            playlistDAO.addSongToPl(playlist.getId(), song.getId(), position);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Song> getSongsFromPl(int playlistId){
        return  playlistDAO.getSongsFromPl(playlistId);
    }

    public void deleteSong(Song target) {
        songDAO.deleteSong(target);
    }

    public void deleteSongPl(Playlist selectedPlaylist, Song selectedSong) {
        if (selectedPlaylist == null || selectedSong == null) return;
        try{
            playlistDAO.deleteSongPl(selectedPlaylist, selectedSong);
        }catch(RuntimeException e){
            throw new RuntimeException(e);
        }

        if (queue != null){
            for (int i = 0; i < queue.size(); i++) {
                if (queue.get(i).getId() == selectedSong.getId()){
                    if (i < currIndex){
                        currIndex--;
                    } else if (i == currIndex) {
                        currIndex--;
                    }
                    queue.remove(i);
                    break;
                }
            }
        }
    }

    public void deletePlaylist(Playlist selectedPlayList) {
        if (selectedPlayList == null) return;
        try{
            playlistDAO.deletePlaylist(selectedPlayList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
