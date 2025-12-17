package dk.easv.cookiefy.gui.popups;

import dk.easv.cookiefy.be.Song;
import dk.easv.cookiefy.be.User;
import dk.easv.cookiefy.bll.Logic;
import dk.easv.cookiefy.gui.util.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConfSongController extends BaseController implements Initializable {

    @FXML private HBox titleBar;
    @FXML private TextField titleInput;
    @FXML private TextField artistInput;
    @FXML private ChoiceBox<String> categoryChoice;
    @FXML private TextField timeInput;
    @FXML private TextField pathInput;

    private User user;
    private Song currSong;
    private double duration = 0;
    private Logic logic =  new Logic();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeDraggable(titleBar);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSong(Song currSong) {
        this.currSong = currSong;
        this.duration = currSong.getDuration();
        titleInput.setText(currSong.getTrackName());
        artistInput.setText(currSong.getArtistName());
        int minutes = (int) currSong.getDuration() / 60;
        int seconds =  (int) currSong.getDuration() % 60;
        timeInput.setText(String.format("%02d:%02d", minutes, seconds));
        pathInput.setText(currSong.getPath());
    }

    public void chooseAudio(){
        Stage stage = (Stage) titleInput.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Audio File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3"));
        File file = fileChooser.showOpenDialog(stage);
        fillInputs(file);
        setDuration(file);
    }
    public void fillInputs(File audioFile){
        if(audioFile == null) return;
        titleInput.setText(audioFile.getName());
        pathInput.setText(audioFile.getAbsolutePath());
    }

    public void setDuration(File f){
        Media media = new Media(f.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(() -> {
            duration = media.getDuration().toSeconds();
            int minutes =  (int) (duration / 60);
            int seconds = (int) (duration % 60);
            String formatedDuration = String.format("%02d:%02d", minutes, seconds);
            timeInput.setText(formatedDuration);
            mediaPlayer.dispose();
        });
    }

    public void saveSong(){
        Stage stage = (Stage) titleInput.getScene().getWindow();
        if (currSong == null) {
            try {
                String title = titleInput.getText();
                String artist = artistInput.getText();
                String path = pathInput.getText();
                logic.saveLocalSong(title, artist, duration, path, user.getUserId());
                stage.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }else{
            try {
                currSong.setTrackName(titleInput.getText());
                currSong.setArtistName(artistInput.getText());
                currSong.setPath(pathInput.getText());
                currSong.setDuration(this.duration);
                logic.updateSong(currSong);
                stage.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
