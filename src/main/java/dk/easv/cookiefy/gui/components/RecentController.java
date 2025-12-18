package dk.easv.cookiefy.gui.components;

import dk.easv.cookiefy.be.Song;
import dk.easv.cookiefy.gui.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class RecentController implements Initializable{

    @FXML private ImageView songThumb;
    @FXML private Label titleLabel;

    private Song song;
    private MainController mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        songThumb.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2){
                if (song == null) return;
                mainController.playSong(song);
            }
        });
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setSong(Song song) {
        this.song = song;
        titleLabel.setText(song.getTrackName());
    }
}
