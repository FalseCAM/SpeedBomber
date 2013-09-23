/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.view;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
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

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SpeedBomber) app;
    }

    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }

    public void quitGame() {
        app.stop();
    }

    public void lanMenu() {
        nifty.gotoScreen("lan_menu");
    }

    @Override
    public void update(float tpf) {
    }
}
