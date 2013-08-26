/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.units;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import speedbomber.Game;

/**
 *
 * @author FalseCAM
 */
public class Bomb extends Node {

    public Bomb() {
        create();
    }

    private void create() {
        Spatial spatial = Game.instance().getAssetManager().loadModel("Models/Bomb.j3o");
        this.attachChild(spatial);
    }
}
