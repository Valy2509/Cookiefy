package dk.easv.cookiefy.gui.auth;

import dk.easv.cookiefy.bll.Logic;
import dk.easv.cookiefy.gui.util.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

import static dk.easv.cookiefy.gui.util.SceneManager.switchTo;

public class RegController extends BaseController implements Initializable {
    @FXML private TextField nameInput;
    @FXML private TextField emailInput;
    @FXML private PasswordField passInput;
    @FXML private HBox titleBar;
    @FXML private Label messageLabel;

    private Logic logic = new Logic();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeDraggable(titleBar);
    }

    public void changeToLogIn(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        switchTo(source, "gui/auth/login-view.fxml");
    }

    @FXML public void createAccount() throws Exception {
        String username = nameInput.getText();
        String email = emailInput.getText();
        String password = passInput.getText();
        try{
            logic.RegisterNewUser(username, email, password);
            messageLabel.setText("Account Created");
        }catch(Exception e){
            messageLabel.setText(e.getMessage());
        }

    }
}
