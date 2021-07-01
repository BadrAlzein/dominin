package de.btu.swp.dominion.game;

import de.btu.swp.dominion.cards.*;
import de.btu.swp.dominion.network.Packets.*;
import de.btu.swp.dominion.gameLogic.GameLogic;
import de.btu.swp.dominion.gameLogic.GuiDesigner;
import de.btu.swp.dominion.gameLogic.OtherPlayers;
import de.btu.swp.dominion.gameLogic.PlayerService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.BorderPane;

public class GameCon extends LobbyCon implements Initializable {

    public PlayerService playerService = getPlayerService();
    private GameLogic gamelogic = new GameLogic(getPlayerService());
    private LinkedList<Cards> newlist = new LinkedList<Cards>();
    private ArrayList<Cards> copyList = new ArrayList<>();

    /** when state is true the player is in game, otherwise he is in spectator */
    private boolean state;

    /** log */
    private static ObservableList<String> logMessages = FXCollections.observableArrayList();
    @FXML
    public ListView<String> logListView = new ListView<String>(logMessages);
    @FXML
    private BorderPane mainBorder;
    /** game */
    @FXML
    private VBox playground1;
    @FXML
    private Rectangle player2TurnBG;
    @FXML
    private Rectangle player3TurnBG;
    @FXML
    private Rectangle player4TurnBG;

    /** spectator */
    @FXML
    private VBox playground2;
    @FXML
    private HBox cardpool;
    @FXML
    private HBox kartenInHand;
    @FXML
    private HBox playedCards;
    @FXML
    private HBox trashImageViews;
    @FXML
    private Pane TrashWindow;
    @FXML
    private Slider TrashSlider;
    @FXML
    private HBox poolHBox1;
    @FXML
    private HBox poolHBox2;
    @FXML
    private VBox poolVBox;
    @FXML
    private ImageView anwesenKarte;
    @FXML
    private ImageView herzogtumKarte;
    @FXML
    private ImageView provinzKarte;
    @FXML
    private ImageView goldKarte;
    @FXML
    private ImageView silberKarte;
    @FXML
    private ImageView kupferKarte;
    @FXML
    private ImageView ablage;
    @FXML
    private ImageView greatImageView;
    @FXML
    private Label labelplayer2;
    @FXML
    private Label labelplayer3;
    @FXML
    private Label labelplayer4;
    @FXML
    private Label dorfLabel1;
    @FXML
    private Label dorfLabel2;
    @FXML
    private Label dorfLabel3;
    @FXML
    private Label dorfLabel4;
    @FXML
    private Label dorfLabel5;
    @FXML
    private Label dorfLabel6;
    @FXML
    private Label dorfLabel7;
    @FXML
    private Label dorfLabel8;
    @FXML
    private Label dorfLabel9;
    @FXML
    private Label dorfLabel10;
    @FXML
    private Label GeldLabel;
    @FXML
    private Label AttackLabel;
    @FXML
    private Label KaufLabel;
    @FXML
    private Label anwesenLabel;
    @FXML
    private Label herzogtumLabel;
    @FXML
    private Label provinzLabel;
    @FXML
    private Label kupferLabel;
    @FXML
    private Label silberLabel;
    @FXML
    private Label goldLabel;
    @FXML
    private ImageView popUpCard1;
    @FXML
    private ImageView popUpCard2;
    @FXML
    private ImageView popUpCard3;
    @FXML
    private ImageView popUpCard4;
    @FXML
    private ImageView popUpCard5;
    @FXML
    private ImageView popUpCard6;
    @FXML
    private ImageView popUpCard7;
    @FXML
    private ImageView specCard1;
    @FXML
    private ImageView specCard2;
    @FXML
    private ImageView specCard3;
    @FXML
    private ImageView specCard4;
    @FXML
    private ImageView specCard5;
    @FXML
    private ImageView specCard6;
    @FXML
    private VBox leftCorner;
    // popupWindow
    @FXML
    private Pane effectWindow;
    @FXML
    private Label popupMessageLabel;
    @FXML
    private ImageView volumeBtn;
    private double opacity = 0.7;

    LinkedList<Integer> popUpWindowIndexlist = new LinkedList<Integer>();
    ArrayList<ImageView> effectImages = new ArrayList<ImageView>();

    public PlayerService getPlayerService2() {
        return this.playerService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /**
         * send to all client if its your Turn to show that you have the turn by the
         * others in the obber left corner
         */
        if (playerService.getPlayer().getYourTurn()) {
            Packet19TurnZeiger turnZeiger = new Packet19TurnZeiger();
            turnZeiger.playerName = playerService.getPlayer().getPlayerName();
            playerService.getClient().getClient().sendTCP(turnZeiger);
        }
        /** log */
        logListView.setItems(logMessages);
        logListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
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

        if (playerService.getPlayer().getYourTurn() == true) {
            startGame();
        } else {
            startSpectator();
        }

        GuiDesigner.scaling(mainBorder);

        effectImages.add(popUpCard1);
        effectImages.add(popUpCard2);
        effectImages.add(popUpCard3);
        effectImages.add(popUpCard4);
        effectImages.add(popUpCard5);
        effectImages.add(popUpCard6);
        effectImages.add(popUpCard7);

        playerService.getPlayer().getCardspool().add(new Anwesen());
        playerService.getPlayer().getCardspool().add(new Herzogtum());
        playerService.getPlayer().getCardspool().add(new Provinz());
        playerService.getPlayer().getCardspool().add(new Gold());
        playerService.getPlayer().getCardspool().add(new Silber());
        playerService.getPlayer().getCardspool().add(new Kupfer());

        playerService.getPlayer().getStartValue();
        for (int i = 1; i <= 7; i++) {
            Cards kupfer = new Kupfer();
            playerService.getPlayer().getDeck().add(kupfer);
        }
        for (int i = 1; i <= 3; i++) {
            Cards anwesen = new Anwesen();
            playerService.getPlayer().getDeck().add(anwesen);
            playerService.getPlayer().setPoints(1);
        }

        gamelogic.initHand();
        updateHand();
        setupCountersLabels();
        initPool();
        setNameGame();
        initSliderListener();
        updatePool();
    }

    /** shows pool Cards from list */
    public void initPool() {
        Image image;

        // set every image in variable pool
        for (int i = 0; i < 10; i++) {
            image = new Image(playerService.getPlayer().getPoolImage(i));
            if (i < 5) {
                ((ImageView) ((Group) poolHBox1.getChildren().get(i)).getChildren().get(0)).setImage(image);
            } else {
                ((ImageView) ((Group) poolHBox2.getChildren().get(i - 5)).getChildren().get(0)).setImage(image);
            }
        }
        updateBuyable();
    }

