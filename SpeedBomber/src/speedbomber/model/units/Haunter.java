/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.units;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import speedbomber.Game;
import speedbomber.model.GameObjectGroup;

/**
 *
 * @author FalseCAM
 */
public class Haunter extends PlayerObject {

    Spatial spatial;
    BetterCharacterControl character;
    private Geometry target = null;

    public Haunter(Vector3f startPoint) {
        node = new Node("Haunter");
        group = GameObjectGroup.HAUNTER;
        create();
        //node.attachChild(getHead());
        //node.attachChild(getBody());
        node.setLocalTranslation(startPoint);
        character = new BetterCharacterControl(3f, 6f, 10f);
        node.addControl(character);

    }

    private void create() {
        spatial = Game.getAssetManager().loadModel("Models/Haunter.j3o");
        Material mat = new Material(Game.getAssetManager(), "Materials/Normale.j3md");
        spatial.setMaterial(mat);
        spatial.setLocalTranslation(new Vector3f(0, 1, 0));
        node.attachChild(spatial);
    }

    private Geometry getHead() {
        Mesh mesh = new Sphere(16, 16, 1f);
        Geometry geom = new Geometry("Head", mesh);
        Material mat = new Material(Game.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        geom.setLocalTranslation(new Vector3f(0, 2, 0));
        return geom;
    }

    private Geometry getBody() {
        Mesh mesh = new Sphere(16, 16, 3f);
        Geometry geom = new Geometry("Body", mesh);
        Material mat = new Material(Game.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        geom.setLocalTranslation(new Vector3f(0, 0.5f, 0));
        return geom;
    }

    public BetterCharacterControl getCharacterControl() {
        return character;
    }

    public void simpleUpdate(float tpf) {
        if (target != null && node.getWorldTranslation().distance(target.getWorldTranslation()) > 5) {
            Vector3f dir = this.target.getWorldTranslation().subtract(node.getWorldTranslation());
            character.setWalkDirection(dir.normalize().mult(50));
            character.setViewDirection(new Vector3f(dir.normalize().x, 0, dir.normalize().z));
        } else {
            this.target = null;
            character.setWalkDirection(Vector3f.ZERO);
        }
    }

    public void move(Geometry target) {
        this.target = target;
    }
}
