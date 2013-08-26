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

    @Override
    public void simpleInitApp() {
        viewPort.setBackgroundColor(ColorRGBA.Blue);

        level = new Level();
        level.initRootNode(this.getRootNode());

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
        bulletAppState.setDebugEnabled(true);
        level.initPhysics(bulletAppState.getPhysicsSpace());
    }

    private void initCamera() {
        // Disable the default flyby cam
        flyCam.setEnabled(false);
        // Enable a chase cam for this target (typically the player).
        ChaseCamera chaseCam = new ChaseCamera(cam, level.getHaunter(), inputManager);
        chaseCam.setDefaultDistance(85f);
        chaseCam.setSmoothMotion(true);
    }

    private void initInput() {
        inputManager.setCursorVisible(true);
        inputController = new DesktopInputController();
        inputController.setPlayerController(playerController);
        inputController.initInput();
    }

    private void initController() {
        playerController = new PlayerController(level, cam);
    }
}
