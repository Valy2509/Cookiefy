package dk.easv.cookiefy.gui;

import dk.easv.cookiefy.be.User;
import dk.easv.cookiefy.bll.Logic;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainController {

    @FXML private Label playingLabel;
    @FXML private TextField emailInput;
    @FXML private TextField passInput;

    private Logic logic = new Logic();
    private File target = null;
    @FXML public void chooseAudio(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose Audio File");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3"));
        target = chooser.showOpenDialog(new Stage());
        System.out.println(target.getAbsolutePath());
    }

    @FXML public void playAudio(){
        logic.playAudio(target);
        logic.getDuration().addListener((observable, oldValue, newValue) -> {
            playingLabel.setText("Playing: " + target.getName() + "\nDuration: " + newValue);
        });
    }

    public void tryLogIn() throws Exception {
        User user = null;
        if (!emailInput.getText().isEmpty() && !passInput.getText().isEmpty()){
            user = logic.logIn(emailInput.getText(), passInput.getText());
        }
        if(user == null) return;
        playingLabel.setText("Welcome back, " + user.getName() + "!");
    }

    public void tryRegister() throws Exception {
        if (!emailInput.getText().isEmpty() && !passInput.getText().isEmpty()){
            logic.registerUser(emailInput.getText(), passInput.getText());
        }
    }


}
