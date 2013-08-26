/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

/**
 *
 * @author Dev
 */
public class Game {

    static Game singleton;
    SimpleApplication simpleApplication;

    private Game() {
    }

    static void init(SimpleApplication simpleApplication) {
        Game game = new Game();
        singleton = game;
        game.simpleApplication = simpleApplication;
    }

    public static Game instance() {
        return singleton;
    }

    public AssetManager getAssetManager() {
        return this.simpleApplication.getAssetManager();
    }

    public InputManager getInputManager() {
        return this.simpleApplication.getInputManager();
    }

    public Node getRoodNode() {
        return this.simpleApplication.getRootNode();
    }

    public Camera getCam() {
        return this.simpleApplication.getCamera();
    }
}
