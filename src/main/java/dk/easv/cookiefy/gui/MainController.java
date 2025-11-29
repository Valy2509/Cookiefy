package dk.easv.cookiefy.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainController extends BaseController {

    @FXML private VBox content;

    @FXML public void loadPage(String fxmlPath){ //Method used for switching between views (pages)
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            content.getChildren().clear();
            content.getChildren().add(root);
            VBox.setVgrow(root, Priority.ALWAYS);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
