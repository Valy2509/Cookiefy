package dk.easv.cookiefy.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LibraryController implements Initializable {

    @FXML private VBox localSongsContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) { // Here ask database to load the saved music each time the tab opens

    }

    public void addLocalMusic(){
        Stage stage = (Stage) localSongsContainer.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Audio File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio File", "*.mp3", "*.wav"));
        fileChooser.showOpenDialog(stage);
        updateSongList("");
    }

    public void updateSongList(String fxmlPath){
        // Here update database each time user adds new music
    }
}
