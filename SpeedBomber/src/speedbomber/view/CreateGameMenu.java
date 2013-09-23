/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.view;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.network.Network;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import speedbomber.Game;
import speedbomber.SpeedBomber;
import speedbomber.model.network.GameServer;
import speedbomber.model.network.ServerMessage;

/**
 *
 * @author FalseCAM
 */
public class CreateGameMenu extends AbstractAppState implements ScreenController {

    private SpeedBomber app;
    private Nifty nifty;
    private Screen screen;
    private TextField nameTextField;
    private TextField mapTextField;
    private TextField portTextField;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SpeedBomber) app;
    }

    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        this.nameTextField = screen.findNiftyControl("nameTextField", TextField.class);
        this.nameTextField.setText(System.getProperty("user.name") + "'s game");
        this.mapTextField = screen.findNiftyControl("mapTextField", TextField.class);
        this.mapTextField.setText("Maps/Map.map.xml");
        this.portTextField = screen.findNiftyControl("portTextField", TextField.class);
        this.portTextField.setText("" + GameServer.STDPORT);
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }

    public void create() {
        Game.instance().setName(this.nameTextField.getRealText());
        Game.instance().setMap(this.mapTextField.getRealText());

        app.hostGame(GameServer.STDPORT);
        com.jme3.network.Client client;
        try {
            client = Network.connectToServer(GameServer.NAME, GameServer.VERSION, "localhost", GameServer.STDPORT, GameServer.STDPORT);
            client.start();
            app.connectGame(client);

            nifty.gotoScreen("connect_game");
        } catch (IOException ex) {
            Logger.getLogger(CreateGameMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void back() {
        nifty.gotoScreen("lan_menu");
    }

    @Override
    public void update(float tpf) {
    }
}
