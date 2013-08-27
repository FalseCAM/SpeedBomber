/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.world.map;

import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Node;

/**
 *
 * @author FalseCAM
 */
public class Map extends Node {

    private final AbstractMap abstractMap;
    private RigidBodyControl physics;
    private CompoundCollisionShape collision;
    private SpawnPoint spawnPoint;

    public Map(AbstractMap abstractMap) {
        this.abstractMap = abstractMap;

        create();
        collision = (CompoundCollisionShape) CollisionShapeFactory.createMeshShape(this);
        physics = new RigidBodyControl(collision, 0.0f);
        this.addControl(physics);
        physics.setFriction(1f);
        physics.setRestitution(0.0f);

    }

    private void create() {
        int scale = 5;
        for (int i = 0; i < abstractMap.getWidth(); i++) {
            for (int j = 0; j < abstractMap.getHeight(); j++) {
                MapType mapType = abstractMap.get(i, j);

                Node node = MapObjectFactory.create(mapType);
                node.scale(scale);
                if (mapType.equals(MapType.SPAWNPOINT)) {
                    this.spawnPoint = (SpawnPoint) node;
                }
                node.setLocalTranslation(scale * 2 * i, -scale, scale * 2 * j);
                attachChild(node);  // make the cube appear in the scene
            }
        }
        this.updateModelBound();
    }

    public SpawnPoint getSpawnPoint() {
        return spawnPoint;
    }

    public RigidBodyControl getPhysics() {
        return physics;
    }
}
