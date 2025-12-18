package dk.easv.cookiefy.gui.popups;

import dk.easv.cookiefy.be.Playlist;
import dk.easv.cookiefy.be.User;
import dk.easv.cookiefy.bll.Logic;
import dk.easv.cookiefy.gui.util.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistController extends BaseController implements Initializable {

    @FXML private HBox titleBar;
    @FXML private TextField nameInput;

    private User user;
    private Playlist chosenPlaylist;
    private Logic logic = new Logic();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeDraggable(titleBar);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPlaylist(Playlist chosenPlaylist) {
        this.chosenPlaylist = chosenPlaylist;
    }

    public void saveNewPlaylist(){
        String name = nameInput.getText();
        Stage stage = (Stage) titleBar.getScene().getWindow();
        if (chosenPlaylist != null){
            try{
                chosenPlaylist.setName(nameInput.getText());
                logic.updatePlaylist(chosenPlaylist);
                stage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            try{
                logic.newPlaylist(name, user.getUserId());
                stage.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
