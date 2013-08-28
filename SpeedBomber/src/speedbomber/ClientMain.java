package speedbomber;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import speedbomber.controller.DesktopInputController;
import speedbomber.controller.GameController;
import speedbomber.controller.InputController;
import speedbomber.model.Level;
import speedbomber.model.User;
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
    BulletAppState bulletAppState;
    Level level;
    User user;
    private String host;
    private int port = 14589;
    
    @Override
    public void simpleInitApp() {
        user = new User();
        
        viewPort.setBackgroundColor(ColorRGBA.Blue);

        //level = new Level();
        //level.initRootNode(this.getRootNode());


        //user.setPlayer(Game.getPlayers().get(0));
        //initController();
        //initInput();
        //initPhysics();
        //initLight();
        //initCamera();
        initNetwork();
        
    }
    
    public Boolean restartGame(Integer userId) {
        Game.getPlayers().clear();
        System.out.println("Client Game Restart");
        // clear part
        this.rootNode.detachAllChildren();
        viewPort.detachScene(rootNode);
        this.rootNode = new Node();
        viewPort.attachScene(rootNode);
        // init part
        user = new User();
        level = new Level();
        level.initRootNode(this.getRootNode());
        GameController.instance().setLevel(level);
        user.setPlayer(Game.getPlayers().get(userId));
        
        initInput();
        initPhysics();
        initLight();
        initCamera();
        
        
        return true;
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        if (level != null) {
            level.simpleUpdate(tpf);
        }
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
        if (bulletAppState != null) {
            // TODO look into 
            stateManager.detach(bulletAppState);
            bulletAppState.stopPhysics();
            bulletAppState.cleanup();
        }
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
        inputManager.reset();
        inputManager.setCursorVisible(true);
        inputController = new DesktopInputController();
        inputController.initInput();
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
