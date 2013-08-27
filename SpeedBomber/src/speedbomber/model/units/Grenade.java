/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.units;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import speedbomber.Game;
import speedbomber.model.GameObjectGroup;
import speedbomber.model.effects.Explosion;

/**
 *
 * @author FalseCAM
 */
public class Grenade extends PlayerObject {

    public Grenade(Haunter haunter, Geometry target) {
        node = new Node("Grenade");
        Vector3f translation = new Vector3f(haunter.getNode().getWorldTranslation());
        createPhysic();
        create();
        physics.setPhysicsLocation(translation.add(0, 5f, 0));
        Vector3f dir = target.getWorldTranslation().subtract(translation.add(0, 5f, 0));
        dir.setY(0);
        physics.setLinearVelocity(dir.normalize().mult(25f));
        physics.setGravity(new Vector3f(0f, -9.81f, 0f));
        physics.setFriction(.9f);
        physics.setRestitution(0.1f);


        //Explosion explosion = new Explosion(node.getLocalTranslation());


    }

    private void create() {
        Sphere sphere = new Sphere(8, 8, 1f, true, false);
        sphere.setTextureMode(TextureMode.Projected);
        Geometry geometry = new Geometry("Grenade", sphere);

        Material mat = new Material(Game.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Black);
        geometry.setMaterial(mat);
        node.attachChild(geometry);
        physics = new RigidBodyControl(50f);
        node.addControl(physics);
    }
    
    private void createPhysic() {
        physics = new RigidBodyControl(50f);
        physics.setCollisionGroup(group.getPhysicsGroup());
        physics.setCollideWithGroups(GameObjectGroup.MAP.getPhysicsGroup());
    }
    

    @Override
    public void simpleUpdate(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
