package speedbomber;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import com.jme3.renderer.RenderManager;
import java.io.IOException;
import java.util.logging.Logger;
import speedbomber.controller.DesktopInputController;
import speedbomber.controller.GameController;
import speedbomber.controller.InputController;
import speedbomber.controller.PlayerController;
import speedbomber.model.Level;
import speedbomber.model.User;
import speedbomber.model.network.ClientListener;
import speedbomber.model.network.GameClient;
import speedbomber.model.network.CommandMessage;

/**
 *
 * @author falsecam
 */
public class ClientMain extends SimpleApplication {

    public static void main(String[] args) {
        ClientMain app = new ClientMain();
        Game.init(app);
        app.start();
    }
    InputController inputController;
    PlayerController playerController;
    BulletAppState bulletAppState;
    Level level;
    User user;
    private String host;
    private int port = 14589;

    @Override
    public void simpleInitApp() {
        user = new User();

        viewPort.setBackgroundColor(ColorRGBA.Blue);

        level = new Level();
        level.initRootNode(this.getRootNode());


        user.setPlayer(Game.getPlayers().get(0));
        initController();
        initInput();
        initPhysics();
        initLight();
        initCamera();
        initNetwork();

    }

    @Override
    public void simpleUpdate(float tpf) {
        level.simpleUpdate(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void initLight() {
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        rootNode.addLight(sun);

        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(0.4f));
        rootNode.addLight(al);
    }

    private void initPhysics() {
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, -9.81f, 0));
        // DEBUG
        //bulletAppState.setDebugEnabled(true);
        level.initPhysics(bulletAppState.getPhysicsSpace());
    }

    private void initCamera() {
        // Disable the default flyby cam
        flyCam.setEnabled(false);
        // Enable a chase cam for this target (typically the player).
        ChaseCamera chaseCam = new ChaseCamera(cam, level.getHaunter(user.getPlayer()).getNode(), inputManager);
        chaseCam.setDefaultDistance(85f);
        chaseCam.setMaxDistance(90f);
        chaseCam.setSmoothMotion(true);
    }

    private void initInput() {
        inputManager.setCursorVisible(true);
        inputController = new DesktopInputController();
        inputController.setPlayerController(playerController);
        inputController.initInput();
    }

    private void initController() {
        playerController = new PlayerController(user, level, cam);
    }

    public BulletAppState getBulletAppState() {
        return bulletAppState;
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

    private void initNetwork() {
        GameClient.init(host, port);
        GameController.instance().setLevel(level);

    }
}
