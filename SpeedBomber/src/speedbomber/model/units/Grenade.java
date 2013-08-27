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

/**
 *
 * @author FalseCAM
 */
public class Grenade extends Node {

    RigidBodyControl physics;

    public Grenade(Haunter haunter, Geometry target) {
        Vector3f translation = new Vector3f(haunter.getWorldTranslation());
        this.setLocalTranslation(translation.add(0, 5f, 0));
        create();
        Vector3f dir = target.getWorldTranslation().subtract(translation.add(0, 5f, 0));
        dir.setY(0);
        physics.setLinearVelocity(dir.normalize().mult(25f));
    }

    private void create() {
        Sphere sphere = new Sphere(4, 4, 1f, true, false);
        sphere.setTextureMode(TextureMode.Projected);
        Geometry geometry = new Geometry("Grenade", sphere);

        Material mat = new Material(Game.instance().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Black);
        geometry.setMaterial(mat);
        this.attachChild(geometry);
        physics = new RigidBodyControl(1f);
        /**
         * Add physical ball to physics space.
         */
        this.addControl(physics);
    }

    public RigidBodyControl getPhysics() {
        return physics;
    }
}
