package de.btu.swp.dominion.game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import de.btu.swp.dominion.gameLogic.GuiDesigner;
import de.btu.swp.dominion.gameLogic.MetaData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.awt.Toolkit;

public class SettingCon extends MainMenuCon implements Initializable {
    private MetaData metaData = new MetaData();
    @FXML
    private BorderPane mainBorder;
    @FXML
    private MenuButton item;
    @FXML
    private MenuButton bar;
    @FXML
    private ImageView volumeBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        item.setText(GuiDesigner.getWidth() + " x " + GuiDesigner.getHeight());
        if (GuiDesigner.getFullScreen()) {
            bar.setText("Vollbild");
        } else {
            bar.setText("Fenster");
        }
        GuiDesigner.scaling(mainBorder);

        /** for the Volum Btn */
        if (!metaData.getSoundOnMeta()) {
            volumeBtn.setImage(musicBox.getVolumOffImg());
        } else {
            volumeBtn.setImage(musicBox.getVolumOnImg());
        }
    }

    @FXML
    void btnSaveClicked(MouseEvent event) {
        if (item.getText().equals("1920 x 1440 (4:3)")) {
            GuiDesigner.setHeight(1440);
            GuiDesigner.setWidth(1920);
            // write in Settings.xml
            metaData.setHeightMeta(1440);
            metaData.setWidthMeta(1920);

        } else if (item.getText().equals("1920 x 1080 (16:9)")) {
            GuiDesigner.setHeight(1080);
            GuiDesigner.setWidth(1920);
            // write in Settings.xml
            metaData.setHeightMeta(1080);
            metaData.setWidthMeta(1902);
        } else if (item.getText().equals("1600 x 900 (16:9)")) {
            GuiDesigner.setHeight(900);
            GuiDesigner.setWidth(1600);
            // write in Settings.xml
            metaData.setHeightMeta(900);
            metaData.setWidthMeta(1600);
        } else if (item.getText().equals("1280×720 (16:9)")) {
            GuiDesigner.setHeight(720);
            GuiDesigner.setWidth(1280);
            // write in Settings.xml
            metaData.setHeightMeta(720);
            metaData.setWidthMeta(1280);
        } else if (item.getText().equals("800 x 600 (4:3)")) {
            GuiDesigner.setHeight(600);
            GuiDesigner.setWidth(800);
            // write in Settings.xml
            metaData.setHeightMeta(600);
            metaData.setWidthMeta(800);
        }

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (bar.getText().equals("Fenster")) {
            GuiDesigner.setFullScreen(false);
            window.setFullScreen(false);
            metaData.setFullScreenMeta(false);
        } else if (bar.getText().equals("Vollbild")) {
            GuiDesigner.setHeight(Toolkit.getDefaultToolkit().getScreenSize().height);
            GuiDesigner.setWidth(Toolkit.getDefaultToolkit().getScreenSize().width);
            window.setFullScreenExitHint("");
            window.setFullScreen(true);
            GuiDesigner.setFullScreen(true);
            metaData.setFullScreenMeta(true);
            metaData.setWidthMeta(GuiDesigner.getWidth());
            metaData.setHeightMeta(GuiDesigner.getHeight());
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/HauptMenu.fxml"));
            if(window.getScene() == null) {
                Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                window.setScene(sc);
            } else {
                window.setWidth(GuiDesigner.getWidth());
                window.setHeight(GuiDesigner.getHeight());
                window.getScene().setRoot(root);
            }
        } catch (IOException e) {
            System.err.println("err Setting 02: Fehler beim Laden des Hauptmenü");
        }
    }

    @FXML
    void solution(ActionEvent event) {
        item.setText(((MenuItem) event.getSource()).getText());
    }

    @FXML
    void modus(ActionEvent event) {
        bar.setText(((MenuItem) event.getSource()).getText());
    }

    @FXML
    void btnBackClicked(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/HauptMenu.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if(window.getScene() == null) {
                Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                window.setScene(sc);
            } else {
                window.getScene().setRoot(root);
            }
        } catch (IOException e) {
            System.err.println("err Setting 01: Fehler beim Laden des Hauptmenü");
        }
    }

    @FXML
    void volumeHandler(MouseEvent event) {
        if (musicBox.getBackgroundAudio().isPlaying()) {
            musicBox.getBackgroundAudio().stop();
            metaData.setSoundOnMeta(false);
            volumeBtn.setImage(musicBox.getVolumOffImg());
        } else {
            metaData.setSoundOnMeta(true);
            volumeBtn.setImage(musicBox.getVolumOnImg());
            musicBox.getBackgroundAudio().play();
        }
    }
}