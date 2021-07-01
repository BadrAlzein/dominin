package de.btu.swp.dominion.game;

import java.io.IOException;

import de.btu.swp.dominion.gameLogic.GuiDesigner;
import de.btu.swp.dominion.gameLogic.MetaData;
import de.btu.swp.dominion.gameLogic.Trigger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Launcher extends Application {
    public GuiDesigner musicBox = new GuiDesigner();
    private MetaData metaData = new MetaData();
    public Trigger volumTrigger = new Trigger();

    @Override
    public void start(Stage stage) {
        try {
            // get the settings from meta and feed it to gui Designer
            GuiDesigner.setWidth((int) metaData.getWidthMeta());
            GuiDesigner.setHeight((int) metaData.getHeightMeta());
            GuiDesigner.setFullScreen(metaData.getFullscreen());

            Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/HauptMenu.fxml"));
            stage.getIcons().add(new Image("/src/main/resources/icons/Bildschirmfoto 2020-01-06 um 15.36.06.png"));
            stage.setTitle("Dominion");
            // start Music
            if (metaData.getSoundOnMeta()) {
                musicBox.getBackgroundAudio().play();
            }
            // adding the font
            Font.loadFont(getClass().getResourceAsStream("/src/main/resources/fonts/AppleChancery.ttf"), 14);
            stage.setScene(new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight()));
            stage.setResizable(false);
            stage.setX(0.0);
            stage.setY(0.0);
            /** on pressing the x button close the application */
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    Platform.exit();
                    System.exit(0);
                }
            });
            stage.setFullScreen(GuiDesigner.getFullScreen());
            stage.setFullScreenExitHint("");
            stage.show();
        } catch (IOException e) {
            System.err.println("err Launcher 01: Fehler beim Laden des Hauptmenüs");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}