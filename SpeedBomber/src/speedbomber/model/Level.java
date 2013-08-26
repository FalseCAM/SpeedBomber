/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import speedbomber.model.units.Bomb;
import speedbomber.model.units.Haunter;
import speedbomber.model.world.map.AbstractMap;
import speedbomber.model.world.map.Map;

/**
 *
 * @author FalseCAM
 */
public class Level {

    Node rootNode = new Node();
    AbstractMap abstractMap;
    Map map;
    Bomb bomb;
    Haunter haunter;

    public Level() {
        init();
    }

    private void init() {
        abstractMap = AbstractMap.loadMapFile("Maps/Map.map");
        map = new Map(abstractMap);
        bomb = new Bomb();
        haunter = new Haunter();


    }

    public void initRootNode(Node rootNode) {
        Vector3f charLocation = new Vector3f(map.getSpawnPoint().getLocalTranslation().getX(), 0, map.getSpawnPoint().getLocalTranslation().getZ());
        haunter.getCharacterControl().setPhysicsLocation(charLocation);
        this.rootNode.attachChild(map);
        this.rootNode.attachChild(bomb);
        this.rootNode.attachChild(haunter);
        rootNode.attachChild(this.rootNode);
    }

    public void simpleUpdate(float tpf) {
        haunter.simpleUpdate(tpf);
    }

    public void initPhysics(PhysicsSpace physicsSpace) {
        physicsSpace.addAll(map);
        physicsSpace.add(haunter.getCharacterControl());
    }

    public Haunter getHaunter() {
        return haunter;
    }
}
