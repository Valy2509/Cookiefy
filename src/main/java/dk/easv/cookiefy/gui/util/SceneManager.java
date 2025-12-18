package dk.easv.cookiefy.gui.util;

import dk.easv.cookiefy.StartApplication;
import dk.easv.cookiefy.be.Playlist;
import dk.easv.cookiefy.be.Song;
import dk.easv.cookiefy.be.User;
import dk.easv.cookiefy.gui.MainController;
import dk.easv.cookiefy.gui.popups.ConfSongController;
import dk.easv.cookiefy.gui.popups.PlaylistController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SceneManager {

    public static void switchTo(Node source, String fxmlPath){ // Method used for switching scenes while keeping the same stage.
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(fxmlPath));
            Stage stage = (Stage) source.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void openNewWindow(String title, String fxmlPath){ // Method used for creating a new window.
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(fxmlPath));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void openNewWindow(String title, String fxmlPath, User user){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(fxmlPath));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            MainController mainController = (MainController) fxmlLoader.getController();
            mainController.setUser(user);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void openWindowWait(String title, String fxmlPath, User user){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(fxmlPath));
            Stage stage = new Stage();

            Scene scene = new Scene(fxmlLoader.load());
            Object controller = fxmlLoader.getController();
            if (controller instanceof ConfSongController){
                if (user != null) {
                    ((ConfSongController) controller).setUser(user);
                }
            }

            if (controller instanceof PlaylistController){
                ((PlaylistController) controller).setUser(user);
            }

            stage.setTitle(title);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void openWindowWait(String title, String fxmlPath, User user, Song song){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(fxmlPath));
            Stage stage = new Stage();

            Scene scene = new Scene(fxmlLoader.load());
            ConfSongController confSongController =  (ConfSongController) fxmlLoader.getController();
            if (user != null && song != null) {
                confSongController.setUser(user);
                confSongController.setSong(song);
            }
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void openWindowWait(String title, String fxmlPath, User user, Playlist playlist){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(fxmlPath));
            Stage stage = new Stage();

            Scene scene = new Scene(fxmlLoader.load());
            PlaylistController playlistController =  (PlaylistController) fxmlLoader.getController();
            if (user != null && playlist != null) {
                playlistController.setUser(user);
                playlistController.setPlaylist(playlist);
            }
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
