/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import speedbomber.model.units.Bomb;
import speedbomber.model.units.Grenade;
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
    Haunter haunter;
    float lastBomb;
    float lastGrenade;
    private PhysicsSpace physicsSpace;

    public Level() {
        init();
    }

    private void init() {
        lastBomb = 0;
        abstractMap = AbstractMap.loadMapFile("Maps/Map.map");
        map = new Map(abstractMap);
        Vector3f charLocation = new Vector3f(map.getSpawnPoint().getLocalTranslation().getX(), 0, map.getSpawnPoint().getLocalTranslation().getZ());


        haunter = new Haunter(charLocation);


    }

    public void initRootNode(Node rootNode) {
        this.rootNode.attachChild(map);
        this.rootNode.attachChild(haunter);
        rootNode.attachChild(this.rootNode);
    }

    public void simpleUpdate(float tpf) {
        lastBomb += tpf;
        lastGrenade += tpf;
        haunter.simpleUpdate(tpf);
    }

    public void initPhysics(PhysicsSpace physicsSpace) {
        this.physicsSpace = physicsSpace;
        physicsSpace.addAll(map);
        physicsSpace.add(haunter.getCharacterControl());
    }

    public Haunter getHaunter() {
        return haunter;
    }

    public void placeBomb(Geometry target) {
        if (lastBomb > 2) {
            Bomb bomb = new Bomb();
            bomb.setLocalTranslation(haunter.getWorldTranslation());
            this.rootNode.attachChild(bomb);
            lastBomb = 0;
        }

    }

    public void throwGrenade(Haunter haunter, Geometry target) {
        if (lastGrenade > 0.1f) {
            Grenade grenade = new Grenade(haunter, target);
            //grenade.setLocalTranslation(target.getWorldTranslation());
            this.rootNode.attachChild(grenade);
            physicsSpace.add(grenade.getPhysics());
            lastGrenade = 0;
        }
    }
}
