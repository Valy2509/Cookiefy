package dk.easv.cookiefy.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

import static dk.easv.cookiefy.gui.SceneManager.switchTo;

public class LoginController extends BaseController implements Initializable {

    @FXML private HBox titleBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeDraggable(titleBar);
    }

    public void changeToRegisterWindow(ActionEvent event){
        Node source = (Node) event.getSource();
        switchTo(source, "gui/auth/reg-view.fxml");
    }
}
