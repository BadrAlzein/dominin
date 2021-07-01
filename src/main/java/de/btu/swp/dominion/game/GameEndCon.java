package de.btu.swp.dominion.game;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import de.btu.swp.dominion.gameLogic.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameEndCon extends GameCon implements Initializable {

    private PlayerService playerService = getPlayerService2();

    @FXML
    private HBox pointsHBox;

    @FXML
    private BorderPane mainBorder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GuiDesigner.scaling(mainBorder);

        for (int i = 0; i < playerService.getPlayers().size(); i++) {
            VBox vbox = (VBox) pointsHBox.getChildren().get(i);
            OtherPlayers others = playerService.getPlayers().get(i);
            ((Label) vbox.getChildren().get(1)).setText(others.getPlayerName());
            ((Label) vbox.getChildren().get(2)).setText("Punkte: " + others.getPoints());
            vbox.setVisible(true);
        }
    }

    @FXML
    void gameendBtnClicked(MouseEvent event) throws IOException {
        if (metaData.getSoundOnMeta()) {
            musicBox.getBackgroundAudio().play();
        }
        
        if (playerService.getPlayer().getHost()) {
            playerService.getServerServices().stopServer();
        }
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
            System.err.println("err GameCon 02: Fehler bei Siegerbild");
        }
        if (playerService.getPlayer().getHost()) {
            playerService.getServerServices().stopServer();
        } else {
            // leave the Server
            playerService.getClient().getClient().close();
        }
    }

    @FXML
    void exitSpielHandler(MouseEvent event) {
        if (playerService.getPlayer().getHost()) {
            playerService.getServerServices().stopServer();
        } else {
            // leave the Server
            playerService.getClient().getClient().close();
        }
        System.exit(0);
    }

    @FXML
    private void goToMain(MouseEvent event) {
        /** close if he is not connected */
        if (!playerService.getClient().getClient().isConnected()) {
            if (metaData.getSoundOnMeta()) {
                musicBox.getBackgroundAudio().play();
            }
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
                System.err.println("err GameCon 02: Fehler bei Siegerbild");
            }
        }
    }
}