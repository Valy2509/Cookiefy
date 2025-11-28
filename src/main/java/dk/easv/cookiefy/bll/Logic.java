package dk.easv.cookiefy.bll;

import dk.easv.cookiefy.be.User;
import dk.easv.cookiefy.dal.UserDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.sql.SQLException;

public class Logic {
    private UserDAO userDAO = new UserDAO();
    private MediaPlayer mediaPlayer;
    private StringProperty duration = new SimpleStringProperty("");
    public void playAudio(File f){
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        if(f != null){
            mediaPlayer = new MediaPlayer(new Media(f.toURI().toString()));
            mediaPlayer.play();
            mediaPlayer.setOnReady(() -> {
                duration.setValue(Math.round((mediaPlayer.getMedia().getDuration().toMinutes()) * 100.0) / 100.0 + "");
            });
        }

    }
    public StringProperty getDuration(){
        return duration;
    }


    public User logIn(String email, String pass) throws Exception {
        User user = userDAO.getUserByEmail(email);
        if (user == null) throw new Exception("User not found");
        if (BCrypt.checkpw(pass, user.getPassword())) {
            return user;
        }else{
            throw new Exception("Incorrect password");
        }
    }

    public void registerUser(String email, String pass) throws Exception {
        String hash =  BCrypt.hashpw(pass, BCrypt.gensalt());
        userDAO.registerUser(email, hash);
    }



}
