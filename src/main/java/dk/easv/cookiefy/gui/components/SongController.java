package dk.easv.cookiefy.gui.components;

import dk.easv.cookiefy.be.Song;
import dk.easv.cookiefy.gui.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SongController {
    @FXML private Label titleLabel;

    private Song song;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setSong(Song song) {
        this.song = song;
        titleLabel.setText(song.getTrackName());
    }

    @FXML public void playSong() {
        if (song != null && mainController != null) {
            mainController.playSong(song);
        }
    }
}
