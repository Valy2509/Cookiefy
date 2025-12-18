package dk.easv.cookiefy.gui;

import dk.easv.cookiefy.be.Song;
import dk.easv.cookiefy.be.User;
import dk.easv.cookiefy.bll.Logic;
import dk.easv.cookiefy.gui.components.RecentController;
import dk.easv.cookiefy.gui.components.SongController;
import dk.easv.cookiefy.gui.inferfaces.IUserAware;
import dk.easv.cookiefy.gui.tabs.DiscoverController;
import dk.easv.cookiefy.gui.tabs.HomeController;
import dk.easv.cookiefy.gui.tabs.LibraryController;
import dk.easv.cookiefy.gui.util.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController extends BaseController implements Initializable {

    @FXML private HBox titleBar;
    @FXML private VBox content;
    @FXML private ImageView songImage;
    @FXML private Label songTitle;
    @FXML private Button playBtn;
    @FXML private Slider volumeSlider;

    private User user;
    private Logic logic = new Logic();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeDraggable(titleBar);

        logic.getSong().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateUi(newValue);
            }
        });

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                double volume = newValue.doubleValue() / 100;
                logic.setVolume(volume);
            }
        });

    }

    @FXML public void loadPage(String fxmlPath){ //Method used for switching between views (pages)
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Object obj = loader.getController();
            if (obj instanceof LibraryController){
                ((LibraryController) obj).setMainController(this);
            }else if(obj instanceof HomeController){
                ((HomeController) obj).setMainController(this);
            }else if (obj instanceof DiscoverController){
                ((DiscoverController) obj).setMainController(this);
            }

            if (obj instanceof IUserAware){
                ((IUserAware) obj).setUser(user);
            }

            content.getChildren().clear();
            content.getChildren().add(root);
            VBox.setVgrow(root, Priority.ALWAYS);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setUser(User user){
        this.user = user;
    }

    public Logic getLogic() {
        return logic;
    }

    public void togglePlay(){
        if(logic.isPlayingSong()){
            logic.pauseSong();
            playBtn.setText("▶");
        }else{
            logic.resumeSong();
            playBtn.setText("⏸");
        }
    }

    public void playSong(Song song){
        if (song == null) return;
        logic.playSingleSong(song, this.user);
        logic.addRecentSong(song, user);
        playBtn.setText("⏸");
    }

    public void playQueue(List<Song> songs, int startIndex){
        logic.playQueue(songs, startIndex, this.user);
    }

    public void updateUi(Song song){
        songTitle.setText(song.getTrackName());
        if (song.getArtworkUrl100() != null){
            songImage.setImage(new Image(song.getArtworkUrl100()));
        }else{
            songImage.setImage(new Image(getClass().getResource("/dk/easv/cookiefy/gui/imgs/notFound.jpg").toExternalForm()));
        }
        playBtn.setText("⏸");
    }

    public void playNext(){
        logic.nextSong(this.user);
    }

    public void playPrevious(){
        logic.prevSong(this.user);
    }

    public void loadHomePage(){
        loadPage("pages/home-view.fxml");
    }

    public void loadDiscover(){
        loadPage("pages/discover-view.fxml");
    }

    public void loadLibrary(){
        loadPage("pages/library-view.fxml");
    }
}
