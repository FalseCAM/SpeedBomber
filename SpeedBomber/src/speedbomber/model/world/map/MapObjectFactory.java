/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.world.map;

import com.jme3.scene.Node;

/**
 *
 * @author FalseCAM
 */
public class MapObjectFactory {

    public static Node create(MapType object) {
        switch (object) {
            case FLOOR:
                return new Floor();
            case SPAWNPOINT:
                return new SpawnPoint();
            case WALL:
                return new Wall();
            default:
                return new Floor();
        }
    }
}
