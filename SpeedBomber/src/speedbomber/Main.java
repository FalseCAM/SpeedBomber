package speedbomber;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import speedbomber.controller.DesktopInputController;
import speedbomber.controller.InputController;
import speedbomber.controller.PlayerController;
import speedbomber.model.Level;
import speedbomber.model.User;

/**
 *
 * @author falsecam
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        Game.init(app);
        app.start();
    }
    InputController inputController;
    PlayerController playerController;
    BulletAppState bulletAppState;
    Level level;
    User user;

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
    }

    private void initPhysics() {
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);
        bulletAppState.getPhysicsSpace().setWorldMax(new Vector3f(1000f, 10f, 1000f));
        bulletAppState.getPhysicsSpace().setWorldMin(new Vector3f(0f, -10f, 0f));
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
}
