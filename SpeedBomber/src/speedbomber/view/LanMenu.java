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
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import speedbomber.Game;
import speedbomber.SpeedBomber;
import speedbomber.model.network.CommandMessage;
import speedbomber.model.network.GameMessage;
import speedbomber.model.network.GameServer;
import speedbomber.model.network.ServerMessage;

/**
 *
 * @author FalseCAM
 */
public class LanMenu extends AbstractAppState implements ScreenController, MessageListener<Client> {

    private SpeedBomber app;
    private Nifty nifty;
    private Screen screen;
    private ListBox gamesListBox;
    private TextField nameTextField;
    List<Client> clients = new LinkedList<Client>();
    List<ServerMessage> messages = new LinkedList<ServerMessage>();

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SpeedBomber) app;

    }

    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        Serializer.registerClass(ServerMessage.class);
        gamesListBox = screen.findNiftyControl("gamesListBox", ListBox.class);
        this.nameTextField = screen.findNiftyControl("nameTextField", TextField.class);
        this.nameTextField.setText(System.getProperty("user.name"));
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }

    public void create() {
        Game.instance().setPlayerName(nameTextField.getRealText());
        nifty.gotoScreen("create_game");
    }

    public void connect() {
        int i = gamesListBox.getFocusItemIndex();
        if (i != -1) {
            Game.instance().setName(messages.get(i).getName());
            Game.instance().setMap(messages.get(i).getMap());
            Game.instance().setPlayerName(nameTextField.getRealText());
            app.connectGame(clients.get(i));
            nifty.gotoScreen("connect_game");
        }
    }

    public void find() {
        for (Client client : clients) {
            client.close();
        }
        gamesListBox.clear();
        clients.clear();
        messages.clear();

        String network = "192.168.0.";
        String localHost = "127.0.0.1";
        try {
            localHost = InetAddress.getLocalHost().getHostAddress();
            String[] na = localHost.split("\\.");
            network = na[0] + "." + na[1] + "." + na[2] + ".";
        } catch (UnknownHostException ex) {
            Logger.getLogger(LanMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        // iterate all ips in lan
        for (int i = 1; i < 255; i++) {
            final String n = network + i;

            new Thread(new Runnable() {
                public void run() {
                    try {
                        findServer(n);
                    } catch (java.net.ConnectException ex) {
                    } catch (IOException ex) {
                        Logger.getLogger(LanMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        }
    }

    private void findServer(String ip) throws IOException {
        com.jme3.network.Client client = Network.connectToServer(GameServer.NAME, GameServer.VERSION, ip, GameServer.STDPORT, GameServer.STDPORT);
        client.addMessageListener(this, ServerMessage.class);
        client.start();
    }

    public void back() {
        nifty.gotoScreen("start");

    }

    @Override
    public void update(float tpf) {
    }

    public void messageReceived(Client source, Message m) {
        if (m instanceof ServerMessage) {
            messages.add((ServerMessage) m);
            clients.add(source);
            ServerMessage gm = (ServerMessage) m;
            StringBuilder sb = new StringBuilder();
            sb.append(gm.getName());
            sb.append(": ");
            sb.append(gm.getMap());
            gamesListBox.addItem(sb.toString());
        }
    }
}
