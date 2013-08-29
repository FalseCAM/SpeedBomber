package speedbomber;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import speedbomber.controller.DesktopInputController;
import speedbomber.controller.InputController;
import speedbomber.model.LevelAppState;
import speedbomber.model.network.GameClient;

/**
 *
 * @author FalseCAM
 */
public class ClientMain extends SimpleApplication {

    public static void main(String[] args) {
        ClientMain app = new ClientMain();
        Game.init(app);
        app.start();
    }
    InputController inputController;
    private String host;
    private int port = 14589;
    LevelAppState level;

    @Override
    public void simpleInitApp() {
        this.setPauseOnLostFocus(false);
        inputManager.setCursorVisible(true);
        // Disable the default flyby cam
        flyCam.setEnabled(false);
        viewPort.setBackgroundColor(ColorRGBA.Blue);
        inputController = new DesktopInputController();
        inputController.initInput(inputManager);
        initNetwork();
    }

    private void initNetwork() {
        GameClient.init(host, port);
    }

    public Boolean restartGame(Integer userId) {
        System.out.println("Client Game Restart");
        if (level != null) {
            level.setEnabled(false);
            stateManager.detach(level);
            level.cleanup();
        }
        level = new LevelAppState(userId);
        stateManager.attach(level);
        return true;
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (level != null) {
            level.update(tpf);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    public InputController getInputController() {
        return inputController;
    }

    void setHost(String host) {
        this.host = host;
    }

    void setPort(int port) {
        this.port = port;
    }

    @Override
    public void destroy() {
        super.destroy();
        System.exit(0);
    }
}
