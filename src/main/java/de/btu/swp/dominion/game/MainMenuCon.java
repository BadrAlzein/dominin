package de.btu.swp.dominion.game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import de.btu.swp.dominion.gameLogic.GuiDesigner;
import de.btu.swp.dominion.gameLogic.MetaData;
import de.btu.swp.dominion.gameLogic.PlayerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainMenuCon extends Launcher implements Initializable {
    public static PlayerService playerService = new PlayerService();
    MetaData metaData = new MetaData();
    private Boolean twoPlayer = false;
    private Boolean therePlayer = false;
    private Boolean forePlayer = false;
    // Boolean to get the bot level
    private Boolean leicht = false;
    private Boolean mittel = false;
    private Boolean schwer = false;

    @FXML
    private BorderPane mainBorderC;
    @FXML
    private BorderPane mainBorderB;
    @FXML
    private BorderPane mainBorder;

    @FXML
    private TextField IPTextField;
    @FXML
    private ImageView backArrow;
    @FXML
    private ImageView vorgefertigtButton;
    @FXML
    private ImageView zufalligKartenButton;
    @FXML
    private ImageView selbgewKarten;
    @FXML
    private MenuItem numberOfplayerstwo;
    @FXML
    private MenuItem numberOfplayersthree;
    @FXML
    private MenuItem numberOfplayersfour;
    @FXML
    private MenuButton menuButtonAnzSpieler;
    @FXML
    private MenuButton menuButtonSchw;
    @FXML
    private MenuButton menuBarerstellen;
    @FXML
    private TextField spielerNameField;
    @FXML
    private TextField spielerName;
    @FXML
    private TextField playerNameTextField;
    @FXML
    private MenuButton menuButtonSpielerAnzahl;
    // Auswahlhaken
    @FXML
    private ImageView zufalligKartenZeiger;
    @FXML
    private ImageView vorgefertigtZeiger;
    @FXML
    private ImageView selbgewKartenZeiger;
    @FXML
    private Button btnEinzelSpieler;
    @FXML
    private Button btnSpielErstellen;
    @FXML
    private Button btnSpielbeitreten;
    @FXML
    private Button btnSetting;
    @FXML
    private Button btnSpielverlassen;
    @FXML
    private Button btnTutorial;

    @FXML
    private ImageView volumeBtn;

    /** from Hauptmenu to einzielSpieler */
    @FXML
    public void einzelSpielerClicked(MouseEvent event) {
        try {
            playerService.getTrigger().setIsInLobbySetting(true);
            playerService.getTrigger().setSource(true);
            playerService.getTrigger().setState(0);
            playerService.getTrigger().setCardMenuState(4);
            Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/einzelSpieler.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if(window.getScene() == null) {
                Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                window.setScene(sc);
            } else {
                window.getScene().setRoot(root);
            }
        } catch (IOException e) {
            System.err.println("err Controller 01: Fehler beim Laden des Einzelspielermenüs");
            e.printStackTrace();
        }
    }

    /** from Hauptmenu to Spielerstellen */
    @FXML
    public void spielErstellenClicked(MouseEvent event) {
        try {
            playerService.getTrigger().setState(1);
            AnchorPane root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/SpielErstellen.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if(window.getScene() == null) {
                Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                window.setScene(sc);
            } else {
                window.getScene().setRoot(root);
            }
        } catch (IOException e) {
            System.err.println("err Controller 05: Fehler beim Laden von Spiel erstellen");
            e.printStackTrace();
        }
    }

    /** from Hauptmenu to spielbeitretten */
    @FXML
    public void spielbeitretenClicked(MouseEvent event) {

        try {
            playerService.getTrigger().setState(2);
            AnchorPane root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/Spielbeitreten.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if(window.getScene() == null) {
                Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                window.setScene(sc);
            } else {
                window.getScene().setRoot(root);
            }
        } catch (IOException e) {
            System.err.println("err Controller 06: Fehler beim Laden von Spiel beitreten");
            e.printStackTrace();
        }
    }

    /** Spielerstellen frame to lobby frame */
    @FXML
    public void goToLobbySpielErstellenClicked(MouseEvent event) {
        metaData.setPlayerNameMeta(spielerName.getText());
        if (spielerName.getText().isEmpty()) {
            alert("failed", "Enter your Name first!");
        } else {
            // change to lobby
            try {
                playerService.getTrigger().setState(0);
                playerService = new PlayerService(spielerName.getText(), true);
                playerService.initServer(getSpielerAnzahl());
                playerService.initConnection(); // noch pending
                Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/Lobby.fxml"));
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                if(window.getScene() == null) {
                    Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                    window.setScene(sc);
                } else {
                    window.getScene().setRoot(root);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("err Controller 08: Fehler beim Laden der Lobby");
                e.printStackTrace();
            }
        }
    }

    /** join the party (from Spielbeitretten to lobby) */
    @FXML
    public void goToLobbySpBeitretenClicked(MouseEvent event) {
        metaData.setPlayerNameMeta(playerNameTextField.getText());
        metaData.setIPMeta(IPTextField.getText());
        if (playerNameTextField.getText().equals("")) {
            alert("Fehler", "Erst den Namen eingeben!");
        } else {

            // change to lobby
            playerService = new PlayerService(playerNameTextField.getText(), false);
            playerService.initConnection();

            try {
                playerService.getTrigger().setState(0);
                // if the player connected then go to lobby
                Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/Lobby.fxml"));
                if (playerService.getClient().getClient().isConnected()) {
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    if(window.getScene() == null) {
                        Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                        window.setScene(sc);
                    } else {
                        window.getScene().setRoot(root);
                    }
                } else {
                    alert("failed", "There is no party with this IP! Please ask your Party Leader for the IP");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("err Controller 10: Fehler beim Laden der Lobby");
                alert("failed", "check your IP");
            }
        }
    }

    /** every backarrow Btn in the Hauptmenu */
    @FXML
    void backArrowClicked(MouseEvent event) {
        try {
            playerService.getTrigger().setState(0);
            playerService.getTrigger().setIsInLobbySetting(false);
            Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/HauptMenu.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if(window.getScene() == null) {
                Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                window.setScene(sc);
            } else {
                window.getScene().setRoot(root);
            }
        } catch (IOException e) {
            System.err.println("err Controller 12: Fehler beim Laden des Hauptmenü");
        }
    }

    @FXML
    public void numberOfplayersfourClicked(ActionEvent event) {
        menuButtonAnzSpieler.setText("4");
        twoPlayer = false;
        therePlayer = false;
        forePlayer = true;
    }

    @FXML
    public void numberOfplayersthreeClicked(ActionEvent event) {
        menuButtonAnzSpieler.setText("3");
        twoPlayer = false;
        therePlayer = true;
        forePlayer = false;
    }

    @FXML
    public void numberOfplayerstwoClicked(ActionEvent event) {
        menuButtonAnzSpieler.setText("2");
        twoPlayer = true;
        therePlayer = false;
        forePlayer = false;
    }

    @FXML
    public void anzSpiel2Clicked(ActionEvent event) {
        menuButtonSpielerAnzahl.setText("2");
        twoPlayer = true;
        therePlayer = false;
        forePlayer = false;
    }

    @FXML
    public void anzSpiel3Clicked(ActionEvent event) {
        menuButtonSpielerAnzahl.setText("3");
        twoPlayer = false;
        therePlayer = true;
        forePlayer = false;
    }

    @FXML
    public void anzSpiel4Clicked(ActionEvent event) {
        menuButtonSpielerAnzahl.setText("4");
        twoPlayer = false;
        therePlayer = false;
        forePlayer = true;
    }

    @FXML
    public void menuButtonLeichtClicked(ActionEvent event) {
        menuButtonSchw.setText("Leicht");
        leicht = true;
        mittel = false;
        schwer = false;

    }

    @FXML
    public void menuButtonMittelClicked(ActionEvent event) {
        menuButtonSchw.setText("Mittel");
        leicht = false;
        mittel = true;
        schwer = false;
    }

    @FXML
    public void menuButtonSchwerClicked(ActionEvent event) {
        menuButtonSchw.setText("Schwer");
        leicht = false;
        mittel = false;
        schwer = true;
    }

    @FXML
    public void menuButtonSpielerAnzahl(MouseEvent event) {
    }

    /** Spiel verlassen im HauptMenu */
    @FXML
    public void spielVerlassenClicked(MouseEvent event) {
        System.exit(0);
    }

    /**
     * git the number of the Chosen PLayer used in the createSpiel
     */
    public int getSpielerAnzahl() {
        if (forePlayer) {
            return 4;
        } else if (therePlayer) {
            return 3;
        } else { // 2 player
            return 2;
        }
    }

    /**
     * git the level of the bots 1 Leicht 2 mittel 3 schwer
     */
    public int getBotLevel() {
        if (leicht) {
            return 1;
        } else if (schwer) {
            return 3;
        } else { // mittel
            return 2;
        }
    }

    /** send and Alert with a titel and a message as a javafx Window */
    public void alert(String titel, String Message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titel);
        alert.setContentText(Message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /** volum Btn init */
        if (volumeBtn != null) {
            if (!metaData.getSoundOnMeta()) {
                volumeBtn.setImage(musicBox.getVolumOffImg());
            } else if (metaData.getSoundOnMeta()) {
                volumeBtn.setImage(musicBox.getVolumOnImg());
            }
        }
        /** responsive init */
        if (playerService.getTrigger().getState() == 0) {
            GuiDesigner.scaling(mainBorder);
        } else if (playerService.getTrigger().getState() == 1) {
            GuiDesigner.scaling(mainBorderC);
        } else if (playerService.getTrigger().getState() == 2) {
            GuiDesigner.scaling(mainBorderB);
        }

        /** default playerName init */
        if (playerNameTextField != null) {
            if (!metaData.getPlayerNameMeta().equals("null")) {
                playerNameTextField.setText(metaData.getPlayerNameMeta());
            }
        } else if (spielerName != null) {
            if (!metaData.getPlayerNameMeta().equals("null")) {
                spielerName.setText(metaData.getPlayerNameMeta());
            }
        }
        /** default ip init */
        if (IPTextField != null) {
            if (!metaData.getIpMeta().equals("null")) {
                IPTextField.setText(metaData.getIpMeta());
            }
        }
    }

    @FXML
    public void btnSettingClicked(MouseEvent event) {
        try {
            playerService.getTrigger().setState(0);
            Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/Setting.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if(window.getScene() == null) {
                Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                window.setScene(sc);
            } else {
                window.getScene().setRoot(root);
            }
        } catch (IOException e) {
            System.err.println("err Controller 13: Fehler beim Laden des Einstellung");
            e.printStackTrace();
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