    @FXML
    void hover(MouseEvent event) {
        Image image;
        if (event.getTarget() == anwesenKarte) {
            image = new Image("/src/main/resources/basis/Anwesen.png");
        } else if (event.getTarget() == herzogtumKarte) {
            image = new Image("/src/main/resources/basis/Herzogtum.png");
        } else if (event.getTarget() == provinzKarte) {
            image = new Image("/src/main/resources/basis/Provinz.png");
        } else if (event.getTarget() == kupferKarte) {
            image = new Image("/src/main/resources/basis/Kupfer.png");
        } else if (event.getTarget() == silberKarte) {
            image = new Image("/src/main/resources/basis/Silber.png");
        } else if (event.getTarget() == goldKarte) {
            image = new Image("/src/main/resources/basis/Gold.png");
        } else {
            image = ((ImageView) event.getSource()).getImage();
        }
        greatImageView.setImage(image);
        greatImageView.setVisible(true);
    }

    @FXML
    void dehover(MouseEvent event) {
        greatImageView.setVisible(false);
    }

    @FXML
    void initSliderListener() {
        TrashSlider.valueProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                TrashSlider.setValue(newValue.intValue());
                for (int i = newValue.intValue(); i < newValue.intValue() + 5; i++) {
                    Node newImageView = trashImageViews.getChildren().get(i - newValue.intValue());
                    if (newImageView instanceof ImageView) {
                        ((ImageView) newImageView).setImage(new Image(playerService.getPlayer().getTrashCardImage(i)));
                    }
                }
            }
        });
    }

    @FXML
    public void updateSpectator(MouseEvent event) {
        if (playerService.getPlayer().getSpecCards().size() > 5) {
            specCard6.setImage(new Image(playerService.getPlayer().getSpecCards().get(4).getImagePath()));
            specCard6.setVisible(true);
        }
        if (playerService.getPlayer().getSpecCards().size() > 4) {
            specCard5.setImage(new Image(playerService.getPlayer().getSpecCards().get(4).getImagePath()));
            specCard5.setVisible(true);
        }
        if (playerService.getPlayer().getSpecCards().size() > 3) {
            specCard4.setImage(new Image(playerService.getPlayer().getSpecCards().get(3).getImagePath()));
            specCard4.setVisible(true);
        }
        if (playerService.getPlayer().getSpecCards().size() > 2) {
            specCard3.setImage(new Image(playerService.getPlayer().getSpecCards().get(2).getImagePath()));
            specCard3.setVisible(true);
        }
        if (playerService.getPlayer().getSpecCards().size() > 1) {
            specCard2.setImage(new Image(playerService.getPlayer().getSpecCards().get(1).getImagePath()));
            specCard2.setVisible(true);
        }
        if (playerService.getPlayer().getSpecCards().size() > 0) {
            specCard1.setImage(new Image(playerService.getPlayer().getSpecCards().get(0).getImagePath()));
            specCard1.setVisible(true);
        }
        if (playerService.getPlayer().getSpecCards().size() == 0) {
            specCard1.setVisible(false);
            specCard2.setVisible(false);
            specCard3.setVisible(false);
            specCard4.setVisible(false);
            specCard5.setVisible(false);
            specCard6.setVisible(false);
        }
        milizPlayed();
        ratsversammlungPlayed();
    }

    /** calls all update methods */
    public void update() {
        updatePlayedCards();
        updateHand();
        setupCountersLabels();
        updateDiscardDeck();
        updatePool();
        kapellePlayed();
        werkstattPlayed();
        umbauPlayed();
        thronsaalPlayed();
        diebPlayed();
        diebPlayed2();
        updateLeftCorner();
        updateBuyable();
    }

    public void updateUmbau(int oldValue) {
        ArrayList<Cards> poolCards = copyPoolCardsUmbau(oldValue);
        if(poolCards.size() > 0 && poolCards.get(0).getCost() <= (oldValue + 2)) {
            popUpCard1.setImage(new Image(poolCards.get(0).getImagePath()));
        } else {
            popUpCard1.setVisible(false);
        }
        if(poolCards.size() > 1 && poolCards.get(1).getCost() <= (oldValue + 2)) {
            popUpCard2.setImage(new Image(poolCards.get(1).getImagePath()));
        } else {
            popUpCard2.setVisible(false);
        }
        if(poolCards.size() > 2 && poolCards.get(2).getCost() <= (oldValue + 2)) {
            popUpCard3.setImage(new Image(poolCards.get(2).getImagePath()));
        } else {
            popUpCard3.setVisible(false);
        }
        if(poolCards.size() > 3 && poolCards.get(3).getCost() <= (oldValue + 2)) {
            popUpCard4.setImage(new Image(poolCards.get(3).getImagePath()));
        } else {
            popUpCard4.setVisible(false);
        }
        if(poolCards.size() > 4 && poolCards.get(4).getCost() <= (oldValue + 2)) {
            popUpCard5.setImage(new Image(poolCards.get(4).getImagePath()));
        } else {
            popUpCard5.setVisible(false);
        }
        if(poolCards.size() > 5 && poolCards.get(5).getCost() <= (oldValue + 2)) {
            popUpCard6.setImage(new Image(poolCards.get(5).getImagePath()));
        } else {
            popUpCard6.setVisible(false);
        }
        if(poolCards.size() > 6 && poolCards.get(6).getCost() <= (oldValue + 2)) {
            popUpCard7.setImage(new Image(poolCards.get(6).getImagePath()));
        } else {
            popUpCard7.setVisible(false);
        }
    }


    public void updateBuyable() {
        ColorAdjust grey = new ColorAdjust();
        ColorAdjust normal = new ColorAdjust();
        grey.setSaturation(-1);
        for (int i = 0; i < playerService.getPlayer().getCardspool().size(); i++) {
            if (playerService.getPlayer().getMoney() < playerService.getPlayer().getCardspool().get(i).getCost()
                    || playerService.getPlayer().getCardnumber().get(i) == 0) {
                if (i < 5) {
                    ((ImageView) ((Group) poolHBox1.getChildren().get(i)).getChildren().get(0)).setEffect(grey);
                } else if (i < 10) {
                    ((ImageView) ((Group) poolHBox2.getChildren().get(i - 5)).getChildren().get(0)).setEffect(grey);
                } else {
                    ((ImageView) ((Group) poolVBox.getChildren().get(i - 10)).getChildren().get(0)).setEffect(grey);
                }
            } else {
                if (i < 5) {
                    ((ImageView) ((Group) poolHBox1.getChildren().get(i)).getChildren().get(0)).setEffect(normal);
                } else if (i < 10) {
                    ((ImageView) ((Group) poolHBox2.getChildren().get(i - 5)).getChildren().get(0)).setEffect(normal);
                } else {
                    ((ImageView) ((Group) poolVBox.getChildren().get(i - 10)).getChildren().get(0)).setEffect(normal);
                }
            }
        }
    }

    public void updateSpecCards() {
        playerService.getPlayer().getSpecCards().clear();
    }

    /** setup Labels for the Attack/Geld/Kauf Counters in Game */
    private void setupCountersLabels() {
        if (playerService.getPlayer().getMoney() >= 10) {
            GeldLabel.setFont(new Font(3.0));
        } else {
            GeldLabel.setFont(new Font(20.0));
        }
        GeldLabel.setText("G " + playerService.getPlayer().getMoney());
        AttackLabel.setText("A " + playerService.getPlayer().getAction());
        KaufLabel.setText("K " + playerService.getPlayer().getBuys());
    }

    /** updates the handcards on Gui */
    public void updateHand() {
        kartenInHand.getChildren().clear();
        for (int i = 0; i < playerService.getPlayer().getHand().size(); i++) {
            Image url = new Image(playerService.getPlayer().getCardImage(i));
            ImageView newImage = new ImageView(url);
            newImage.setFitHeight(208);
            newImage.setFitWidth(120);
            if (kartenInHand.getChildren().size() > 8) {
                kartenInHand.setSpacing(-120 + kartenInHand.getPrefWidth() / kartenInHand.getChildren().size());
            } else {
                kartenInHand.setSpacing(0);
            }
            newImage.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    greatImageView.setVisible(true);
                    greatImageView.setImage(url);
                }
            });
            newImage.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    greatImageView.setVisible(false);
                }
            });
            newImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (state == true && playerService.getPlayer().getAction() >= 1) {
                        for (int i = 0; i < kartenInHand.getChildren().size(); i++) {
                            if (kartenInHand.getChildren().get(i) == ((ImageView) e.getSource())) {
                                if (playerService.getPlayer().getHand().get(i).getCardType().equals("Aktion")) {
                                    gamelogic.playClickedCard(i);
                                    update();
                                }
                            }
                        }
                    }
                }
            });
            kartenInHand.getChildren().add(newImage);
        }

        Packet09HandCardNumber card = new Packet09HandCardNumber();
        card.NumberOfCardInHand = kartenInHand.getChildren().size();
        card.playerName = playerService.getPlayer().getPlayerName();
        playerService.getClient().getClient().sendTCP(card);
    }

    /** updates played Cards in Gui */
    public void updatePlayedCards() {
        playedCards.getChildren().clear();
        LinkedList<Cards> list = new LinkedList<Cards>(updateList());
        for (int i = 0; i < list.size(); i++) {
            Image url = new Image(list.get(i).getImagePath());
            ImageView newImage = new ImageView(url);
            Group group = new Group();
            StackPane stack = new StackPane();
            Rectangle rect = new Rectangle();
            Label label = new Label();
            newImage.setFitHeight(208);
            newImage.setFitWidth(120);
            stack.setPrefHeight(40.0);
            stack.setPrefHeight(49.0);
            rect.setWidth(25.0);
            rect.setHeight(26.0);
            rect.setFill(Color.RED);
            label.setPrefHeight(25.6);
            label.setPrefWidth(19.2);
            String path = list.get(i).getImagePath();
            label.setText(Integer.toString(numberOfCards(path) - 1));
            label.setTextFill(Color.WHITE);
            label.setFont(new Font(16.0));
            stack.getChildren().add(rect);
            stack.getChildren().add(label);
            group.getChildren().add(newImage);
            group.getChildren().add(stack);
            playedCards.getChildren().add(group);
        }
    }

    public int numberOfCards(String path) {
        int value = 1;
        for (int i = playerService.getPlayer().getPlayedCardsList().size() - 1; i >= 0; i--) {
            String namea = playerService.getPlayer().getPlayedCardImage(i);
            if (namea == path) {
                value++;
            }
        }
        return value;
    }

    public LinkedList<Cards> updateList() {
        LinkedList<String> newlist2 = new LinkedList<String>();
        newlist.clear();
        for (Cards card : playerService.getPlayer().getPlayedCardsList()) {
            newlist.add(card);
        }
        for (int i = newlist.size() - 1; i >= 0; i--) {
            String path = newlist.get(i).getName();
            newlist2.add(path);
        }
        Set<String> set = new LinkedHashSet<String>(newlist2);
        newlist2 = new LinkedList<String>(set);
        newlist.clear();
        for (int k = newlist2.size() - 1; k >= 0; k--) {
            Cards card = playerService.stringtoCards(newlist2.get(k));
            newlist.add(card);
        }
        return newlist;
    }

    public void updateDiscardDeck() {
        int lastcard = playerService.getPlayer().getDiscardDeck().size() - 1;
        if (playerService.getPlayer().getDiscardDeck().size() > 0) {
            ablage.setImage(new Image(playerService.getPlayer().getDiscardCardImage(lastcard)));
            Packet08DiscardDeck card = new Packet08DiscardDeck();
            card.cardName = playerService.getPlayer().getDiscardDeck().getLast().getImagePath();
            card.playerName = playerService.getPlayer().getPlayerName();
            playerService.getClient().getClient().sendTCP(card);
        }
        if (playerService.getPlayer().getDiscardDeck().size() == 0) {
            ablage.setImage(new Image("/src/main/resources/backgrounds/Blanko.png"));
            Packet08DiscardDeck card = new Packet08DiscardDeck();
            card.cardName = "/src/main/resources/backgrounds/Blanko.png";
            card.playerName = playerService.getPlayer().getPlayerName();
            playerService.getClient().getClient().sendTCP(card);
        }
    }

    public void updateLeftCorner() {
        String currentPlayer = playerService.getTrigger().getCurrentPlayerName();
        for (int i = 0; i < playerService.getPlayers().size(); i++) {
            OtherPlayers other = playerService.getPlayers().get(i);
            Group group = (Group) leftCorner.getChildren().get(i);
            // updated number of Cards in Hand
            Label otherHand = (Label) ((StackPane) group.getChildren().get(2)).getChildren().get(1);
            otherHand.setText(other.getNumberCardsInHand() + "");
            // updated discard Card
            ImageView discard = (ImageView) ((HBox) ((VBox) group.getChildren().get(1)).getChildren().get(0))
                    .getChildren().get(0);
            discard.setImage(new Image(other.getFirstDiscardCard()));
            // updated boxes
            group.getChildren().get(0).setVisible(other.getPlayerName().equals(currentPlayer));
        }
    }

    public void updatePool() {
        StackPane pane;
        for (int i = 0; i < playerService.getPlayer().getCardspool().size(); i++) {
            if (i < 5) {
                pane = ((StackPane) ((Group) poolHBox1.getChildren().get(i)).getChildren().get(1));
            } else if (i < 10) {
                pane = ((StackPane) ((Group) poolHBox2.getChildren().get(i - 5)).getChildren().get(1));
            } else {
                pane = ((StackPane) ((Group) poolVBox.getChildren().get(i - 10)).getChildren().get(1));
            }
            Label label = (Label) pane.getChildren().get(1);
            label.setText(playerService.getPlayer().getCardnumber().get(i).toString());
        }
    }

    /**
     * when the button is clicked the player end his turn and the spectatorscene
     * appear
     */
    @FXML
    void btnCleaningPhaseClicked(MouseEvent event) throws IOException {
        /** log */
        gamelogic.sendToLog("null", "endTurn");
        // close all popups Window
        effectWindow.setVisible(false);
        TrashWindow.setVisible(false);
        playerService.getPlayer().getStartValue();
        // füge hand zum Ablagestapel
        playerService.getPlayer().addDiscardDeck(playerService.getPlayer().getHand());
        playerService.getPlayer().setHand(new LinkedList<Cards>());
        // füge gespielte Karten zum Ablagestapel
        playerService.getPlayer().addDiscardDeck(playerService.getPlayer().getPlayedCardsList());
        playerService.getPlayer().setPlayedCardsList(new LinkedList<Cards>());
        playerService.drawCard();
        playerService.drawCard();
        playerService.drawCard();
        playerService.drawCard();
        playerService.drawCard();
        playedCards.getChildren().clear();

        // when the player is host, then he can change the list in serverProgram
        if (playerService.getPlayer().getHost() == true) {
            changeTurn(playerService.getPlayer().getPlayerName(), playerService);

        } else {
            // otherwise the player send the next player to the host
            Packet06EndTurn player = new Packet06EndTurn();
            player.name = playerService.getPlayer().getPlayerName();
            playerService.getClient().getClient().sendTCP(player);
        }
        startSpectator();
        updateSpecCards();
        update();
    }

    /** in develop setName method */
    @FXML
    public void changeScene(MouseEvent event) {
        if (playerService.getPlayer().getEnd() == true) {
            musicBox.getBackgroundAudio().stop();
            gamelogic.sendpoints();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/GameEnd.fxml"));
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                if (window.getScene() == null) {
                    Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                    window.setScene(sc);
                } else {
                    window.getScene().setRoot(root);
                }
            } catch (IOException e) {
                System.err.println("err GameCon 02: Fehler bei Siegerbild");
                e.printStackTrace();
            }
        } else {
            if (!gamelogic.getMilizActive()) {
                if (playerService.getPlayer().getYourTurn()) {
                    startGame();
                    playerService.getPlayer().setYourTurn(false);
                }
            }
            milizPlayed();
            updateLeftCorner();
            updateDiscardDeck();
        }
        /** close if he is not connected */
        if (!playerService.getClient().getClient().isConnected()) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/HauptMenu.fxml"));
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                if (window.getScene() == null) {
                    Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                    window.setScene(sc);
                } else {
                    window.setWidth(GuiDesigner.getWidth());
                    window.setHeight(GuiDesigner.getHeight());
                    window.getScene().setRoot(root);
                }
            } catch (IOException e) {
                System.err.println("err GameCon 02: Fehler bei Siegerbild");
                e.printStackTrace();
            }
        }
    }

    public void startGame() {
        state = true;
        playground2.setVisible(false);
        playground1.setVisible(true);
        cardpool.setVisible(true);
        update();
    }

    public void startSpectator() {
        state = false;
        playground2.setVisible(true);
        playground1.setVisible(false);
        cardpool.setVisible(false);
        update();
    }

    /**
     * This methode search the player after the actual player (is static because it
     * must be accessed by ClientNetworkListerner)
     */
    public static void changeTurn(String name, PlayerService ps) {
        for (int i = 1; i <= ps.getServerServices().getAllPlayers().size(); i++) {
            // searched which one the actual player in list
            if (ps.getServerServices().getAllPlayers().get(i - 1).toString().equals(name)) {
                Packet04NextPlayer turnPacket = new Packet04NextPlayer();
                turnPacket.turn = true;

                // the first player of the list get his turn, when the last player clicked the
                // button
                if (i == ps.getServerServices().numberOfPLayerConected()) {
                    turnPacket.playerN = ps.getServerServices().getAllPlayers().getFirst().toString();
                } else {
                    turnPacket.playerN = ps.getServerServices().getAllPlayers().get(i).toString();
                }

                ps.getClient().getClient().sendTCP(turnPacket);
                break;
            }
        }
    }

    @FXML
    void btnChatClicked(MouseEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/Chat.fxml"));
            Stage chat = new Stage();
            chat.setScene(new Scene(root, 732, 552));
            chat.initStyle(StageStyle.UNDECORATED);
            chat.show();
        } catch (IOException e) {
            System.out.println("err Game 11: Fehler beim Laden vom Chat");
            e.printStackTrace();
        }
    }

    @FXML
    void btnExitClicked(MouseEvent event) throws IOException {
        ButtonType exit = new ButtonType("verlassen");
        ButtonType stay = new ButtonType("bleiben");
        Alert closeConfirmation = new Alert(AlertType.CONFIRMATION, "Sind Sie sicher, dass Sie das Spiel verlassen?",
                exit, stay);
        closeConfirmation.setTitle("Bestätigung");
        Optional<ButtonType> result = closeConfirmation.showAndWait();
        if (result.get() == exit) {
            if (playerService.getPlayer().getHost()) {
                playerService.getPlayer().setShutDownServer(true);
                playerService.getServerServices().stopServer();
            } else {
                playerService.getClient().getClient().close();
            }

            if (!playerService.getClient().getClient().isConnected()) {
                try {
                    playerService.clearAllList();
                    Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/FXML/HauptMenu.fxml"));
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    if (window.getScene() == null) {
                        Scene sc = new Scene(root, GuiDesigner.getWidth(), GuiDesigner.getHeight());
                        window.setScene(sc);
                    } else {
                        window.setWidth(GuiDesigner.getWidth());
                        window.setHeight(GuiDesigner.getHeight());
                        window.getScene().setRoot(root);
                    }
                } catch (IOException e) {
                    System.err.println("err GameCon 100 Gengler Fehler zu ungenau :)");
                }
            }
        }
    }

    @FXML
    void btnTrashClicked(MouseEvent event) {
        if (TrashWindow.isVisible()) {
            TrashWindow.setVisible(false);
            if (playground2.isVisible()) {
                playground1.getChildren().get(1).setVisible(true);
                playground1.setVisible(false);
            }
        } else {
            TrashWindow.setVisible(true);
            if (playground2.isVisible()) {
                playground2.setVisible(true);
                playground1.setVisible(true);
                playground1.getChildren().get(1).setVisible(false);
            }

            if (playerService.getPlayer().getTrashCardsList().size() < 6) {
                for (int i = 0; i < playerService.getPlayer().getTrashCardsList().size(); i++) {
                    Node newImageView = trashImageViews.getChildren().get(i);
                    if (newImageView instanceof ImageView) {
                        ((ImageView) newImageView).setImage(new Image(playerService.getPlayer().getTrashCardImage(i)));
                    }
                }
                TrashSlider.setVisible(false);
            }
            if (playerService.getPlayer().getTrashCardsList().size() > 5) {
                for (int i = 0; i < 5; i++) {
                    Node newImageView = trashImageViews.getChildren().get(i);
                    if (newImageView instanceof ImageView) {
                        ((ImageView) newImageView).setImage(new Image(playerService.getPlayer().getTrashCardImage(i)));
                    }
                }
                double maxVisiblecards = 5;
                TrashSlider.setVisible(true);
                TrashSlider.setMin(0);
                TrashSlider.setMax(((double) playerService.getPlayer().getTrashCardsList().size()) - maxVisiblecards);
                TrashSlider.setValue(0);
            }
        }
    }

    /**
     * here we have to ask which card is on the pool, for set the
     * image(ablagestapel) and get the price(playMoneyonBoard) TODO: when we can buy
     * other cards we can do the playerservice lines in gamelogic with parameters
     */
    @FXML
    void btnCardClicked(MouseEvent event) {
        playerService.getPlayer().setAction(-playerService.getPlayer().getAction());
        Cards card = new Blanko();
        ImageView clickedCard = ((ImageView) event.getSource());
        for (int i = 0; i < playerService.getPlayer().getCardspool().size(); i++) {
            if (i < 5) {
                if (clickedCard == ((ImageView) ((Group) poolHBox1.getChildren().get(i)).getChildren().get(0))) {
                    card = playerService.getPlayer().getCardspool().get(i);
                }
            } else if (i < 10) {
                if (clickedCard == ((ImageView) ((Group) poolHBox2.getChildren().get(i - 5)).getChildren().get(0))) {
                    card = playerService.getPlayer().getCardspool().get(i);
                }
            } else {
                if (clickedCard == ((ImageView) ((Group) poolVBox.getChildren().get(i - 10)).getChildren().get(0))) {
                    card = playerService.getPlayer().getCardspool().get(i);
                }
            }
        }

        Boolean buy = (playerService.getPlayer().getMoney() >= card.getCost())
                && (playerService.getPlayer().getBuys() >= 1);

        if (buy) {
            // change the number of Cards in pool
            StackPane redRect = (StackPane) ((Group) clickedCard.getParent()).getChildren().get(1);
            Label label = ((Label) redRect.getChildren().get(1));
            if (label.getText().equals("1")) {
                clickedCard.setDisable(true);
                ColorAdjust grey = new ColorAdjust();
                grey.setSaturation(-1);
                clickedCard.setEffect(grey);
                // if Provinz is empty the game ends
                if (card.getName().equals("Provinz")) {
                    Packet12GameEnd end = new Packet12GameEnd();
                    end.end = true;
                    playerService.getClient().getClient().sendTCP(end);
                }
            } else if (label.getText().equals("0")) {
                clickedCard.setDisable(true);
                return;
            }
            // buy Card
            gamelogic.buyCard(card);
            // set image on discard Deck
            ablage.setImage(new Image(card.getImagePath()));
            // play Money and updates everything
            gamelogic.playMoneyOnBoard(card.getCost());
            playerService.getPlayer().setAction(-playerService.getPlayer().getAction());
            gamelogic.checkIfGameEnds();
            update();
        }
    }

    /**
     * shows the name of the player under his cards and when the prev player end his
     * turn and you move your Mouse the scene changes
     */
    public void setNameGame() {
        for (int i = 0; i < playerService.getPlayers().size(); i++) {
            OtherPlayers other = playerService.getPlayers().get(i);
            Group group = (Group) leftCorner.getChildren().get(i);
            Label playerLabel = (Label) ((VBox) group.getChildren().get(1)).getChildren().get(1);
            group.setVisible(true);
            playerLabel.setText(other.getPlayerName());
        }

        Packet08DiscardDeck card1 = new Packet08DiscardDeck();
        card1.cardName = "/src/main/resources/backgrounds/Blanko.png";
        card1.playerName = playerService.getPlayer().getPlayerName();
        playerService.getClient().getClient().sendTCP(card1);

        Packet09HandCardNumber card2 = new Packet09HandCardNumber();
        card2.NumberOfCardInHand = kartenInHand.getChildren().size();
        card2.playerName = playerService.getPlayer().getPlayerName();
        playerService.getClient().getClient().sendTCP(card2);

        Packet21FirstDeckCard carddeck = new Packet21FirstDeckCard();
		carddeck.cardname = playerService.getPlayer().getDeck().get(0).getName();
        carddeck.playername = playerService.getPlayer().getPlayerName();
        playerService.getClient().getClient().sendTCP(carddeck);
    }

    /////////////////////////// POPUPWindow ///////////////////////

    /** well handel the confirm Btn of the Popupwindow */
    @FXML
    void confirmPopUPHandler(MouseEvent event) {
        effectWindow.setVisible(false);
        if (gamelogic.getkapelleActive()) {
            // sort the list of the deleted cards (avoid exceptions)
            Collections.sort(popUpWindowIndexlist, Collections.reverseOrder());
            for (Integer index : popUpWindowIndexlist) {
                gamelogic.sendCardToTrash(index);
                updateHand();
            }
            popUpWindowIndexlist.clear();
            gamelogic.setkapelleActive(false);
            closeEffectWindow();
            if (gamelogic.getThronsaalTrigger()) {
                gamelogic.setThronsaalTrigger(false);
                effectWindow.setVisible(true);
                // set images in pop up to alle cards in hand
                updateKapelle();
            }
        } else if (gamelogic.getthronsaalActive()) {
            int selectedCard = 999;
            // select a card
            for (int i = 0; i < effectImages.size(); i++) {
                if (effectImages.get(i).getOpacity() == opacity) {
                    selectedCard = i;
                }
            }
            gamelogic.setthronsaalActive(false);
            closeEffectWindow();
            // avoid selecting err (not selecting)
            if (selectedCard == 999) {
            }

            /// player the selected cards twice
            else {

                gamelogic.setThronsaalTrigger(true);
                gamelogic.playClickedCard(gamelogic.getActionCardsFromHand().get(selectedCard));
                update();
            }
        } else if (gamelogic.getMilizActive()) {
            if (playerService.getPlayer().getHand().size() == popUpWindowIndexlist.size() + 3) {
                Collections.sort(popUpWindowIndexlist);
                for (int i = popUpWindowIndexlist.size() - 1; i >= 0 ; i--) {
                    int index = popUpWindowIndexlist.get(i);
                    Cards card = playerService.getPlayer().getHand().get(index);
                    if (card.getCardType() == "Geld") {
                        playerService.getPlayer().setMoney(-card.getPoints());
                    }
                    gamelogic.discardCardfromHand(index);
                }
                playground2.setVisible(true);
                playground1.getChildren().get(1).setVisible(true);
                playground1.setVisible(false);
                GameLogic.setMilizActive(false);
                closeEffectWindow();
                update();
            } else {
                alert("Warnung", "Es wurden zu wenig Karten ausgewählt!");
            }
        } else if (gamelogic.getDiebActive()) {
            gamelogic.setDiebActive(false);
            showAllPopUpCards();
            clearAllPopUPCards();
            effectWindow.setVisible(false);
            gamelogic.setDiebActive2(true);
            update();
            setDisablePopUp();
            popUpWindowIndexlist.clear();
        } else if (gamelogic.getDiebActive2()) {
            addCards();
            trashCards();
            gamelogic.setDiebActive2(false);
            closeEffectWindow();
            setImagesVisible();
            update();
        } else if (gamelogic.getUmbauActive()) {
            int index = popUpWindowIndexlist.getFirst();
            updateUmbau(playerService.getPlayer().getHand().get(index).getCost());
            gamelogic.sendCardToTrash(index);
            gamelogic.setUmbauActive(false);               
            closeEffectWindow();
            popupMessageLabel.setText("Umbau :: Wähle eine neue Karte");
            effectWindow.setVisible(true);
            update();
        } else if (gamelogic.getUmbauActive2()) {
            int choosenCard = popUpWindowIndexlist.getFirst();
            int number = playerService.getPlayer().getCardnumber().get(choosenCard);
            gamelogic.takeCard(copyList.get(choosenCard));
            playerService.getPlayer().getCardnumber().set(choosenCard, number - 1);
            gamelogic.setUmbauActive2(false);
            closeEffectWindow();
            update();

        } else if (gamelogic.getWerkstattActive()) {
            int choosenCard = popUpWindowIndexlist.getFirst();
            int number = playerService.getPlayer().getCardnumber().get(choosenCard);
            gamelogic.takeCard(copyList.get(choosenCard));
            playerService.getPlayer().getCardnumber().set(choosenCard, number - 1);
            gamelogic.setWerkstattActive(false);
            closeEffectWindow();
            update();
        }
    }

    public ArrayList<Cards> copyPoolCardsUmbau(Integer ii) {
        copyList = new ArrayList<>();
        for(int i = 0; i < playerService.getPlayer().getCardspool().size(); i++) {
            if(playerService.getPlayer().getCardspool().get(i).getCost() <= (ii + 2)) {
                copyList.add(playerService.getPlayer().getCardspool().get(i));
            }
        }
        return copyList;
    }

    public void getACardUmbau(int index) {
        playerService.getPlayer().getPlayedCardsList().add(copyList.get(index));
    }

    public void updateWerkstatt() {
        ArrayList<Cards> poolCards = copyPoolCards();
        if(poolCards.size()> 0 && poolCards.get(0).getCost() <= 4) {
            popUpCard1.setImage(new Image(poolCards.get(0).getImagePath()));
        }
        if(poolCards.size() > 1 && poolCards.get(1).getCost() <= 4) {
            popUpCard2.setImage(new Image(poolCards.get(1).getImagePath()));
        }
        if(poolCards.size() > 2 && poolCards.get(2).getCost() <= 4) {
            popUpCard3.setImage(new Image(poolCards.get(2).getImagePath()));
        }
        if(poolCards.size() > 3 && poolCards.get(3).getCost() <= 4) {
            popUpCard4.setImage(new Image(poolCards.get(3).getImagePath()));
        }
        if(poolCards.size() > 4 && poolCards.get(4).getCost() <= 4) {
            popUpCard5.setImage(new Image(poolCards.get(4).getImagePath()));
        }
        if(poolCards.size() > 5 && poolCards.get(5).getCost() <= 4) {
            popUpCard6.setImage(new Image(poolCards.get(5).getImagePath()));
        }
        if(poolCards.size() > 6 && poolCards.get(6).getCost() <= 4) {
            popUpCard7.setImage(new Image(poolCards.get(6).getImagePath()));
        }
    }

    /** clear Btn in the effect window */
    @FXML
    void clearBtnHandler(MouseEvent event) {
        for (ImageView image : effectImages) {
            image.setOpacity(1);
        }
        popUpWindowIndexlist.clear();
        setDisablePopUp();
    }

    /**
     * well close the effect window, reset images in the window and deactive alle
     * effects of the cards
     */
    private void closeEffectWindow() {
        // deactive all effects

        // reset window
        showAllPopUpCards();
        clearAllPopUPCards();
        // close window
        effectWindow.setVisible(false);
        popUpWindowIndexlist.clear();
    }

    private void clearAllPopUPCards() {
        for (ImageView card : effectImages) {
            card.setImage(null);
        }
    }

    private void showAllPopUpCards() {
        for (ImageView card : effectImages) {
            card.setOpacity(1);
        }
    }

    /** well active when any of the card in popup window */
    @FXML
    void popUpCardsHandler(MouseEvent event) {
        HBox hbox = (HBox) ((ImageView) event.getSource()).getParent();
        for (int i = 0; i < hbox.getChildren().size(); i++) {
            if (((ImageView) hbox.getChildren().get(i)) == event.getTarget()) {
                // kapelle
                if (gamelogic.getkapelleActive()) {
                    if (!popUpWindowIndexlist.contains(i)) {
                        popUpWindowIndexlist.add(i);
                    }
                } // Werkstatt
                else if (gamelogic.getWerkstattActive()) {
                    if (popUpWindowIndexlist.size() == 0) {
                        popUpWindowIndexlist.add(i);
                    } else {
                        ((ImageView) hbox.getChildren().get(popUpWindowIndexlist.getFirst())).setOpacity(1);
                        popUpWindowIndexlist.removeFirst();
                        popUpWindowIndexlist.add(i);
                    }
                } // umbau
                else if (gamelogic.getUmbauActive()) {
                    if (popUpWindowIndexlist.size() == 0) {
                        popUpWindowIndexlist.add(i);
                    } else {
                        ((ImageView) hbox.getChildren().get(popUpWindowIndexlist.getFirst())).setOpacity(1);
                        popUpWindowIndexlist.removeFirst();
                        popUpWindowIndexlist.add(i);
                    }
                } else if (gamelogic.getUmbauActive2()) {
                    if (popUpWindowIndexlist.size() == 0) {
                        popUpWindowIndexlist.add(i);
                    } else {
                        ((ImageView) hbox.getChildren().get(popUpWindowIndexlist.getFirst())).setOpacity(1);
                        popUpWindowIndexlist.removeFirst();
                        popUpWindowIndexlist.add(i);
                    }

                } // Thronsaal
                else if (gamelogic.getthronsaalActive()) {
                    showAllPopUpCards();
                } // Miliz
                else if (gamelogic.getMilizActive()) {
                    int j = playerService.getPlayer().getHand().size() - 3;
                    if (popUpWindowIndexlist.size() < j && !popUpWindowIndexlist.contains(i)) {
                        popUpWindowIndexlist.add(i);
                    } else if (popUpWindowIndexlist.size() >= j) {
                        ((ImageView) hbox.getChildren().get(popUpWindowIndexlist.getFirst())).setOpacity(1);
                        popUpWindowIndexlist.removeFirst();
                        popUpWindowIndexlist.add(i);
                    }
                }
                // Dieb
                else if (gamelogic.getDiebActive()) {
                        popUpWindowIndexlist.add(i);
                        if (popUpWindowIndexlist.contains(0)) {
                            popUpCard2.setDisable(true);
                        }
                        if (popUpWindowIndexlist.contains(1)) {
                            popUpCard1.setDisable(true);
                        }
                        if (popUpWindowIndexlist.contains(2)) {
                            popUpCard4.setDisable(true);
                        }
                        if (popUpWindowIndexlist.contains(3)) {
                            popUpCard3.setDisable(true);
                        }
                        if (popUpWindowIndexlist.contains(4)) {
                            popUpCard6.setDisable(true);
                        }
                        if (popUpWindowIndexlist.contains(5)) {
                            popUpCard5.setDisable(true);
                        }
                } else if (gamelogic.getDiebActive2()) {
                    popUpWindowIndexlist.add(i);
                }
                ((ImageView) event.getSource()).setOpacity(opacity);
            }
        }
    }

    /////////////////////////// Cards ///////////////////////

    /** well take bis zu 7 cards of the hand and but it in the popup window */
    public void updateKapelle() {
        int Handsize = playerService.getPlayer().getHand().size();
        if (Handsize > 0) {
            popUpCard1.setImage(gamelogic.createImageFromHand(0));
        }
        if (Handsize > 1) {
            popUpCard2.setImage(gamelogic.createImageFromHand(1));
        }
        if (Handsize > 2) {
            popUpCard3.setImage(gamelogic.createImageFromHand(2));
        }
        if (Handsize > 3) {
            popUpCard4.setImage(gamelogic.createImageFromHand(3));
        }
        if (Handsize > 4) {
            popUpCard5.setImage(gamelogic.createImageFromHand(4));
        }
        if (Handsize > 5) {
            popUpCard6.setImage(gamelogic.createImageFromHand(5));
        }
        if (Handsize > 6) {
            popUpCard7.setImage(gamelogic.createImageFromHand(6));
        }
    }

    /** well take bis zu 7 cards of the hand and but it in the popup window */
    public void updateThronsaal() {

        int ActionCardsCounter = gamelogic.getActionCardsFromHand().size();
        if (ActionCardsCounter > 0) {
            popUpCard1.setImage(gamelogic.createImageFromHand(gamelogic.getActionCardsFromHand().get(0)));
        }
        if (ActionCardsCounter > 1) {
            popUpCard2.setImage(gamelogic.createImageFromHand(gamelogic.getActionCardsFromHand().get(1)));
        }
        if (ActionCardsCounter > 2) {
            popUpCard3.setImage(gamelogic.createImageFromHand(gamelogic.getActionCardsFromHand().get(2)));
        }
        if (ActionCardsCounter > 3) {
            popUpCard4.setImage(gamelogic.createImageFromHand(gamelogic.getActionCardsFromHand().get(3)));
        }
        if (ActionCardsCounter > 4) {
            popUpCard5.setImage(gamelogic.createImageFromHand(gamelogic.getActionCardsFromHand().get(4)));
        }
        if (ActionCardsCounter > 5) {
            popUpCard6.setImage(gamelogic.createImageFromHand(gamelogic.getActionCardsFromHand().get(5)));
        }
        if (ActionCardsCounter > 6) {
            popUpCard7.setImage(gamelogic.createImageFromHand(gamelogic.getActionCardsFromHand().get(6)));
        }
    }

    /** take up to six cards in the popup for dieb  */
    public void updateDieb() {

        int numberofPlayers = playerService.getPlayers().size();
        if (numberofPlayers > 0) {
            popUpCard1.setImage(new Image(playerService.getPlayers().get(0).getFirstDeckCard().getImagePath()));
            popUpCard2.setImage(new Image(playerService.getPlayers().get(0).getSecondDeckCard().getImagePath()));
            if (!playerService.getPlayers().get(0).getFirstDeckCard().getCardType().equals("Geld")){
                popUpCard1.setDisable(true);
            }
            if (!playerService.getPlayers().get(0).getSecondDeckCard().getCardType().equals("Geld")){
                popUpCard2.setDisable(true);
            }
        }
        if (numberofPlayers > 1) {
            popUpCard3.setImage(new Image(playerService.getPlayers().get(1).getFirstDeckCard().getImagePath()));
            popUpCard4.setImage(new Image(playerService.getPlayers().get(1).getSecondDeckCard().getImagePath()));
            if (!playerService.getPlayers().get(1).getFirstDeckCard().getCardType().equals("Geld")){
                popUpCard3.setDisable(true);
            }
            if (!playerService.getPlayers().get(1).getSecondDeckCard().getCardType().equals("Geld")){
                popUpCard4.setDisable(true);
            }
        }
        if (numberofPlayers > 2) {
            popUpCard5.setImage(new Image(playerService.getPlayers().get(2).getFirstDeckCard().getImagePath()));
            popUpCard6.setImage(new Image(playerService.getPlayers().get(2).getSecondDeckCard().getImagePath()));
            if (!playerService.getPlayers().get(2).getFirstDeckCard().getCardType().equals("Geld")){
                popUpCard5.setDisable(true);
            }
            if (!playerService.getPlayers().get(2).getSecondDeckCard().getCardType().equals("Geld")){
                popUpCard6.setDisable(true);
            }
        }
    }

    /** shows the selected cards of dieb */
    public void updateDieb2() {

            if (popUpWindowIndexlist.contains(0)) {
                popUpCard1.setImage(new Image(playerService.getPlayers().get(0).getFirstDeckCard().getImagePath()));
            } else {
                popUpCard1.setVisible(false);
            }
            if (popUpWindowIndexlist.contains(1)) {
                popUpCard2.setImage(new Image(playerService.getPlayers().get(0).getSecondDeckCard().getImagePath()));
            } else {
                popUpCard2.setVisible(false);
            }
            if (popUpWindowIndexlist.contains(2)) {
                popUpCard3.setImage(new Image(playerService.getPlayers().get(1).getFirstDeckCard().getImagePath()));
            } else {
                popUpCard3.setVisible(false);
            }
            if (popUpWindowIndexlist.contains(3)) {
                popUpCard4.setImage(new Image(playerService.getPlayers().get(1).getSecondDeckCard().getImagePath()));
            } else {
                popUpCard4.setVisible(false);
            }
            if (popUpWindowIndexlist.contains(4)) {
                popUpCard5.setImage(new Image(playerService.getPlayers().get(2).getFirstDeckCard().getImagePath()));
            } else {
                popUpCard5.setVisible(false);
            }
            if (popUpWindowIndexlist.contains(5)) {
                popUpCard6.setImage(new Image(playerService.getPlayers().get(2).getSecondDeckCard().getImagePath()));
            } else {
                popUpCard6.setVisible(false);
            }
    }

    /** set all popupcards to not disabled */
    public void setDisablePopUp() {
        popUpCard1.setDisable(false);
        popUpCard2.setDisable(false);
        popUpCard3.setDisable(false);
        popUpCard4.setDisable(false);
        popUpCard5.setDisable(false);
        popUpCard6.setDisable(false);
    }

    /**set all popupcards visible */
    public void setImagesVisible() {
        popUpCard1.setVisible(true);
        popUpCard2.setVisible(true);
        popUpCard3.setVisible(true);
        popUpCard4.setVisible(true);
        popUpCard5.setVisible(true);
        popUpCard6.setVisible(true);
    }

    /** adds the choosen cards of dieb to the discarddeck of the player */
    public void addCards() {
        
        int numberofPlayers = playerService.getPlayers().size();
        if (numberofPlayers > 0) {
            if (popUpWindowIndexlist.contains(0)) {
                 playerService.getPlayer().getDiscardDeck().add(playerService.getPlayers().get(0).getFirstDeckCard());
                 gamelogic.sendFirstCard(0);
            }
            if (popUpWindowIndexlist.contains(1)) {
                playerService.getPlayer().getDiscardDeck().add(playerService.getPlayers().get(0).getSecondDeckCard());
                gamelogic.sendSecondCard(0);
            }
        }
        if (numberofPlayers > 1) {
            if (popUpWindowIndexlist.contains(2)) {
                playerService.getPlayer().getDiscardDeck().add(playerService.getPlayers().get(1).getFirstDeckCard());
                gamelogic.sendFirstCard(1);
            }
            if (popUpWindowIndexlist.contains(3)) {
                playerService.getPlayer().getDiscardDeck().add(playerService.getPlayers().get(1).getSecondDeckCard());
                gamelogic.sendSecondCard(1);
            }
        }
        if (numberofPlayers > 2) {
            if (popUpWindowIndexlist.contains(4)) {
                playerService.getPlayer().getDiscardDeck().add(playerService.getPlayers().get(2).getFirstDeckCard());
                gamelogic.sendFirstCard(2);
            }
            if (popUpWindowIndexlist.contains(5)) {
                playerService.getPlayer().getDiscardDeck().add(playerService.getPlayers().get(2).getSecondDeckCard());
                gamelogic.sendSecondCard(2);
            }
        }
    }

    /** adds the not choosen cards of dieb to the trash */
    public void trashCards() {
        int numberofPlayers = playerService.getPlayers().size();
        if (numberofPlayers > 0) {
            if (!popUpWindowIndexlist.contains(0) && playerService.getPlayers().get(0).getFirstDeckCard().getCardType() == "Geld"
                && popUpCard1.isVisible()) {
                playerService.getPlayer().getTrashCardsList().add(playerService.getPlayers().get(0).getFirstDeckCard());
                gamelogic.sendFirstCard(0);
            }
            if (!popUpWindowIndexlist.contains(1) && playerService.getPlayers().get(0).getSecondDeckCard().getCardType() == "Geld"
                && popUpCard2.isVisible()) {
                playerService.getPlayer().getTrashCardsList().add(playerService.getPlayers().get(0).getSecondDeckCard());
                gamelogic.sendSecondCard(0);
            }
        }
        if (numberofPlayers > 1) {
            if (!popUpWindowIndexlist.contains(2) && playerService.getPlayers().get(1).getFirstDeckCard().getCardType() == "Geld"
                && popUpCard3.isVisible()) {
                playerService.getPlayer().getTrashCardsList().add(playerService.getPlayers().get(1).getFirstDeckCard());
                gamelogic.sendFirstCard(1);
            }
            if (!popUpWindowIndexlist.contains(3) && playerService.getPlayers().get(1).getSecondDeckCard().getCardType() == "Geld"
                && popUpCard4.isVisible()) {
                playerService.getPlayer().getTrashCardsList().add(playerService.getPlayers().get(1).getSecondDeckCard());
                gamelogic.sendSecondCard(1);
            }
        }
        if (numberofPlayers > 2) {
            if (!popUpWindowIndexlist.contains(4) && playerService.getPlayers().get(2).getFirstDeckCard().getCardType() == "Geld"
                && popUpCard5.isVisible()) {
                playerService.getPlayer().getTrashCardsList().add(playerService.getPlayers().get(2).getFirstDeckCard());
                gamelogic.sendFirstCard(2);
            }
            if (!popUpWindowIndexlist.contains(5) && playerService.getPlayers().get(2).getSecondDeckCard().getCardType() == "Geld"
                && popUpCard6.isVisible()) {
                playerService.getPlayer().getTrashCardsList().add(playerService.getPlayers().get(2).getSecondDeckCard());
                gamelogic.sendSecondCard(2);
            }
        }
    }

    /** well take all the action cards from hand and put it in popup window */
    public void thronsaalPlayed() {
        if (gamelogic.getthronsaalActive()) {
            popupMessageLabel.setText("Thronsaal :: wähle eine Aktionkarte");
            updateThronsaal();
            effectWindow.setVisible(true);
        }
    }

    public void kapellePlayed() {
        if (gamelogic.getkapelleActive()) {
            popupMessageLabel.setText("Kapelle :: wähle bis zu vier Karten zum entwerfen");
            effectWindow.setVisible(true);
            // set images in pop up to alle cards in hand
            updateKapelle();
        }
    }

    public void diebPlayed() {
        if (gamelogic.getDiebActive()) {
            popupMessageLabel.setText("Dieb :: wähle Geldkarten aus");
            effectWindow.setVisible(true);
            updateDieb();
        }
    }

    public void diebPlayed2() {
        if (gamelogic.getDiebActive2()) {
            popupMessageLabel.setText("Dieb :: wähle Geldkarten welche du behalten willst");
            effectWindow.setVisible(true);
            updateDieb2();
        }
    }

    public void milizPlayed() {
        if (gamelogic.getMilizActive()) {
            popupMessageLabel.setText("Miliz :: wähle Karten aus bis du nur noch 3 hast");
            effectWindow.setVisible(true);
            playground2.setVisible(true);
            playground1.setVisible(true);
            playground1.getChildren().get(1).setVisible(false);
            updateKapelle();
        }
    }

    public void umbauPlayed() {
        if (gamelogic.getUmbauActive()) {
            popupMessageLabel.setText("Umbau :: wähle eine Karten zum entwerfen");
            effectWindow.setVisible(true);
            // set images in pop up to alle cards in hand
            updateKapelle();
        }
    }

    public void ratsversammlungPlayed() {
        if (gamelogic.checkIfSpec("Ratsversammlung") == true) {
            updateHand();
        }
    }

    public void werkstattPlayed(){
        if(gamelogic.getWerkstattActive()) {
            popupMessageLabel.setText("Werkstatt :: wähle eine Karte aus");
            effectWindow.setVisible(true);
            updateWerkstatt();
        }
    }

    public ArrayList<Cards> copyPoolCards() {
        copyList = new ArrayList<>();
        for(int i = 0; i < playerService.getPlayer().getCardspool().size(); i++) {
            if(playerService.getPlayer().getCardspool().get(i).getCost() <= 4) {
                copyList.add(playerService.getPlayer().getCardspool().get(i));
            }
        }
        return copyList;
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

    /** log */
    public void addTextToLogList(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                logMessages.add(message);
            }
        });
    }
}