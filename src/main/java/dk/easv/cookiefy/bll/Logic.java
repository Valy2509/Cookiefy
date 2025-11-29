package dk.easv.cookiefy.bll;

import dk.easv.cookiefy.be.Song;
import dk.easv.cookiefy.be.User;
import dk.easv.cookiefy.dal.ITunesDAO;
import dk.easv.cookiefy.dal.UserDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Logic {
    private ITunesDAO iTunesDAO = new ITunesDAO();

    private MediaPlayer mediaPlayer;

    public List<Song> searchSongs(String query) {
        try{
            if (query.isBlank() || query == null) return List.of();
            return iTunesDAO.search(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void playAudio(String url){
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        if(url != null){
            mediaPlayer = new MediaPlayer(new Media(url));
            mediaPlayer.play();
        }

    }

}
