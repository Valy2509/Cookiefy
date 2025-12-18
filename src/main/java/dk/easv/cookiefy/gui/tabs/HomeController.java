package dk.easv.cookiefy.gui.tabs;

import dk.easv.cookiefy.be.Song;
import dk.easv.cookiefy.be.User;
import dk.easv.cookiefy.bll.Logic;
import dk.easv.cookiefy.gui.MainController;
import dk.easv.cookiefy.gui.components.RecentController;
import dk.easv.cookiefy.gui.inferfaces.IUserAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class HomeController implements IUserAware {

    @FXML private HBox recentContainer;

    private User user;
    private MainController mainController;
    private Logic logic = new Logic();

    @Override
    public void setUser(User user) {
        this.user = user;
        updateRecents();
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void updateRecents(){
        List<Song> songs = logic.getRecents(user);
        if (songs == null) return;
        recentContainer.getChildren().clear();
        for (Song song : songs){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/cookiefy/gui/components/recent-view.fxml"));
                VBox songBox = loader.load();
                RecentController controller = loader.getController();
                controller.setSong(song);
                controller.setMainController(mainController);
                recentContainer.getChildren().add(songBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
