package speedbomber;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
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

    InputController inputController;
    private String host;
    private int port = 14589;
    private String playerName = "NoName";
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

        BitmapText text = new BitmapText(guiFont, false);
        text.setSize(guiFont.getCharSet().getRenderedSize());
        text.setText("Press 'R' to start or restart Game");
        text.setLocalTranslation(100, text.getHeight() + 20, 0);
        guiNode.attachChild(text);
    }

    private void initNetwork() {
        GameClient.init(host, port, playerName);
    }

    public Boolean restartGame(Integer nrPlayer) {
        guiNode.detachAllChildren();
        System.out.println("Client Game Restart");
        if (level != null) {
            level.setEnabled(false);
            stateManager.detach(level);
            level.cleanup();
        }
        level = new LevelAppState(nrPlayer);
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

    void setPlayerName(String name) {
        this.playerName = name;
    }

    @Override
    public void destroy() {
        super.destroy();
        System.exit(0);
    }
}
