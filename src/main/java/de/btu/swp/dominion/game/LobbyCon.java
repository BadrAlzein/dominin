package de.btu.swp.dominion.game;

import de.btu.swp.dominion.gameLogic.GuiDesigner;
import de.btu.swp.dominion.gameLogic.OtherPlayers;
import de.btu.swp.dominion.network.Packets.*;
import de.btu.swp.dominion.gameLogic.PlayerService;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LobbyCon extends MainMenuCon implements Initializable {

    private PlayerService playerService = MainMenuCon.playerService;
    private static ObservableList<String> chatmessages = FXCollections.observableArrayList();

    @FXML
    private BorderPane mainBorder;
    @FXML
    private ListView<String> lobbyChatList = new ListView<String>(chatmessages);
    @FXML
    private TextField lobbyTextField;
    @FXML
    private Button lobbySendBtn;
    @FXML
    private ImageView LobbyBackBtn;
    @FXML
    private Button lobbyReadyBtn;
    @FXML
    private ImageView SettingsBtn;
    @FXML
    private ImageView volumeBtn;
    @FXML
    private HBox SettingsBtnHBox;
    @FXML
    private VBox playerVBox;
    private Boolean ready = false;

    public PlayerService getPlayerService() {
        return this.playerService;
    }

    @FXML
    public void EventHandler(KeyEvent e) throws Exception {
        if (e.getCode() == KeyCode.ENTER) {
            Packet01Message MessagePacket = new Packet01Message();
            MessagePacket.message = lobbyTextField.getText();
            MessagePacket.clientname = playerService.getPlayer().getPlayerName();
            MessagePacket.stage = 1;
            playerService.getClient().getClient().sendTCP(MessagePacket);
            lobbyTextField.setText("");
        }
    }

    @FXML
    public void setName(MouseEvent event) {
        for (int i = 0; i < playerService.getPlayers().size() + 1; i++) {
            HBox hbox = (HBox) playerVBox.getChildren().get(i);
            Label playerLabel = (Label) hbox.getChildren().get(0);
            ImageView playerArrow = (ImageView) hbox.getChildren().get(1);
            hbox.setVisible(true);
            if (i == 0 && playerService.getPlayer().getHost()) {
                playerLabel.setText(playerService.getPlayer().getPlayerName());
            } else if (i == 0) {
                OtherPlayers other = playerService.getPlayers().get(i);
                playerLabel.setText(other.getPlayerName());
                HBox hbox1 = (HBox) playerVBox.getChildren().get(i + 1);
                Label playerLabel1 = (Label) hbox1.getChildren().get(0);
                hbox1.setVisible(true);
                playerLabel1.setText(playerService.getPlayer().getPlayerName());
                ((ImageView) hbox1.getChildren().get(1)).setVisible(ready);
                i++;
            } else {
                OtherPlayers other = playerService.getPlayers().get(i - 1);
                playerLabel.setText(other.getPlayerName());
                playerArrow.setVisible(other.getReady());
            }
        }
    }

    @FXML
    public void ActionHandler(MouseEvent event) throws Exception {

        // Action for Back to Menu Btn
        if (event.getTarget() == LobbyBackBtn) {
            // alert to leave the game
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Bestätigung ");
            alert.setContentText("Wollen Sie wirklich die Party verlassen?");
            Optional<ButtonType> result = alert.showAndWait();
            // if agreed to leave
            if (result.get() == ButtonType.OK) {
                playerService.getTrigger().reset();
                playerService.getTrigger().setIsInLobbySetting(false);
                // close the Server if you are a host
                if (playerService.getPlayer().getHost()) {
                    playerService.getServerServices().stopServer();
                }
                // leave the Server
                playerService.getClient().getClient().close();
                // change to mainMenu
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
                    System.err.println("err GameCon 100 ");
                }
            }
        }
        // send btn to the Chat
        if (event.getTarget() == lobbySendBtn) {
            if (!lobbyTextField.getText().equals("")) {
                Packet01Message messagePacket = new Packet01Message();
                messagePacket.message = lobbyTextField.getText();
                messagePacket.clientname = playerService.getPlayer().getPlayerName();
                messagePacket.stage = 1;
                playerService.getClient().getClient().sendTCP(messagePacket);
                lobbyTextField.setText("");
            }
        }
    }

    @FXML
    void readyBtnClicked(MouseEvent event) {
        if (playerService.getPlayer().getHost()) {
            if (playerService.allReady()) {
                if (playerService.getPlayer().getCardspool().size() == 0) {
                    CardMenuCon cards = new CardMenuCon();
                    playerService.getPlayer().setCardspool(cards.getAutoCompleteList());
                }
                
                lobbyReadyBtn.setText("Spiel wird gestartet..");
                playerService.getServerServices().sendCardpool(playerService.getPlayer().getCardspool());

                // send to all players
                Packet01Message messagePacket = new Packet01Message();
                messagePacket.message = " hat das Spiel gestartet!! ";
                messagePacket.clientname = playerService.getPlayer().getPlayerName();
                playerService.getClient().getClient().sendTCP(messagePacket);

                // send game launching to all Clients
                Packet03GameLaunch launchPacket = new Packet03GameLaunch();
                launchPacket.start = true;
                playerService.getClient().getClient().sendTCP(launchPacket);
            } else {
                alert("Warnung", "Es sind noch nicht alle Spieler bereit.");
            }
        } else {
            Packet16ReadyCheck check = new Packet16ReadyCheck();
            check.playerName = playerService.getPlayer().getPlayerName();
            if (lobbyReadyBtn.getText().equals("Bereit")) {
                ready = true;
                check.ready = true;
                lobbyReadyBtn.setText("warte auf Host");
            } else {
                ready = false;
                check.ready = false;
                lobbyReadyBtn.setText("Bereit");
            }
            playerService.getClient().getClient().sendTCP(check);
        }
    }

    /** connect to Game */
    public void goToGameLaunch(MouseEvent event) {
        if (playerService.getPlayer().getReady() == true) {
            playerService.getTrigger().reset();
            playerService.getTrigger().setIsInLobbySetting(false);
            /** give the host YourTurn if no Ranking is Selected */
            if ((playerService.getPlayer().getHost()) && (!playerService.getTrigger().getIsRankingSorted())) {
                playerService.getPlayer().setYourTurn(true);
            }
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/game.fxml"));
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                if(window.getScene() == null) {
                    Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                    window.setScene(sc);
                } else {
                    window.getScene().setRoot(root);
                }
            } catch (IOException e) {
                System.out.println("err Lobby 01: Fehler beim Laden von Game");
                e.printStackTrace();
            }
        }
        /** close if he is not connected */
        if (!playerService.getClient().getClient().isConnected()) {
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
                System.err.println("err GameCon 100 ");
            }
        }
    }

    public void addTextToList(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                chatmessages.add(message);
            }
        });
    }

    /**
     * The Action for the Settings Btn in the Loppy go to Chose Card
     * 
     * @param event
     */
    @FXML
    void SettingsBtnClicked(MouseEvent event) {
        Parent root;
        playerService.getTrigger().setIsInLobbySetting(true);
        playerService.getTrigger().setSource(false);
        playerService.getTrigger().setCardMenuState(3);
        try {
            root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/LobbySettings.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if(window.getScene() == null) {
                Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                window.setScene(sc);
            } else {
                window.getScene().setRoot(root);
            }
        } catch (IOException e) {
            System.out.println("err Lobby 02: Fehler beim Laden von Lobby Settings");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GuiDesigner.scaling(mainBorder);
        // dont show Setting for Clients
        if (!playerService.getPlayer().getHost()) {
            SettingsBtn.setVisible(false);
            SettingsBtnHBox.setVisible(false);
            // send name of the player to host
            Packet05Players pn = new Packet05Players();
            pn.player = playerService.getPlayer().getPlayerName();
            playerService.getClient().getClient().sendTCP(pn);
            playerService.getTrigger().setSource(false);
        } else {
            boolean l = true;
            while (l) {
                System.out.print("");
                if (playerService.getServerServices().getAllPlayers().size() > 0) {
                    l = false;
                }
            }
            if (!playerService.getServerServices().getAllPlayers().get(0).toString().equals(playerService.getPlayer().getPlayerName())) {
                // put his name in playerlist
                playerService.getServerServices().getAllPlayers().get(0).setName(playerService.getPlayer().getPlayerName());
                playerService.getTrigger().setSource(false);
            }

            lobbyReadyBtn.setText("Spiel starten!");
        }

        /** Chat setup in Lobby */
        lobbyChatList.setItems(chatmessages);
        lobbyChatList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> lb, String old_val, String new_val) {
            }
        });

        /** for the Volum Btn */
        if (!metaData.getSoundOnMeta()) {
            volumeBtn.setImage(musicBox.getVolumOffImg());
        } else {
            volumeBtn.setImage(musicBox.getVolumOnImg());
        }
    }

    /** music */
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