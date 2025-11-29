package dk.easv.cookiefy.gui;

import dk.easv.cookiefy.be.Song;
import dk.easv.cookiefy.bll.Logic;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SongController {
    @FXML private Label titleLabel;

    private Song song;
    private Logic logic = new Logic();

    public void setSong(Song song) {
        this.song = song;
        titleLabel.setText(song.getTrackName());
    }

    @FXML public void playSong() {
        if (song != null || song.getPreviewUrl().isBlank()) {
            logic.playAudio(song.getPreviewUrl());
        }
    }
}
