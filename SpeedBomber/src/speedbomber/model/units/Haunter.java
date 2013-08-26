/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.units;

import com.jme3.ai.navmesh.NavMesh;
import com.jme3.ai.navmesh.NavMeshPathfinder;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import speedbomber.Game;

/**
 *
 * @author FalseCAM
 */
public class Haunter extends Node {

    Spatial spatial;
    CharacterControl character;
    private Geometry target = null;

    public Haunter() {
        create();
    }

    private void create() {
        spatial = Game.instance().getAssetManager().loadModel("Models/Haunter.j3o");
        Material mat = new Material(Game.instance().getAssetManager(), "Materials/Normale.j3md");
        spatial.setMaterial(mat);
        CapsuleCollisionShape capsule = new CapsuleCollisionShape(3f, 4f);
        character = new CharacterControl(capsule, 0.05f);
        character.setJumpSpeed(20f);
        this.attachChild(spatial);
        this.addControl(character);

    }

    public CharacterControl getCharacterControl() {
        return character;
    }

    public void simpleUpdate(float tpf) {
        //character.jump();
        if (target != null && this.getWorldTranslation().distance(target.getWorldTranslation()) > 20) {
            Vector3f dir = this.target.getWorldTranslation().subtract(this.getWorldTranslation());
            character.setWalkDirection(dir.normalize());
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
