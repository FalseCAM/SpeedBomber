/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;

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
}
