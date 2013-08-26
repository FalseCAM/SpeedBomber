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
public class Haunter extends Node {

    Spatial spatial;

    public Haunter() {
        create();
    }

    private void create() {
        spatial = Game.instance().getAssetManager().loadModel("Models/Haunter.j3o");
        spatial.rotate(0f, .5f, 0f);
        this.attachChild(spatial);
    }
}
