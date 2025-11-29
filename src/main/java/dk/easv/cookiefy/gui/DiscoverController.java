package dk.easv.cookiefy.gui;

import dk.easv.cookiefy.be.Song;
import dk.easv.cookiefy.bll.Logic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DiscoverController{
    @FXML private TextField searchInput;
    @FXML private VBox songsContainer;

    private Logic logic = new Logic();

    public void searchSong(){
        List<Song> songs = logic.searchSongs(searchInput.getText());
        songsContainer.getChildren().clear();
        for (Song song : songs){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("clonables/song-view.fxml"));
                HBox songBox = loader.load();
                SongController controller = loader.getController();
                controller.setSong(song);
                songsContainer.getChildren().add(songBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
