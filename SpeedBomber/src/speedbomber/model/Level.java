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
import java.util.HashMap;
import java.util.List;
import speedbomber.Game;
import speedbomber.model.player.Player;
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
    HashMap<Player, Haunter> haunters = new HashMap<Player, Haunter>();
    float lastBomb;
    float lastGrenade;
    private PhysicsSpace physicsSpace;

    public Level() {
        initPlayer();
        init();
        initHaunter();
    }

    private void init() {
        lastBomb = 0;
        abstractMap = AbstractMap.loadMapFile("Maps/SmallMap.map");
        map = new Map(abstractMap);

    }

    public void initRootNode(Node rootNode) {
        this.rootNode.attachChild(map.getNode());

        rootNode.attachChild(this.rootNode);
    }

    public void simpleUpdate(float tpf) {
        lastBomb += tpf;
        lastGrenade += tpf;
        for (Haunter haunter : haunters.values()) {
            haunter.simpleUpdate(tpf);
        }
    }

    public void initPhysics(PhysicsSpace physicsSpace) {
        this.physicsSpace = physicsSpace;
        physicsSpace.addAll(map.getNode());
        for (Haunter haunter : haunters.values()) {
            Game.getPhysicsSpace().add(haunter.getCharacterControl());
        }

    }

    public void placeBomb(Haunter haunter) {
        if (lastBomb > 2) {
            Bomb bomb = new Bomb();
            bomb.getPhysics().setPhysicsLocation(haunter.getNode().getWorldTranslation());
            Game.attach(bomb);
            lastBomb = 0;
        }

    }

    private void initPlayer() {
        Game.getPlayers().clear();
        for (int i = 0; i < 8; i++) {
            Game.getPlayers().add(Player.getPlayer(i));
        }
    }

    private void initHaunter() {
        for (int i = 0; i < Game.getPlayers().size(); i++) {
            Vector3f charLocation = new Vector3f(map.getSpawnPoint(i).getLocalTranslation().getX(), 0, map.getSpawnPoint(i).getLocalTranslation().getZ());
            Haunter haunter = new Haunter(charLocation);
            haunters.put(Game.getPlayers().get(i), haunter);
            Game.attach(haunter);
        }
    }

    public Haunter getHaunter(Player player) {
        return haunters.get(player);
    }

    public void throwGrenade(Haunter haunter, Vector3f target) {
        if (lastGrenade > 0.5f) {
            Grenade grenade = new Grenade(haunter, target);
            Game.attach(grenade);
            lastGrenade = 0;
        }
    }
}
