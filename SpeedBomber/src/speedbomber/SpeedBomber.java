package speedbomber;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import de.lessvoid.nifty.Nifty;
import speedbomber.controller.DesktopInputController;
import speedbomber.controller.InputController;
import speedbomber.model.LevelAppState;
import speedbomber.model.network.GameClient;
import speedbomber.model.network.GameServer;
import speedbomber.view.StartMenu;

/**
 *
 * @author FalseCAM
 */
public class SpeedBomber extends SimpleApplication {

    InputController inputController;
    private String host;
    private int port = 14589;
    private String playerName = "NoName";
    LevelAppState level;
    // Menu
    StartMenu startMenu;
    boolean hosting = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        SpeedBomber speedBomber = new SpeedBomber();
        speedBomber.start();
    }

    @Override
    public void simpleInitApp() {

        // Menu
        startMenu = new StartMenu();
        stateManager.attach(startMenu);
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
        nifty.fromXml("Interface/start_menu.xml", "start", startMenu);



        Game.init(this);
        this.setPauseOnLostFocus(false);
        inputManager.setCursorVisible(true);
        // Disable the default flyby cam
        flyCam.setEnabled(false);
        viewPort.setBackgroundColor(ColorRGBA.Blue);
        inputController = new DesktopInputController();
        inputController.initInput(inputManager);

        BitmapText text = new BitmapText(guiFont, false);
        text.setSize(guiFont.getCharSet().getRenderedSize());
        text.setText("Press 'R' to start or restart Game");
        text.setLocalTranslation(100, text.getHeight() + 20, 0);
        guiNode.attachChild(text);
    }

    private void initNetwork() {
    }

    public Boolean restartGame(Integer nrPlayer) {
        //setDisplayFps(true);       // to hide the FPS
        //setDisplayStatView(false);  // to hide the statistics 
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
        if (hosting) {
            GameServer.update(tpf);
        }
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

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPlayerName(String name) {
        this.playerName = name;
    }

    @Override
    public void destroy() {
        if (hosting) {
            GameServer.stop();
        }
        super.destroy();
        System.exit(0);
    }

    public void hostGame() {
        this.hosting = true;
        if (hosting) {
            GameServer.init(port);
        }
        stateManager.detach(startMenu);

    }

    public void connectGame() {
        GameClient.init(host, port, playerName);
        stateManager.detach(startMenu);
    }
}
