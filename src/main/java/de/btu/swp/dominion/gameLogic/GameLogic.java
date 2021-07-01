package de.btu.swp.dominion.gameLogic;

import de.btu.swp.dominion.gameLogic.PlayerService;
import de.btu.swp.dominion.network.Packets;
import de.btu.swp.dominion.network.Packets.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import de.btu.swp.dominion.cards.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;

public class GameLogic {
    private PlayerService playerService;
    private LinkedList<Cards> newhand = new LinkedList<>();
    /** well Triger if a card is played */
    private boolean kapelleActive = false;
    private boolean thronsaalActive = false;
    private static boolean milizActive = false;
    private static boolean diebActive = false;
    private static boolean diebActive2 = false;
    /** well stay as much as thronsaal is active */
    private boolean thronsaalTrigger = false;
    private boolean umbauActive = false;
    private boolean umbauActive2 = false;
    private boolean werkstattActive = false;

    @FXML
    private HBox kartenInHand;
    Scanner scanner = new Scanner(System.in);

    public GameLogic(PlayerService newplayerservice) {
        this.playerService = newplayerservice;

    }

    /**
     * plays money cards from hand and play them TODO: change so we can get the
     * money for all cards and to accept silver and gold as well as money
     */
    public void playMoneyOnBoard(int price) {
        int anzahlKupfer = 0;
        if (playerService.getPlayer().getPlayedCardsList().size() > 0) {
            for (int a = playerService.getPlayer().getPlayedCardsList().size() - 1; a >= 0; a--) {
                if (playerService.getPlayer().getCardNamePlayed(a) == "Kupfer") {
                    anzahlKupfer++;
                }
            }
        }

        for (int k = playerService.getPlayer().getHand().size() - 1; k >= 0; k--) {
            if (anzahlKupfer >= price) {
                break;
            } else {
                if (playerService.getPlayer().getCardName(k) == "Kupfer") {
                    playerService.getPlayer().getPlayedCardsList().add(playerService.getPlayer().getHand().get(k));
                    anzahlKupfer++;
                    playerService.getPlayer().getHand().remove(k);
                    newhand = playerService.getPlayer().getHand();
                    playerService.getPlayer().setHand(newhand);
                } else if (playerService.getPlayer().getCardName(k) == "Silber") {
                    playerService.getPlayer().getPlayedCardsList().add(playerService.getPlayer().getHand().get(k));
                    anzahlKupfer = anzahlKupfer + 2;
                    playerService.getPlayer().getHand().remove(k);
                    newhand = playerService.getPlayer().getHand();
                    playerService.getPlayer().setHand(newhand);
                } else if (playerService.getPlayer().getCardName(k) == "Gold") {
                    playerService.getPlayer().getPlayedCardsList().add(playerService.getPlayer().getHand().get(k));
                    anzahlKupfer = anzahlKupfer + 3;
                    playerService.getPlayer().getHand().remove(k);
                    newhand = playerService.getPlayer().getHand();
                    playerService.getPlayer().setHand(newhand);
                }
            }
        }
    }

