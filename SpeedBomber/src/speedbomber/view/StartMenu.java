/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.view;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import speedbomber.SpeedBomber;

/**
 *
 * @author FalseCAM
 */
public class StartMenu extends AbstractAppState implements ScreenController {
    
    private SpeedBomber app;
    private Nifty nifty;
    private Screen screen;
    private TextField nameTextField;
    private TextField hostTextField;
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
        this.nameTextField.setText(System.getProperty("user.name"));
        this.hostTextField = screen.findNiftyControl("hostTextField", TextField.class);
        this.hostTextField.setText("192.168.0.2");
        this.portTextField = screen.findNiftyControl("portTextField", TextField.class);
        this.portTextField.setText("15386");
    }
    
    public void onStartScreen() {
    }
    
    public void onEndScreen() {
    }
    
    public void quitGame() {
        app.stop();
    }
    
    public void hostGame() {
        app.setPort(Integer.parseInt(portTextField.getRealText()));
        app.hostGame();
        app.setHost("localhost");
        app.setPlayerName(nameTextField.getRealText());
        app.connectGame();
        nifty.gotoScreen(null);
    }
    
    public void connectGame() {
        app.setHost(hostTextField.getRealText());
        app.setPlayerName(nameTextField.getRealText());
        app.connectGame();
        nifty.gotoScreen(null);
        
    }
    
    @Override
    public void update(float tpf) {
    }
}
