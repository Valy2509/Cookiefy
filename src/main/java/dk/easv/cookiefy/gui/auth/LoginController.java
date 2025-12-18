package dk.easv.cookiefy.gui.auth;

import dk.easv.cookiefy.be.User;
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
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static dk.easv.cookiefy.gui.util.SceneManager.openNewWindow;
import static dk.easv.cookiefy.gui.util.SceneManager.switchTo;

public class LoginController extends BaseController implements Initializable {

    @FXML private HBox titleBar;
    @FXML private Label messageLabel;
    @FXML private TextField emailInput;
    @FXML private PasswordField passInput;

    private User user;
    private Logic logic = new Logic();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeDraggable(titleBar);
    }

    public void loginAccount() throws Exception {
        Stage stage =  (Stage) titleBar.getScene().getWindow();
        String email = emailInput.getText();
        String pass = passInput.getText();
        try{
            user = logic.Login(email, pass);
        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
        }
        if (user == null) return;
        openNewWindow("Cookiefy", "gui/main-view.fxml", user);
        stage.close();
    }

    @FXML public void changeToRegisterWindow(ActionEvent event){
        Node source = (Node) event.getSource();
        switchTo(source, "gui/auth/reg-view.fxml");
    }
}
