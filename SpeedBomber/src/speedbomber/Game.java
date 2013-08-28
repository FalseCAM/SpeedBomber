/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import speedbomber.model.GameObject;
import speedbomber.model.player.Player;

/**
 *
 * @author FalseCAM
 */
public class Game {

    static Game singleton;
    ClientMain simpleApplication;
    List<Player> players = new LinkedList<Player>();

    private Game() {
    }

    static void init(ClientMain simpleApplication) {
        Game game = new Game();
        singleton = game;
        game.simpleApplication = simpleApplication;
    }

    public static Game instance() {
        return singleton;
    }

    public static ClientMain getMain() {
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

    public static PhysicsSpace getPhysicsSpace() {
        return singleton.simpleApplication.getBulletAppState().getPhysicsSpace();
    }

    public static void attach(GameObject gameObject) {
        getRoodNode().attachChild(gameObject.getNode());
        if (gameObject.getPhysics() != null) {
            getPhysicsSpace().add(gameObject.getPhysics());
        }

    }

    public static void detach(GameObject gameObject) {
        getRoodNode().detachChild(gameObject.getNode());
        if (gameObject.getPhysics() != null) {
            getPhysicsSpace().remove(gameObject.getPhysics());
        }
    }

    public static List<Player> getPlayers() {
        return singleton.players;
    }

    public void restart(final Integer userId) {
        this.simpleApplication.enqueue(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return simpleApplication.restartGame(userId);
            }
        });
    }
}
