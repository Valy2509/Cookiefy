package dk.easv.cookiefy.gui;

import dk.easv.cookiefy.StartApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    public static void switchTo(Node source, String fxmlPath){
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

    public static void openNewWindow(String title, String fxmlPath){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(fxmlPath));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
