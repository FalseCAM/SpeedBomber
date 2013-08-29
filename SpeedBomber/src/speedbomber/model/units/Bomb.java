/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.units;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import speedbomber.Game;
import speedbomber.model.GameObjectGroup;

/**
 *
 * @author FalseCAM
 */
public class Bomb extends PlayerObject {

    BoxCollisionShape collisionShape;

    public Bomb() {
        node = new Node("Bomb");
        group = GameObjectGroup.WEAPON;

        createPhysic();
        create();
    }

    private void create() {
        Spatial spatial = Game.getAssetManager().loadModel("Models/Bomb.j3o");
        node.attachChild(spatial);
        node.addControl(physics);
    }

    @Override
    public void update(float tpf) {
    }

    private void createPhysic() {
        collisionShape = new BoxCollisionShape(new Vector3f(1, 1, 1));
        physics = new RigidBodyControl(collisionShape, 50f);
        physics.setCollisionGroup(group.getPhysicsGroup());
        physics.setCollideWithGroups(GameObjectGroup.MAP.getPhysicsGroup());
    }
}
