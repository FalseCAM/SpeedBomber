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
import speedbomber.model.GameWorld;

/**
 *
 * @author FalseCAM
 */
public class Bomb extends PlayerObject {

    private float lifeTime = 0;
    private final static float maxLifeTime = 10;
    private final static float explosionRadius = 10;
    BoxCollisionShape collisionShape;

    public Bomb(GameWorld world) {
        this.world = world;
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
        lifeTime += tpf;
        if (lifeTime > maxLifeTime) {
            explode();
        }
    }

    private void explode() {
        super.alive = false;
        for (PlayerObject playerObject : world.getPlayerObjects(this, explosionRadius)) {
            if (playerObject != this) {
                playerObject.doDamage();
            }
        }
    }

    @Override
    public void doDamage() {
        if (lifeTime < maxLifeTime - 0.5f) {
            lifeTime = maxLifeTime - 0.5f;
        }
    }

    private void createPhysic() {
        collisionShape = new BoxCollisionShape(new Vector3f(1, 1, 1));
        physics = new RigidBodyControl(collisionShape, 2f);
        physics.setCollisionGroup(group.getPhysicsGroup());
        physics.setCollideWithGroups(GameObjectGroup.MAP.getPhysicsGroup());
    }
}
