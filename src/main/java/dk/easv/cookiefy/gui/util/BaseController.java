package dk.easv.cookiefy.gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public abstract class BaseController {
    private double posX;
    private double posY;

    protected Stage getStage(Node node) {
        return (Stage) node.getScene().getWindow();
    }

    public void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        getStage(source).close();
    }

    public void minimizeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        getStage(source).setIconified(true);
    }

    public void toggleMaximized(ActionEvent event) {
        Node source = (Node) event.getSource();
        Button btn = (Button) source;
        Stage stage = getStage(source);
        if(stage.isMaximized()){
            stage.setMaximized(false);
            btn.setText("❐");
        }else{
            stage.setMaximized(true);
            btn.setText("⬜");
        }
    }

    public void makeDraggable(Node titleBar){
        titleBar.setOnMousePressed(event -> {
            posX = event.getSceneX();
            posY = event.getSceneY();
        });

        titleBar.setOnMouseDragged(event -> {
            Stage stage = (Stage) titleBar.getScene().getWindow();
            stage.setX(event.getScreenX() - posX);
            stage.setY(event.getScreenY() - posY);
        });
    }
}
