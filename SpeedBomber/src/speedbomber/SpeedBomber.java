package speedbomber;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.network.Client;
import com.jme3.network.serializing.Serializer;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import de.lessvoid.nifty.Nifty;
import speedbomber.controller.DesktopInputController;
import speedbomber.controller.GameEvent;
import speedbomber.controller.InputController;
import speedbomber.model.LevelAppState;
import speedbomber.model.network.CommandMessage;
import speedbomber.model.network.GameClient;
import speedbomber.model.network.GameMessage;
import speedbomber.model.network.GameServer;
import speedbomber.model.network.LobbyMessage;
import speedbomber.model.network.ServerMessage;
import speedbomber.view.ConnectGameMenu;
import speedbomber.view.CreateGameMenu;
import speedbomber.view.LanMenu;
import speedbomber.view.StartMenu;

/**
 *
 * @author FalseCAM
 */
public class SpeedBomber extends SimpleApplication {
    
    InputController inputController;
    LevelAppState level;
    // Menu
    StartMenu startMenu;
    LanMenu lanMenu;
    CreateGameMenu createGameMenu;
    ConnectGameMenu connectGameMenu;
    boolean hosting = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        SpeedBomber speedBomber = new SpeedBomber();
        speedBomber.start();
    }
    
    private void loadNiftyXML(Nifty nifty){
        nifty.addXml("Interface/client_gui.xml");
    }
    
    private void registerScreenController(Nifty nifty){
        nifty.registerScreenController(lanMenu);
        nifty.registerScreenController(createGameMenu);
        nifty.registerScreenController(connectGameMenu);
        nifty.registerScreenController(startMenu);
    }
    
    private void registerSeializer(){
        Serializer.registerClass(CommandMessage.class);
        Serializer.registerClass(GameMessage.class);
        Serializer.registerClass(GameEvent.class);
        Serializer.registerClass(ServerMessage.class);
        Serializer.registerClass(LobbyMessage.class);
    }
    
    @Override
    public void simpleInitApp() {

        registerSeializer();
        // Menu
        startMenu = new StartMenu();
        lanMenu = new LanMenu();
        createGameMenu = new CreateGameMenu();
        connectGameMenu = new ConnectGameMenu();
        stateManager.attach(startMenu);
        stateManager.attach(lanMenu);
        stateManager.attach(createGameMenu);
        stateManager.attach(connectGameMenu);
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
        
        registerScreenController(nifty);
        loadNiftyXML(nifty);
        nifty.gotoScreen("start");
        
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
    
    @Override
    public void destroy() {
        if (hosting) {
            GameServer.stop();
        }
        super.destroy();
        System.exit(0);
    }
    
    public void hostGame(int port) {
        this.hosting = true;
        if (hosting) {
            GameServer.init(port);
        }
        
    }
    
    public void connectGame(Client client) {
        GameClient.instance().init(client);
    }
}
