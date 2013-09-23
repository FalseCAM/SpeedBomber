/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.view;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.serializing.Serializer;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import speedbomber.Game;
import speedbomber.SpeedBomber;
import speedbomber.model.network.CommandMessage;
import speedbomber.model.network.GameClient;
import speedbomber.model.network.LobbyMessage;

/**
 *
 * @author FalseCAM
 */
public class ConnectGameMenu extends AbstractAppState implements ScreenController, MessageListener<Client> {

    private SpeedBomber app;
    private Nifty nifty;
    private Screen screen;
    private Label nameLabel;
    private ListBox playersListBox;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SpeedBomber) app;
    }

    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        Serializer.registerClass(LobbyMessage.class);
        Serializer.registerClass(CommandMessage.class);
        this.nameLabel = screen.findNiftyControl("nameLabel", Label.class);
        this.nameLabel.setText(Game.instance().getName());
        this.playersListBox = screen.findNiftyControl("playersListBox", ListBox.class);
    }

    public void onStartScreen() {
        GameClient.getClient().addMessageListener(this, LobbyMessage.class);
        Message message = new CommandMessage(CommandMessage.MessageType.HELLO, Game.instance().getPlayerName());
        GameClient.getClient().send(message);
    }

    public void onEndScreen() {
    }

    public void ready() {
        Message message = new LobbyMessage(LobbyMessage.MessageType.READY);
        GameClient.getClient().send(message);
    }

    public void back() {
        nifty.gotoScreen("lan_menu");
    }

    @Override
    public void update(float tpf) {
    }

    public void messageReceived(Client source, Message m) {
        if (m instanceof LobbyMessage) {
            LobbyMessage lm = (LobbyMessage) m;
            if (lm.getType().equals(LobbyMessage.MessageType.UPDATE)) {
                playersListBox.clear();
                playersListBox.addAllItems(lm.getPlayers());
            } else if (lm.getType().equals(LobbyMessage.MessageType.START)) {
                nifty.gotoScreen(null);
            }
        }
    }
}
