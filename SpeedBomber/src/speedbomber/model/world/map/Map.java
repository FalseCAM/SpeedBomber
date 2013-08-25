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

    public Map(AbstractMap abstractMap) {
        this.abstractMap = abstractMap;

        create();
        collision = (CompoundCollisionShape) CollisionShapeFactory.createMeshShape(this);
        physics = new RigidBodyControl(collision, 0.0f);
        physics.setFriction(1f);
        this.addControl(physics);

    }

    private void create() {
        for (int i = 0; i < abstractMap.getWidth(); i++) {
            for (int j = 0; j < abstractMap.getHeight(); j++) {
                MapType o = abstractMap.get(i, j);
                Node n = MapObjectFactory.create(o);
                n.setLocalTranslation(2 * i, -8, 2 * j);
                attachChild(n);  // make the cube appear in the scene
            }
        }
        this.updateModelBound();
    }

    public RigidBodyControl getPhysics() {
        return physics;
    }
    
    

}
