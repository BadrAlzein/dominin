package de.btu.swp.dominion.game;

import de.btu.swp.dominion.gameLogic.PlayerService;
import de.btu.swp.dominion.network.Packets.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChatCon extends GameCon implements Initializable {

    private PlayerService playerservice = getPlayerService2();
    private static ObservableList<String> chatmessages = FXCollections.observableArrayList();
    
    @FXML
    private ListView<String> lobbyChatList = new ListView<String>(chatmessages);
    @FXML
    private TextField lobbyTextField;
    @FXML
    private Button lobbySendBtn;
    @FXML
    private ImageView extBtn;

    /**
     * updates the listview if there is a new message
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lobbyChatList.setItems(chatmessages);
        lobbyChatList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> lb, String old_val, String new_val) {
            }
        });
    }

    /**
     * gives the action to the send btn if enter is pressed
     */
    @FXML
    public void EventHandler(KeyEvent e) throws Exception {
        if (e.getCode() == KeyCode.ENTER) {
            Packet01Message MessagePacket = new Packet01Message();
            MessagePacket.message = lobbyTextField.getText();
            MessagePacket.clientname = playerService.getPlayer().getPlayerName();
            MessagePacket.stage = 2;
            playerService.getClient().getClient().sendTCP(MessagePacket);
            lobbyTextField.setText("");
        }
    }

    /**
     * gives the action to the buttons if they are clicked
     */
    @FXML
    public void ActionHandler(MouseEvent event) throws Exception {
        if (event.getTarget() == lobbySendBtn) {
            if (!lobbyTextField.getText().equals("")) {
                Packet01Message messagePacket = new Packet01Message();
                messagePacket.message = lobbyTextField.getText();
                messagePacket.clientname = playerService.getPlayer().getPlayerName();
                messagePacket.stage = 2;
                playerService.getClient().getClient().sendTCP(messagePacket);
                lobbyTextField.setText("");
            }
        } 
        if (event.getTarget() == extBtn) {
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // close the current Stage
            window.close();
        }
    }

    /**
     * adds the message to the listview
     */
    public void addTextToList(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                chatmessages.add(message);
            }
        });
    }
}