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

    }

    private void create() {
        int scale = 15;
        for (int i = 0; i < abstractMap.getWidth(); i++) {
            for (int j = 0; j < abstractMap.getHeight(); j++) {
                MapType o = abstractMap.get(i, j);

                Node n = MapObjectFactory.create(o);
                n.scale(scale);
                if (o.equals(MapType.SPAWNPOINT)) {
                    this.spawnPoint = (SpawnPoint) n;
                }
                n.setLocalTranslation(scale * 2 * i, -4 * scale, scale * 2 * j);
                attachChild(n);  // make the cube appear in the scene
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
