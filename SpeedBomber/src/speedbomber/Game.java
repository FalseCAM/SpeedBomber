/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import java.util.concurrent.Callable;

/**
 *
 * @author FalseCAM
 */
public class Game {

    public final static float scale = 1;
    static Game singleton;
    SpeedBomber simpleApplication;

    private Game() {
    }

    static void init(SpeedBomber simpleApplication) {
        Game game = new Game();
        singleton = game;
        game.simpleApplication = simpleApplication;
    }

    public static Game instance() {
        return singleton;
    }

    public static SpeedBomber getMain() {
        return singleton.simpleApplication;
    }

    public static AssetManager getAssetManager() {
        return singleton.simpleApplication.getAssetManager();
    }

    public static InputManager getInputManager() {
        return singleton.simpleApplication.getInputManager();
    }

    public static Node getRoodNode() {
        return singleton.simpleApplication.getRootNode();
    }

    public static Camera getCam() {
        return singleton.simpleApplication.getCamera();
    }

    public void restart(final Integer nrPlayer) {
        this.simpleApplication.enqueue(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return simpleApplication.restartGame(nrPlayer);
            }
        });
        
 
    }
}
