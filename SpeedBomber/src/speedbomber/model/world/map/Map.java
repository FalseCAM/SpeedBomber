/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.world.map;

import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Node;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import speedbomber.Game;
import speedbomber.model.GameObject;
import speedbomber.model.GameObjectGroup;

/**
 *
 * @author FalseCAM
 */
public class Map extends GameObject {

    private final AbstractMap abstractMap;
    //private RigidBodyControl physics;
    private CollisionShape collision;
    private List<SpawnPoint> spawnPoints = new LinkedList<SpawnPoint>();

    public Map(AbstractMap abstractMap) {
        this.abstractMap = abstractMap;

        node = new Node("Map");
        group = GameObjectGroup.MAP;

        create();
        collision = CollisionShapeFactory.createMeshShape(node);
        physics = new RigidBodyControl(collision, 0.0f);
        node.addControl(physics);
        physics.setCollisionGroup(group.getPhysicsGroup());
        physics.setFriction(1f);
        physics.setRestitution(0.0f);

    }

    private void create() {
        int scale = 5;
        for (int i = 0; i < abstractMap.getWidth(); i++) {
            for (int j = 0; j < abstractMap.getHeight(); j++) {
                MapType mapType = abstractMap.get(i, j);

                Node element = MapObjectFactory.create(mapType);
                element.scale(scale);
                if (mapType.equals(MapType.SPAWNPOINT)) {
                    SpawnPoint spawnPoint = (SpawnPoint) element;
                    spawnPoints.add(spawnPoint);
                }
                element.setLocalTranslation(scale * 2 * i, -scale, scale * 2 * j);
                node.attachChild(element);
            }
        }
        node.updateModelBound();
    }

    public SpawnPoint getSpawnPoint(int nr) {
        return spawnPoints.get(nr);
    }

    @Override
    public void simpleUpdate(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
