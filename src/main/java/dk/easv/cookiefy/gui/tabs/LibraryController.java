package dk.easv.cookiefy.gui.tabs;

import dk.easv.cookiefy.be.Playlist;
import dk.easv.cookiefy.be.Song;
import dk.easv.cookiefy.be.User;
import dk.easv.cookiefy.bll.Logic;
import dk.easv.cookiefy.gui.MainController;
import dk.easv.cookiefy.gui.inferfaces.IUserAware;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static dk.easv.cookiefy.gui.util.SceneManager.openWindowWait;

public class LibraryController implements Initializable, IUserAware {

    @FXML private ListView<Song> localSongsContainer;
    @FXML private ListView<Playlist> playListsContainer;
    @FXML private ListView<Song> plContent;

    private MainController mainController;

    private User currUser;
    private Logic logic = new Logic();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        localSongsContainer.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                mainController.playSong(localSongsContainer.getSelectionModel().getSelectedItem());
            }
        });

        plContent.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Song selected = plContent.getSelectionModel().getSelectedItem();
                List<Song> songs = plContent.getItems();
                int index = songs.indexOf(selected);
                mainController.playQueue(songs, index);
            }
        });

        playListsContainer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateContent(newValue);
            }
        });
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setUser(User user) {
        this.currUser = user;
        updateSongList();
        updatePlList();
    }

    public void addLocalSong(){
        openWindowWait("New/Edit Song", "gui/popups/consong-view.fxml", currUser);
        updateSongList();
    }

    public void editLocalSong(){
        Song song = localSongsContainer.getSelectionModel().getSelectedItem();
        if (song == null) return;
        openWindowWait("New/Edit Song", "gui/popups/consong-view.fxml", currUser, song);
        updateSongList();
    }

    public void updateSongList() {
        try {
            List<Song> songs = logic.getAllSongs(currUser.getUserId());
            localSongsContainer.getItems().clear();
            for (Song song : songs) {
                localSongsContainer.getItems().add(song);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void newPlaylist(){
        openWindowWait("New/Edit Playlist",  "gui/popups/playlist-view.fxml", currUser);
        updatePlList();

    }
    public void editPlaylist(){
        Playlist playlist = playListsContainer.getSelectionModel().getSelectedItem();
        if (playlist == null) return;
        openWindowWait("New/Edit Playlist",  "gui/popups/playlist-view.fxml", currUser, playlist);
        updatePlList();
    }

    public void updatePlList(){
        try{
            List<Playlist> playlists = logic.getAllPlaylists(currUser.getUserId());
            playListsContainer.getItems().clear();
            for (Playlist playlist : playlists) {
                playListsContainer.getItems().add(playlist);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addSongPlaylist(){
        Playlist selectedPl =  playListsContainer.getSelectionModel().getSelectedItem();
        Song selectedSong = localSongsContainer.getSelectionModel().getSelectedItem();
        if (selectedPl != null && selectedSong != null){
            logic.addSongToPlaylist(selectedPl, selectedSong);
            updateContent(selectedPl);
        };
    }

    public void updateContent(Playlist selectedPl){
        List<Song> songs = logic.getSongsFromPl(selectedPl.getId());
        plContent.getItems().clear();
        plContent.getItems().addAll(songs);
    }

    public void deleteLocalSong(){
        Song selectedSong = localSongsContainer.getSelectionModel().getSelectedItem();
        if (selectedSong == null) return;
        logic.deleteSong(selectedSong);
        updateSongList();
    }

    public void deletePlaylist(){
        Playlist selectedPlayList = playListsContainer.getSelectionModel().getSelectedItem();
        if(selectedPlayList == null) return;
        Alert alert = new  Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Confirm Deletion of " + selectedPlayList.getName());
        alert.setContentText("Are you sure you want to delete this playlist?");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/dk/easv/cookiefy/gui/css/styles.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog");
        if(alert.showAndWait().get() == ButtonType.OK){
            logic.deletePlaylist(selectedPlayList);
            updatePlList();
            updateContent(selectedPlayList);
        }
    }

    public void deleteSongPl(){
        Song selectedSong = plContent.getSelectionModel().getSelectedItem();
        Playlist selectedPlaylist = playListsContainer.getSelectionModel().getSelectedItem();
        if(selectedSong == null || selectedPlaylist == null) return;
        logic.deleteSongPl(selectedPlaylist, selectedSong);
        updateContent(selectedPlaylist);
    }
}