    public void launchEffect(int index) {
        Cards card = playerService.getPlayer().getPlayedCardsList().getLast();
        Packet10RenderSpecator zug = new Packets.Packet10RenderSpecator();
        zug.cardName = card.getName();
        playerService.getClient().getClient().sendTCP(zug);
        // führt die Effekte aus
        for (int i = 0; i < card.getEffect().size(); i++) {
            if (card.getEffect().get(i) == "addCard") {
                playerService.drawCard();
            } else if (card.getEffect().get(i) == "addAction") {
                playerService.getPlayer().setAction(1);
            } else if (card.getEffect().get(i) == "addBuy") {
                playerService.getPlayer().setBuys(1);
            } else if (card.getEffect().get(i) == "addMoney") {
                playerService.getPlayer().setMoney(1);
            } else if (card.getEffect().get(i) == "addKanzlerEffekt") {
                if (playerService.getPlayer().getDeck().size() > 0) {
                    ButtonType effect = new ButtonType("auf Ablagestapel");
                    ButtonType nothing = new ButtonType("auf Nachziehstapel lassen");
                    Alert alert = new Alert(AlertType.CONFIRMATION,
                            "Wollen Sie ihren Nachziehstapel auf den Ablagestapel legen?", effect, nothing);
                    alert.setTitle("Kanzlereffekt");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == effect) {
                        playerService.getPlayer().getDiscardDeck().addAll(playerService.getPlayer().getDeck());
                        playerService.getPlayer().setRightList(new LinkedList<Cards>());
                    }
                }
            } else if (card.getEffect().get(i) == "addKapelleEffect") {
                setkapelleActive(true);
            } else if (card.getEffect().get(i) == "addThronsaalEffect") {
                setthronsaalActive(true);
            } else if (card.getEffect().get(i) == "addAdventure") {
                int numberOfMoneyCards = 0;
                while (numberOfMoneyCards < 2) {
                    if (playerService.getPlayer().getDeck().size() == 0) {
                        playerService.getPlayer().setRightList(playerService.getPlayer().getDiscardDeck());
                        playerService.getPlayer().setDiscardDeck(new LinkedList<Cards>());
                        if (playerService.getPlayer().getDeck().size() == 0) {
                            return;
                        }
                    } else {
                        if (playerService.getPlayer().getDeck().get(0).getCardType() == "Geld") {
                            numberOfMoneyCards++;
                            playerService.drawCard();
                        } else {
                            playerService.getPlayer().getDiscardDeck().add(playerService.getPlayer().getDeck().get(0));
                            playerService.getPlayer().getDeck().remove(0);
                        }
                    }
                }
            } else if (card.getEffect().get(i) == "addCardLibrary") {

                while (playerService.getPlayer().getHand().size() < 7) {
                    if (playerService.getPlayer().getDeck().size() > 0) {
                        if (playerService.getPlayer().getDeck().get(0).getCardType() == "Aktion") {
                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setContentText("Wollen Sie die Karte " + playerService.getPlayer().getDeck().get(0).getName() + " ablegen?");
                            alert.setTitle(playerService.getPlayer().getDeck().get(0).getName());
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                discardCardfromDeck();
                            } else {
                                playerService.drawCard();
                            }
                        } else {
                            playerService.drawCard();
                        }
                    } else {
                        playerService.drawCard();
                    }
                }
            } else if (card.getEffect().get(i) == "addMilizEffekt") {
                Packet11MilizEffekt effect = new Packet11MilizEffekt();
                effect.playPlayer = playerService.getPlayer().getPlayerName();
                playerService.getClient().getClient().sendTCP(effect);
            } else if (card.getEffect().get(i) == "addHexeEffect") {
                // draw two cards
                playerService.drawCard();
                playerService.drawCard();
                // send flucht to the clients
                Packet10HexeEffect hexeCard = new Packet10HexeEffect();
                hexeCard.ownerName = playerService.getPlayer().getPlayerName();
                playerService.getClient().getClient().sendTCP(hexeCard);
            } else if (card.getEffect().get(i) == "addRatsEffekt") {
                Packet17RatsEffect effect = new Packet17RatsEffect();
                effect.name = playerService.getPlayer().getPlayerName();
                playerService.getClient().getClient().sendTCP(effect);
            } else if (card.getEffect().get(i) == "addDiebEffekt") {
                Packet23DiebEffekt effekt = new Packet23DiebEffekt();
                effekt.playername = playerService.getPlayer().getPlayerName();
                playerService.getClient().getClient().sendTCP(effekt);
                setDiebActive(true);
            } else if (card.getEffect().get(i) == "addWerkstattEffect") {
                setWerkstattActive(true);
            } else if (card.getEffect().get(i) == "addUmbauEffect") {
                setUmbauActive(true);
                setUmbauActive2(true);
            }

        }
        playerService.getPlayer().setAction(-1);
    }

    /**
     * well take a string and publish it to all player in the log list used a
     * trigger to decied the kind of the log message
     */
    public void sendToLog(String CardName, String trigger) {
        Packet15log log = new Packets.Packet15log();
        String playerName = "[" + playerService.getPlayer().getPlayerName() + "]:";

        if (trigger.equals("playCard")) {
            log.logMessage = playerName + " spielte " + CardName + ".";
        } else if (trigger.equals("endTurn")) {
            log.logMessage = playerName + " beendete seinen Zug.";
        } else if (trigger.equals("buyCard")) {
            log.logMessage = playerName + " kaufte " + CardName + ".";
        }
        playerService.getClient().getClient().sendTCP(log);
    }

    public void playClickedCard(int index) {
        sendToLog(playerService.getPlayer().getHand().get(index).getName(), "playCard");
        playerService.getPlayer().getPlayedCardsList().add(playerService.getPlayer().getHand().get(index));
        playerService.getPlayer().getHand().remove(index);
        /** start every effect + Network */
        launchEffect(index);
        if (getThronsaalTrigger()) {
            launchEffect(index);
        }
    }

    /** throw the card at index from the hand to the discardDeck */
    public void discardCardfromHand(int index) {
        playerService.getPlayer().getDiscardDeck().add(playerService.getPlayer().getHand().get(index));
        playerService.getPlayer().getHand().remove(index);
    }

    /** throw the first card of the deck to the discardDeck */
    public void discardCardfromDeck() {
        playerService.getPlayer().getDiscardDeck().add(playerService.getPlayer().getDeck().get(0));
        playerService.getPlayer().getDeck().remove(0);
    }

    /** maybe unite money and points cards to two methods */
    public void buyCard(Cards card) {
        Packet14Card2 boughtCard = new Packet14Card2();
        boughtCard.name = card.getName();
        playerService.getClient().getClient().sendTCP(boughtCard);

        sendToLog(card.getName(), "buyCard");
        playerService.getPlayer().getDiscardDeck().add(card);
        playerService.getPlayer().setMoney(-card.getCost());
        playerService.getPlayer().setBuys(-1);
        if (card.getCardType() == "Punkte") {
            playerService.getPlayer().setPoints(card.getPoints());
        }
    }

    public void takeCard(Cards card) {
        Packet14Card2 boughtCard = new Packet14Card2();
        boughtCard.name = card.getName();
        playerService.getClient().getClient().sendTCP(boughtCard);

        sendToLog(card.getName(), "buyCard");
        playerService.getPlayer().getDiscardDeck().add(card);
        if (card.getCardType() == "Punkte") {
            playerService.getPlayer().setPoints(card.getPoints());
        }
    }

    /** draws Cards and shows them in Hand */
    public void initHand() {
        Collections.shuffle(playerService.getPlayer().getDeck());
        // draw 5 Cards
        playerService.drawCard();
        playerService.drawCard();
        playerService.drawCard();
        playerService.drawCard();
        playerService.drawCard();
    }

    /** well send a card with an index to trash */
    public void sendCardToTrash(int index) {
        /** send to the glbal Trash */
        Packet20GlobalTrash globalTrashCard = new Packet20GlobalTrash();
        globalTrashCard.cardName = playerService.getPlayer().getHand().get(index).getName();
        playerService.getClient().getClient().sendTCP(globalTrashCard);
        /** local Trash */
        if (playerService.getPlayer().getHand().get(index).getCardType() == "Geld") {
            playerService.getPlayer().setMoney(-playerService.getPlayer().getHand().get(index).getPoints());
        }

        playerService.getPlayer().getHand().remove(index); // in the feuture finde alternative ways (glitch expected)
    }

    /** get the Index of all aktion cards from the hand */
    public ArrayList<Integer> getActionCardsFromHand() {
        ArrayList<Integer> actionCardsList = new ArrayList<Integer>();
        actionCardsList.clear();

        for (int i = 0; i < playerService.getPlayer().getHand().size(); i++) {
            if (playerService.getPlayer().getHand().get(i).getCardType().equals("Aktion")) {
                actionCardsList.add(i);
            }
        }
        return actionCardsList;
    }

    /** check if a card is played */
    public Boolean checkIfPlayed(String cardName) {
        for (int i = 0; i < playerService.getPlayer().getPlayedCardsList().size(); i++) {
            if (playerService.getPlayer().getPlayedCardsList().get(i).getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }

    /** check if a card is in spec window */
    public Boolean checkIfSpec(String cardName) {
        for (int i = 0; i < playerService.getPlayer().getSpecCards().size(); i++) {
            if (playerService.getPlayer().getSpecCards().get(i).getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }

    /** sends the first card of the players deck, so it can be removed */
    public void sendFirstCard(int i) {
        Packet26DeleteFirstDeck deletcard = new Packet26DeleteFirstDeck();
        deletcard.cardname = playerService.getPlayers().get(i).getFirstDeckCard().getName();
        deletcard.playername = playerService.getPlayers().get(i).getPlayerName();
        playerService.getClient().getClient().sendTCP(deletcard);
    }

    /** sends the second card of the players deck, so it can be removed */
    public void sendSecondCard(int i) {
        Packet27DeleteSecondDeck deletcard = new Packet27DeleteSecondDeck();
        deletcard.cardname = playerService.getPlayers().get(i).getSecondDeckCard().getName();
        deletcard.playername = playerService.getPlayers().get(i).getPlayerName();
        playerService.getClient().getClient().sendTCP(deletcard);
    }

    /**
     * well create an image from an index in the handlist
     * 
     * @param Index
     * @return
     */
    public Image createImageFromHand(int Index) {
        return new Image(playerService.getPlayer().getHand().get(Index).getImagePath());
    }

    /**
     * if the card Effect is active
     * 
     * @return
     */
    public Boolean getkapelleActive() {
        return this.kapelleActive;
    }

    /**
     * if the card Effect is avtive
     */
    public Boolean getUmbauActive(){
        return this.umbauActive;
    }

     /**
     * if the card Effect is avtive
     */
    public Boolean getUmbauActive2(){
        return this.umbauActive2;
    }

    public Boolean getWerkstattActive() {
        return this.werkstattActive;
    }

    /**
     * if the card Effect is active
     * 
     * @return
     */
    public Boolean getthronsaalActive() {
        return this.thronsaalActive;
    }

    /**
     * if the card Effect is active
     * 
     * @return
     */
    public void setthronsaalActive(Boolean thronsaalActive) {
        this.thronsaalActive = thronsaalActive;
    }

    /**
     * if the card Effect is active
     * 
     * @return
     */
    public void setkapelleActive(Boolean kapelleActive) {
        this.kapelleActive = kapelleActive;
    }

    public void setThronsaalTrigger(Boolean thronsaalTrigger) {
        this.thronsaalTrigger = thronsaalTrigger;
    }

    public static void setMilizActive(Boolean newMilizActive) {
        milizActive = newMilizActive;
    }

    public Boolean getMilizActive() {
        return milizActive;
    }

    public static void setDiebActive(Boolean newDiebActive) {
        diebActive = newDiebActive;
    }

    public Boolean getDiebActive() {
        return diebActive;
    }

    public static void setDiebActive2(Boolean newDiebActive2) {
        diebActive2 = newDiebActive2;
    }

    public Boolean getDiebActive2() {
        return diebActive2;
        
    public void setWerkstattActive(Boolean werkstattActive) {
        this.werkstattActive = werkstattActive;
    }


    public void setUmbauActive(Boolean umbauActive) {
        this.umbauActive = umbauActive;
    }

    public void setUmbauActive2(Boolean umbauActive2) {
        this.umbauActive2 = umbauActive2;
    }

    public Boolean getThronsaalTrigger() {
        return this.thronsaalTrigger;
    }

    public void sendpoints() {
        int counter1 = playerService.getPlayer().getDeck().size();
        int counter2 = playerService.getPlayer().getHand().size();
        int counter3 = playerService.getPlayer().getDiscardDeck().size();
        int counter = (counter1 +  counter2 + counter3)/10;
        Packet13Points points = new Packet13Points();

        if(checkIfGardenInDeck()) {
            points.points = playerService.getPlayer().getPoints() + (getNumberOfGardens()*counter);
            points.name = playerService.getPlayer().getPlayerName();
        } else {
            points.points = playerService.getPlayer().getPoints();
            points.name = playerService.getPlayer().getPlayerName();
        }
        playerService.getClient().getClient().sendTCP(points);
    }

    public int getNumberOfGardens() {
        int sum = 0;
        for(int i = 0; i < playerService.getPlayer().getDeck().size(); i++) {
            if(playerService.getPlayer().getDeck().get(i).getName() == "Gaerten") {
                sum += 1;
            }
        }
        for(int i = 0 ; i< playerService.getPlayer().getHand().size(); i++) {
            if(playerService.getPlayer().getHand().get(i).getName() == "Gaerten") {
                sum +=1;
            }
        }
        return sum;
    }

    public boolean checkIfGardenInDeck() {
        for (int i = 0; i < playerService.getPlayer().getDeck().size(); i++) {
            if (playerService.getPlayer().getDeck().get(i).getName() == "Gaerten") {
                return true;
            }
        }
        for (int i = 0; i < playerService.getPlayer().getHand().size(); i++) {
            if (playerService.getPlayer().getHand().get(i).getName() == "Gaerten") {
                return true;
            }
        }
        for (int i = 0; i < playerService.getPlayer().getDiscardDeck().size(); i++) {
            if (playerService.getPlayer().getDiscardDeck().get(i).getName() == "Gaerten") {
                return true;
            }
        }
        return false;
    }


    public static void changeNumb(String cardname, PlayerService playerService) {
        int number;
        for (int i = 0; i < playerService.getPlayer().getCardspool().size(); i++) {
            if (playerService.getPlayer().getCardspool().get(i).getName().equals(cardname)) {
                number = playerService.getPlayer().getCardnumber().get(i);
                if (number > 0) {
                    number--;
                    playerService.getPlayer().getCardnumber().set(i, number);
                }
            }
        }
    }

    public void checkIfGameEnds() {
        int j = 0;
        for (int i = 0; i < playerService.getPlayer().getCardnumber().size(); i++) {
            if (playerService.getPlayer().getCardnumber().get(i) == 0) {
                j++;
            }
        }

        if (j == 3) {
            Packet12GameEnd end = new Packet12GameEnd();
            end.end = true;
            playerService.getClient().getClient().sendTCP(end);
        }
    }
}
