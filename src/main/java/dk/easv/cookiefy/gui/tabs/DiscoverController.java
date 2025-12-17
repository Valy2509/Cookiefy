package dk.easv.cookiefy.gui.tabs;

import dk.easv.cookiefy.be.Song;
import dk.easv.cookiefy.bll.Logic;
import dk.easv.cookiefy.gui.MainController;
import dk.easv.cookiefy.gui.components.SongController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class DiscoverController{
    @FXML private TextField searchInput;
    @FXML private VBox songsContainer;

    private Logic logic = new Logic();
    private MainController mainController;

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public void searchSong(){
        List<Song> songs = logic.searchSongs(searchInput.getText());
        songsContainer.getChildren().clear();
        for (Song song : songs){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/cookiefy/gui/components/song-view.fxml"));
                HBox songBox = loader.load();
                SongController controller = loader.getController();
                controller.setSong(song);
                controller.setMainController(mainController);
                songsContainer.getChildren().add(songBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
