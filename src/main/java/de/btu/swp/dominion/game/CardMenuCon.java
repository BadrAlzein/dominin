package de.btu.swp.dominion.game;

import com.esotericsoftware.kryonet.Connection;
import de.btu.swp.dominion.cards.*;
import de.btu.swp.dominion.game.LobbyCon;
import de.btu.swp.dominion.gameLogic.PlayerService;
import de.btu.swp.dominion.gameLogic.GuiDesigner;
import de.btu.swp.dominion.network.Packets.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CardMenuCon extends LobbyCon implements Initializable {
    // BackButtons
    private PlayerService playerService = getPlayerService();
    private ArrayList<Cards> kartenpoolList = new ArrayList<>();
    /** autoComplete List */
    private ArrayList<Cards> autoCompleteList = new ArrayList<>();
    // Objects
    Cards CardDorf = new Dorf();
    Cards CardSchmiede = new Schmiede();
    Cards CardHolzfaeller = new Holzfaeller();
    Cards CardKanzler = new Kanzler();
    Cards CardAbenteurer = new Abenteurer();
    Cards cardLaboratorium = new Laboratorium();
    Cards cardBibliothek = new Bibliothek();
    Cards CardJahrmarkt = new Jahrmarkt();
    Cards CardMarkt = new Markt();
    Cards cardKapelle = new Kapelle();
    Cards cardMiliz = new Miliz();
    Cards cardBurggraben = new Burggraben();
    Cards cardThronsaal = new Thronsaal();
    Cards cardHexe = new Hexe();
    Cards cardRatsversammlung = new Ratsversammlung();
    Cards cardDieb = new Dieb();
    Cards cardWerkstatt = new Werkstatt();
    Cards cardUmbau = new Umbau();
    Cards cardGarten = new Gaerten();

    /** vack arrow to the Lobby */
    @FXML
    private ImageView backArrow;
    @FXML
    private TextField userNameForSInglePlayer;
    // Save Buttons
    /** save Btn from Vorgewaehlte Karten */
    @FXML
    private ImageView erstesSpielBtn;
    @FXML
    private ImageView grossesGeldBtn;
    @FXML
    private Button SaveBtnSelbstGe;
    // Zeiger::
    @FXML
    private ImageView zufalligKartenZeiger;
    @FXML
    private ImageView vorgefertigtZeiger;
    @FXML
    private ImageView selbgewKartenZeiger;
    // Karten
    @FXML
    private ImageView dorfKarte;
    @FXML
    private ImageView schmiedeKarte;
    @FXML
    private ImageView holzfaellerKarte;
    @FXML
    private ImageView kanzlerKarte;
    @FXML
    private ImageView abenteurerKarte;
    @FXML
    private ImageView laboratoriumKarte;
    @FXML
    private ImageView libKarte;
    @FXML
    private ImageView jahrmarktKarte;
    @FXML
    private ImageView marktKarte;
    @FXML
    private ImageView kapelleKarte;
    @FXML
    private ImageView thronsaalKarte;
    @FXML
    private ImageView hexeKarte;
    @FXML
    private ImageView milizKarte;
    @FXML
    private ImageView burggrabenKarte;
    @FXML
    private ImageView ratsversammlungKarte;
    @FXML
    private ImageView diebKarte;
    private ImageView werkstattKarte;
    @FXML
    private ImageView umbauKarte;
    @FXML
    private ImageView gaertenKarte;

    // Ranking
    /** used for the Ranking */
    private int playerOneRanking = 1;
    private int playerTwoRanking = 2;
    private int playerThreeRanking = 3;
    private int playerFourRanking = 4;

    /** Reihenfolge der Spieler */

    @FXML
    private BorderPane mainBorderS;
    @FXML
    private BorderPane mainBorderC;
    @FXML
    private BorderPane mainBorderVor;
    @FXML
    private BorderPane mainBorderRan;
    @FXML
    private BorderPane mainBorderSel;
    @FXML
    private HBox HostHBox;
    @FXML
    private Label HostLabel;
    @FXML
    private MenuButton rankHost;
    @FXML
    private MenuItem hostRankOne;
    @FXML
    private MenuItem hostRankTwo;
    @FXML
    private MenuItem hostRankThree;
    @FXML
    private MenuItem hostRankFour;
    @FXML
    private HBox PlayerTwoHBox;
    @FXML
    private Label PlayerTwoLabel;
    @FXML
    private MenuButton rankPlayerTwo;
    @FXML
    private MenuItem playerTwoRankOne;
    @FXML
    private MenuItem playerTwoRankTwo;
    @FXML
    private MenuItem playerTwoRankThree;
    @FXML
    private MenuItem playerTwoRankFour;
    @FXML
    private HBox PlayerThreeHBox;
    @FXML
    private Label PlayerThreeLabel;
    @FXML
    private MenuButton rankPlayerThree;
    @FXML
    private MenuItem playerThreeRankOne;
    @FXML
    private MenuItem playerThreeRankTwo;
    @FXML
    private MenuItem playerThreeRankThree;
    @FXML
    private MenuItem playerThreeRankFour;
    @FXML
    private HBox PlayerFourHBox;
    @FXML
    private Label PlayerFourLabel;
    @FXML
    private MenuButton rankPlayerFour;
    @FXML
    private MenuItem playerFourRankOne;
    @FXML
    private MenuItem playerFourRankTwo;
    @FXML
    private MenuItem playerFourRankThree;
    @FXML
    private MenuItem playerFourRankFour;
    /**zufällige Kartensatz */
    @FXML
    private HBox randomCards1;
    @FXML
    private HBox randomCards2;
    private int cardHeight =  220; //old 308
    private int cardWidth =  150;   //old 200

    // Action Handler
    @FXML
    void backArrowSettingClicked(MouseEvent event) {
        Parent root;
        // back to lobby from CardMenu
        if (event.getTarget() == backArrow) {
            try {
                if (playerService.getTrigger().getSource() == false) {
                    root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/Lobby.fxml"));
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    if(window.getScene() == null) {
                        Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                        window.setScene(sc);
                    } else {
                        window.getScene().setRoot(root);
                    }
                } else {
                    root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/HauptMenu.fxml"));
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    if(window.getScene() == null) {
                        Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                        window.setScene(sc);
                    } else {
                        window.getScene().setRoot(root);
                    }
                }
            } catch (IOException e) {
                System.err.println("err CardMenu 01: BackArrow Menu");
            }
        } else {
            // back to LobbySettings (Card Menu from XX..Kartensatz Menus)
            playerService.getTrigger().reset();
            try {
                if (playerService.getTrigger().getSource() == false) {
                    playerService.getTrigger().setCardMenuState(3);
                    root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/LobbySettings.fxml"));
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    if(window.getScene() == null) {
                        Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                        window.setScene(sc);
                    } else {
                        window.getScene().setRoot(root);
                    }
                } else {
                    playerService.getTrigger().setCardMenuState(4);
                    root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/einzelSpieler.fxml"));
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    if(window.getScene() == null) {
                        Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                        window.setScene(sc);
                    } else {
                        window.getScene().setRoot(root);
                    }
                }
            } catch (IOException e) {
                System.err.println("err CardMenu 02");
            }
        }
    }

    /** from Settings to Cards Menu */
    @FXML
    void selbstgewClicked(MouseEvent event) {
        /** for SinglePlayer rememeber the username before going to menues */
        if (playerService.getTrigger().getSource() == true) {
            if (!userNameForSInglePlayer.getText().isEmpty()) {
                playerService.getPlayer().setPlayerName(userNameForSInglePlayer.getText());
            }
        }

        if (IsListSelected()) {
            // alert to reSelect the Cards if the kartenpool list already full
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Bestätigung ");
            alert.setContentText("Sie haben bereit eine Listen gewählt wollen Sie die Liste neu wählen?");
            // if agreed to leave
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                playerService.getPlayer().getCardspool().clear();
                kartenpoolList.clear();// reset the list of cards
            }
        }
        // trigger zeiger
        playerService.getTrigger().reset();
        playerService.getTrigger().setTriggerChosen(true);
        playerService.getTrigger().setTriggerCardSets(false);
        playerService.getTrigger().setTriggerRandom(false);

        playerService.getTrigger().setIsInLobbySetting(false);
        Parent root;
        try {
            playerService.getTrigger().setCardMenuState(2);
            root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/SelbstGewaehlterKartensatz.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if(window.getScene() == null) {
                Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                window.setScene(sc);
            } else {
                window.getScene().setRoot(root);
            }
        } catch (IOException e) {
            System.err.println("err CardMenu 03: BackArrow Menu");
            e.printStackTrace();
        }
    }

    /** from Settings to Cards Menu */
    @FXML
    void vorgefertigtKartenClicked(MouseEvent event) {
        /** for SinglePlayer rememeber the username before going to menues */
        if (playerService.getTrigger().getSource() == true) {
            if (!userNameForSInglePlayer.getText().isEmpty()) {
                playerService.getPlayer().setPlayerName(userNameForSInglePlayer.getText());
            }
        }
        if (IsListSelected()) {
            // alert to reSelect the Cards if the kartenpool list already full
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Bestätigung ");
            alert.setContentText("Sie haben bereit eine Listen gewaehlt wollen Sie die Liste neu waehlen?");
            // if agreed to leave
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                playerService.getPlayer().getCardspool().clear();
                kartenpoolList.clear();// reset the list of cards
            }
        }
        // trigger zeiger
        playerService.getTrigger().reset();
        playerService.getTrigger().setTriggerChosen(false);
        playerService.getTrigger().setTriggerCardSets(true);
        playerService.getTrigger().setTriggerRandom(false);

        playerService.getTrigger().setIsInLobbySetting(false);

        Parent root;
        try {
            playerService.getTrigger().setCardMenuState(0);
            root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/VorgefertigterKartensatz.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if(window.getScene() == null) {
                Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                window.setScene(sc);
            } else {
                window.getScene().setRoot(root);
            }
        } catch (IOException e) {
            System.err.println("err CardMenu 04: BackArrow Menu");
            e.printStackTrace();
        }
    }

    /** from Settings to Cards Menu */
    @FXML
    void zufalligKartenClicked(MouseEvent event) {
        /** for SinglePlayer rememeber the username before going to menues */
        if (playerService.getTrigger().getSource() == true) {
            if (!userNameForSInglePlayer.getText().isEmpty()) {
                playerService.getPlayer().setPlayerName(userNameForSInglePlayer.getText());
            }
        }
        if (IsListSelected()) {
            // alert to reSelect the Cards if the kartenpool list already full
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Bestätigung ");
            alert.setContentText("Sie haben bereit eine Listen gewählt wollen Sie die Liste neu wählen?");
            // if agreed to leave
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                playerService.getPlayer().getCardspool().clear();
                kartenpoolList.clear();// reset the list of cards
            }
        }
        // trigger zeiger
        playerService.getTrigger().reset();
        playerService.getTrigger().setTriggerChosen(false);
        playerService.getTrigger().setTriggerCardSets(false);
        playerService.getTrigger().setTriggerRandom(true);

        playerService.getTrigger().setIsInLobbySetting(false);
        Parent root;
        try {
            playerService.getTrigger().setCardMenuState(1);
            root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/ZufalligKartensatz.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if(window.getScene() == null) {
                Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                window.setScene(sc);
            } else {
                window.getScene().setRoot(root);
            }
        } catch (IOException e) {
            System.err.println("err CardMenu 05: BackArrow Menu");
        }
    }

    /** Multiplayer save Cards after selection in different Menus */
    @FXML
    void SaveCards(MouseEvent event) {
        // selbstgewählte
        if (playerService.getTrigger().getCardMenuState() == 2) {
            // add Cards to CardList in PlayerServices
            autoComplete();
        }
        /** vorgewählte erstesSpiel */
        else if (event.getTarget() == erstesSpielBtn) {
            // add Cards to CardList in PlayerServices
            setVorgefertigterKartensatz(1);
        }
        /** vorgewählte grosses Geld */
        else if (event.getTarget() == grossesGeldBtn) {
            // add Cards to CardList in PlayerServices
            setVorgefertigterKartensatz(2);
        }
        // Random (zufälligeKartensatz)
        else {
        }
        // go at the end to lobbySettings
        playerService.getTrigger().setIsInLobbySetting(true);
        // save kartenpoolList into the kartenpool in playerService
        playerService.getPlayer().setCardspool(kartenpoolList);
        if (!playerService.getTrigger().getSource()) {
            playerService.getServerServices().getServerProgram().setCardspool(kartenpoolList);
        }
        Parent root;
        try {
            if (playerService.getTrigger().getSource() == false) {
                playerService.getTrigger().setCardMenuState(3);
                root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/LobbySettings.fxml"));
            } else {
                playerService.getTrigger().setCardMenuState(4);
                root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/einzelSpieler.fxml"));
            }
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if(window.getScene() == null) {
                Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                window.setScene(sc);
            } else {
                window.getScene().setRoot(root);
            }
        } catch (IOException e) {
            System.err.println("err CardMenu 06");
        }
    }

    // Anmiations
    // Zoom ActionHandler

    /** if the Mouse on the Card then zoom out to the Card */
    public void zoomIn(MouseEvent event) {
        ((ImageView) event.getSource()).setFitHeight(((ImageView) event.getSource()).getFitHeight() + 50);
        ((ImageView) event.getSource()).setFitWidth(((ImageView) event.getSource()).getFitWidth() + 50);
    }

    /** if the Mouse on the Card then zoom out to the Card */
    @FXML
    public void zoomOut(MouseEvent event) {
        ((ImageView) event.getSource()).setFitHeight(((ImageView) event.getSource()).getFitHeight() - 50);
        ((ImageView) event.getSource()).setFitWidth(((ImageView) event.getSource()).getFitWidth() - 50);
    }

    /** select& DeSelect Cards for selbstgewählte Kartensatz */
    @FXML
    void selectCard(MouseEvent event) {
        if (kartenpoolList.size() < 10) {
            VBox cardVBox = ((VBox) ((ImageView) event.getSource()).getParent());
            // Dorf
            if (event.getTarget() == dorfKarte) {
                // add Card To list
                if (!kartenpoolList.contains(CardDorf)) {
                    kartenpoolList.add(CardDorf);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(CardDorf);
                    changeToNormal(cardVBox);
                }
            } // schmiede
            else if (event.getTarget() == schmiedeKarte) {
                if (!kartenpoolList.contains(CardSchmiede)) {
                    kartenpoolList.add(CardSchmiede);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(CardSchmiede);
                    changeToNormal(cardVBox);
                }
            } // Holzfäller
            else if (event.getTarget() == holzfaellerKarte) {
                if (!kartenpoolList.contains(CardHolzfaeller)) {
                    kartenpoolList.add(CardHolzfaeller);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(CardHolzfaeller);
                    changeToNormal(cardVBox);
                }
            } // Kanzler
            else if (event.getTarget() == kanzlerKarte) {
                if (!kartenpoolList.contains(CardKanzler)) {
                    kartenpoolList.add(CardKanzler);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(CardKanzler);
                    changeToNormal(cardVBox);
                }
            } // Abenteurer
            else if (event.getTarget() == abenteurerKarte) {
                if (!kartenpoolList.contains(CardAbenteurer)) {
                    kartenpoolList.add(CardAbenteurer);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(CardAbenteurer);
                    changeToNormal(cardVBox);
                }
            } else if (event.getTarget() == laboratoriumKarte) {
                if (!kartenpoolList.contains(cardLaboratorium)) {
                    kartenpoolList.add(cardLaboratorium);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(cardLaboratorium);
                    changeToNormal(cardVBox);
                }
            } else if (event.getTarget() == libKarte) {
                if (!kartenpoolList.contains(cardBibliothek)) {
                    kartenpoolList.add(cardBibliothek);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(cardBibliothek);
                    changeToNormal(cardVBox);
                }
            } else if (event.getTarget() == jahrmarktKarte) {
                if (!kartenpoolList.contains(CardJahrmarkt)) {
                    kartenpoolList.add(CardJahrmarkt);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(CardJahrmarkt);
                    changeToNormal(cardVBox);
                }
            } else if (event.getTarget() == marktKarte) {
                if (!kartenpoolList.contains(CardMarkt)) {
                    kartenpoolList.add(CardMarkt);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(CardMarkt);
                    changeToNormal(cardVBox);
                }
            } else if (event.getTarget() == kapelleKarte) {
                if (!kartenpoolList.contains(cardKapelle)) {
                    kartenpoolList.add(cardKapelle);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(cardKapelle);
                    changeToNormal(cardVBox);
                }
            } else if (event.getTarget() == thronsaalKarte) {
                if (!kartenpoolList.contains(cardThronsaal)) {
                    kartenpoolList.add(cardThronsaal);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(cardThronsaal);
                    changeToNormal(cardVBox);
                }
            } else if (event.getTarget() == hexeKarte) {
                if (!kartenpoolList.contains(cardHexe)) {
                    kartenpoolList.add(cardHexe);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(cardHexe);
                    changeToNormal(cardVBox);
                }
            } else if (event.getTarget() == milizKarte) {
                if(!kartenpoolList.contains(cardMiliz)){
                    kartenpoolList.add(cardMiliz);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(cardMiliz);
                    changeToNormal(cardVBox);
                }
            } else if (event.getTarget() == burggrabenKarte) {
                if(!kartenpoolList.contains(cardBurggraben)){
                    kartenpoolList.add(cardBurggraben);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(cardBurggraben);
                    changeToNormal(cardVBox);
                }
            } else if (event.getTarget() == ratsversammlungKarte) {
                if(!kartenpoolList.contains(cardRatsversammlung)){
                    kartenpoolList.add(cardRatsversammlung);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(cardRatsversammlung);
                    changeToNormal(cardVBox);
                }
            } else if (event.getTarget() == diebKarte) {
                if(!kartenpoolList.contains(cardDieb)){
                    kartenpoolList.add(cardDieb);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(cardDieb);
                }
            } else if (event.getTarget() == werkstattKarte) {
                if(!kartenpoolList.contains(cardWerkstatt)) {
                    kartenpoolList.add(cardWerkstatt);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(cardWerkstatt);
                    changeToNormal(cardVBox);
                }
            } else if (event.getTarget() == umbauKarte) {
                if(!kartenpoolList.contains(cardUmbau)) {
                    kartenpoolList.add(cardUmbau);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(cardUmbau);
                    changeToNormal(cardVBox);
                }
            } else if (event.getTarget() == gaertenKarte) {
                if(!kartenpoolList.contains(cardGarten)) {
                    kartenpoolList.add(cardGarten);
                    changeToGreen(cardVBox);
                } else {
                    kartenpoolList.remove(cardGarten);
                    changeToNormal(cardVBox);
                }
            }
        } else {
            alert("Fehler", "Sie dürfen nicht mehr als 10 Karten auswählen");
        }
    }

    /**
     * change the selected cardsbackground to green
     * 
     * @param bg
     */
    private void changeToGreen(VBox bg) {
        bg.setBackground(new Background(new BackgroundFill(Color.valueOf("#DFDDCA"), null, null)));
    }

    /** return to normal background color */
    private void changeToNormal(VBox bg) {
        bg.setBackground(new Background(new BackgroundFill(null, null, null)));
    }

    /**
     * Save Reihenfolge Btn is in LobbySetting
     */
    @FXML
    void saveInLobbySettingsHandler(MouseEvent event) {
        // TODO: set Reihenfolge
        // if there is two players with the same Ranking
        if (MismatchRanking()) {
            // show error
            alert("Mismatch Error",
                    "Mehrere Spieler Können nicht in der selben Zeit spielen! Korrigieren Sie die Reihenfolge der Spieler.");
        } else {
            // save Reihenfolge
            setupRanking();
            /** send True for the First One in the Connectionlist */
            playerService.getTrigger().seIsRankingSorted(true);
            Packet04NextPlayer turn = new Packet04NextPlayer();
            turn.turn = true;
            turn.playerN = playerService.getServerServices().getAllPlayers().get(0).toString();
            playerService.getClient().getClient().sendTCP(turn);
            // go back to Lobby
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/Lobby.fxml"));
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                if(window.getScene() == null) {
                    Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                    window.setScene(sc);
                } else {
                    window.getScene().setRoot(root);
                }
            } catch (IOException e) {
                System.err.println("err CardMenu 07");
            }
        }
    }

    /** setup the Reihenfolge von Spielern */
    private void setupRanking() {
        try {
            int limit = playerService.getServerServices().getAllPlayers().size();
            Connection[] list = new Connection[limit];
            list[0] = playerService.getServerServices().getAllPlayers().get(playerOneRanking - 1);
            if (limit == 2) {
                list[1] = playerService.getServerServices().getAllPlayers().get(playerTwoRanking - 1);
            } else if (limit == 3) {
                list[1] = playerService.getServerServices().getAllPlayers().get(playerTwoRanking - 1);
                list[2] = playerService.getServerServices().getAllPlayers().get(playerThreeRanking - 1);
            } else if (limit == 4) {
                list[1] = playerService.getServerServices().getAllPlayers().get(playerTwoRanking - 1);
                list[2] = playerService.getServerServices().getAllPlayers().get(playerThreeRanking - 1);
                list[3] = playerService.getServerServices().getAllPlayers().get(playerFourRanking - 1);
            }

            playerService.getServerServices().getAllPlayers().clear();

            for (int i = 0; i < limit; i++) {
                playerService.getServerServices().getAllPlayers().add(list[i]);
            }
        } catch (Exception e) {
            System.err.println("err CardMenu 08: fxml refresh exception aber nicht entscheident!");
        }
    }

    /**
     * work with the Treger Class to show a zeiger of the selected set of cards in
     * the lobby Settings
     */
    private void showZeiger() {
        if (playerService.getTrigger().getTriggerChosen()) {
            selbgewKartenZeiger.setVisible(true);
            vorgefertigtZeiger.setVisible(false);
            zufalligKartenZeiger.setVisible(false);

        } else if (playerService.getTrigger().getTriggerCardSets()) {
            vorgefertigtZeiger.setVisible(true);
            selbgewKartenZeiger.setVisible(false);
            zufalligKartenZeiger.setVisible(false);

        } else if (playerService.getTrigger().getTriggerRandom()) {
            zufalligKartenZeiger.setVisible(true);
            selbgewKartenZeiger.setVisible(false);
            vorgefertigtZeiger.setVisible(false);

        }
    }

    @FXML
    void rank(ActionEvent event) {
        SwitchPlayerOne(event);
        SwitchPlayerTwo(event);
        SwitchPlayerThree(event);
        SwitchPlayerFour(event);
    }

    /** einstellung für die Reihenfolge für Player 1 */
    private void SwitchPlayerOne(ActionEvent event) {
        if (event.getTarget() == hostRankOne) {
            swap("1", rankHost.getText());
            rankHost.setText("1");
            playerOneRanking = 1;
        } else if (event.getTarget() == hostRankTwo) {
            swap("2", rankHost.getText());
            rankHost.setText("2");
            playerOneRanking = 2;
        } else if (event.getTarget() == hostRankThree) {
            swap("3", rankHost.getText());
            rankHost.setText("3");
            playerOneRanking = 3;
        } else if (event.getTarget() == hostRankFour) {
            swap("4", rankHost.getText());
            rankHost.setText("4");
            playerOneRanking = 4;
        }
    }

    /** einstellung für die Reihenfolge für Player 2 */
    private void SwitchPlayerTwo(ActionEvent event) {
        if (event.getTarget() == playerTwoRankOne) {
            swap("1", rankPlayerTwo.getText());
            rankPlayerTwo.setText("1");
            playerTwoRanking = 1;
        } else if (event.getTarget() == playerTwoRankTwo) {
            swap("2", rankPlayerTwo.getText());
            rankPlayerTwo.setText("2");
            playerTwoRanking = 2;
        } else if (event.getTarget() == playerTwoRankThree) {
            swap("3", rankPlayerTwo.getText());
            rankPlayerTwo.setText("3");
            playerTwoRanking = 3;
        } else if (event.getTarget() == playerTwoRankFour) {
            swap("4", rankPlayerTwo.getText());
            rankPlayerTwo.setText("4");
            playerTwoRanking = 4;
        }
    }

    /** einstellung für die Reihenfolge für Player 3 */
    private void SwitchPlayerThree(ActionEvent event) {
        if (event.getTarget() == playerThreeRankOne) {
            swap("1", rankPlayerThree.getText());
            rankPlayerThree.setText("1");
            playerThreeRanking = 1;
        } else if (event.getTarget() == playerThreeRankTwo) {
            swap("2", rankPlayerThree.getText());
            rankPlayerThree.setText("2");
            playerThreeRanking = 2;
        } else if (event.getTarget() == playerThreeRankThree) {
            swap("3", rankPlayerThree.getText());
            rankPlayerThree.setText("3");
            playerThreeRanking = 3;
        } else if (event.getTarget() == playerThreeRankFour) {
            swap("4", rankPlayerThree.getText());
            rankPlayerThree.setText("4");
            playerThreeRanking = 4;
        }
    }

    /** einstellung für die Reihenfolge für Player 4 */
    private void SwitchPlayerFour(ActionEvent event) {
        if (event.getTarget() == playerFourRankOne) {
            swap("1", rankPlayerFour.getText());
            rankPlayerFour.setText("1");
            playerFourRanking = 1;
        } else if (event.getTarget() == playerFourRankTwo) {
            swap("2", rankPlayerFour.getText());
            rankPlayerFour.setText("2");
            playerFourRanking = 2;
        } else if (event.getTarget() == playerFourRankThree) {
            swap("3", rankPlayerFour.getText());
            rankPlayerFour.setText("3");
            playerFourRanking = 3;
        } else if (event.getTarget() == playerFourRankFour) {
            swap("4", rankPlayerFour.getText());
            rankPlayerFour.setText("4");
            playerFourRanking = 4;
        }
    }

    /**
     * swap will swap a two ranking with each other
     * 
     * @return
     */
    private void swap(String oldPos, String newPos) {
        if (rankHost.getText().equals(oldPos)) {
            rankHost.setText(newPos);
            playerOneRanking = Integer.parseInt(newPos);
        } else if (rankPlayerTwo.getText().equals(oldPos)) {
            rankPlayerTwo.setText(newPos);
            playerTwoRanking = Integer.parseInt(newPos);
        } else if (rankPlayerThree.getText().equals(oldPos)) {
            rankPlayerThree.setText(newPos);
            playerThreeRanking = Integer.parseInt(newPos);
        } else if (rankPlayerFour.getText().equals(oldPos)) {
            playerFourRanking = Integer.parseInt(newPos);
            rankPlayerFour.setText(newPos);
        }

    }

    /** if two Players have the same Ranking then give true */
    private Boolean MismatchRanking() {
        if ((playerOneRanking == playerTwoRanking) || (playerOneRanking == playerThreeRanking)
                || (playerOneRanking == playerFourRanking)) {
            return true;
        } else if ((playerTwoRanking == playerThreeRanking) || (playerTwoRanking == playerFourRanking)
                || (playerThreeRanking == playerFourRanking)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * show the avalible Players in the Ranking. Well take the limit of the Server
     * and show HBoxs as much as the limit
     */
    private void showAvaliblePlayers() {
        showPlayerOne();
        if (playerService.getServerServices().getAllPlayers().size() == 2) {
            showPlayerTwo();
        } else if (playerService.getServerServices().getAllPlayers().size() == 3) {
            showPlayerTwo();
            showPlayerThree();
        } else if (playerService.getServerServices().getAllPlayers().size() == 4) {
            showPlayerTwo();
            showPlayerThree();
            showPlayerFour();
        }
    }

    private void showPlayerOne() {
        HostHBox.setVisible(true);
        HostLabel.setText(playerService.getPlayer().getPlayerName());
    }

    private void showPlayerTwo() {
        PlayerTwoHBox.setVisible(true);
        hostRankTwo.setVisible(true);
        PlayerTwoLabel.setText(playerService.getServerServices().getAllPlayers().get(1).toString());
    }

    private void showPlayerThree() {
        PlayerThreeHBox.setVisible(true);
        hostRankTwo.setVisible(true);
        hostRankThree.setVisible(true);

        playerTwoRankThree.setVisible(true);
        PlayerThreeLabel.setText(playerService.getServerServices().getAllPlayers().get(2).toString());
    }

    private void showPlayerFour() {
        PlayerFourHBox.setVisible(true);
        hostRankTwo.setVisible(true);
        hostRankThree.setVisible(true);
        hostRankFour.setVisible(true);

        playerTwoRankThree.setVisible(true);
        playerTwoRankFour.setVisible(true);
        playerThreeRankFour.setVisible(true);
        PlayerFourLabel.setText(playerService.getServerServices().getAllPlayers().get(3).toString());
    }

    /** live changings */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (playerService.getTrigger().getIsInLobbySetting()) {
            showZeiger();
            if (playerService.getTrigger().getSource() == false) {
                showAvaliblePlayers();

            } else {
                if (!(playerService.getPlayer().getPlayerName() == null)) {
                    userNameForSInglePlayer.setText(playerService.getPlayer().getPlayerName());
                }
            }
        }
        if (playerService.getTrigger().getCardMenuState() == 4) {
            GuiDesigner.scaling(mainBorderS);
        } else if (playerService.getTrigger().getCardMenuState() == 0) {
            GuiDesigner.scaling(mainBorderVor);
        } else if (playerService.getTrigger().getCardMenuState() == 1) {
            autoComplete();
            for (int i = 0; i < 5; i++) {
                ImageView image = new ImageView(kartenpoolList.get(i).getImagePath());
                image.setFitHeight(cardHeight);
                image.setFitWidth(cardWidth);
                randomCards1.getChildren().add(image);
            }
            for (int i = 5; i < 10; i++) {
                ImageView image = new ImageView(kartenpoolList.get(i).getImagePath());
                image.setFitHeight(cardHeight);
                image.setFitWidth(cardWidth);
                randomCards2.getChildren().add(image);
            }

            GuiDesigner.scaling(mainBorderRan);
        } else if (playerService.getTrigger().getCardMenuState() == 2) {
            GuiDesigner.scaling(mainBorderSel);
        } else {
            GuiDesigner.scaling(mainBorderC);
        }

    }

    //////// Adding Cards

    /**
     * add the Vorgefertigter Kartensatz to the playerServices id = 1 erstes Spiel
     * id = 2 grosses Geld
     */
    private void setVorgefertigterKartensatz(int ID) {
        switch (ID) {
        // erstes Spiel
        case 1:
            autoComplete();
            break;
        // grosses Geld set
        case 2:
            autoComplete();
            break;
        default:
            autoComplete();
        }
    }

    /** bei selbstgewählte Karten autocomplete von Karten */
    private void autoComplete() {
        if (autoCompleteList.size() <= 0) {
            setupAutoCompleteList();
        }
        while (kartenpoolList.size() < 10) {
            Random random = new Random();
            int randomCard = random.nextInt(autoCompleteList.size());
            if (!kartenpoolList.contains(autoCompleteList.get(randomCard))) {
                kartenpoolList.add(autoCompleteList.get(randomCard));
            }
        }
    }

    /** autoComplete List */
    private void setupAutoCompleteList() {
        autoCompleteList.add(CardDorf);
        autoCompleteList.add(CardSchmiede);
        autoCompleteList.add(CardHolzfaeller);
        autoCompleteList.add(CardKanzler);
        autoCompleteList.add(cardKapelle);
        autoCompleteList.add(CardAbenteurer);
        autoCompleteList.add(cardLaboratorium);
        autoCompleteList.add(cardBibliothek);
        autoCompleteList.add(CardJahrmarkt);
        autoCompleteList.add(CardMarkt);
        autoCompleteList.add(cardKapelle);
        autoCompleteList.add(cardMiliz);
        autoCompleteList.add(cardBurggraben);
        autoCompleteList.add(cardThronsaal);
        autoCompleteList.add(cardHexe);
        autoCompleteList.add(cardRatsversammlung);
        autoCompleteList.add(cardDieb);
        autoCompleteList.add(cardWerkstatt);
        autoCompleteList.add(cardUmbau);
        autoCompleteList.add(cardGarten);
    }

    /**
     * get an autoCompleted List : used in the default value of kartenpool
     * 
     * @return
     */
    public ArrayList<Cards> getAutoCompleteList() {
        autoComplete();
        return this.kartenpoolList;
    }

    /**
     * if a list is already selected
     * 
     * @return
     */
    private Boolean IsListSelected() {
        return ((vorgefertigtZeiger.isVisible()) || (zufalligKartenZeiger.isVisible())
                || (selbgewKartenZeiger.isVisible()));
    }

    /** open Spielfeld from einzelspieler.fxml */
    @FXML
    public void moveToSpielFeldBtnClicked(MouseEvent event) {
        if (userNameForSInglePlayer.getText().isEmpty()) {
            alert("Fehler", "Enter your Name first!");
        } else {
            // change to lobby
            playerService.getPlayer().setPlayerName(userNameForSInglePlayer.getText());
            playerService.getPlayer().setHost(true);
            // check if the card list is full
            if (playerService.getPlayer().getCardspool().size() == 0) {
                autoComplete();
                playerService.getPlayer().setCardspool(kartenpoolList);
            }
            /** give the Host true if he in single Player */
            playerService.getPlayer().setYourTurn(true);
            try {
                playerService.initServer(getSpielerAnzahl());
                playerService.initConnection();
                playerService.getTrigger().reset();
                playerService.getTrigger().setIsInLobbySetting(false);

                Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/game.fxml"));
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                if(window.getScene() == null) {
                    Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                    window.setScene(sc);
                } else {
                    window.getScene().setRoot(root);
                }
            } catch (IOException e) {
                System.err.println("err CardMenu 09: Fehler beim Laden des Games");
                e.printStackTrace();
            }
            
        }
    }
}