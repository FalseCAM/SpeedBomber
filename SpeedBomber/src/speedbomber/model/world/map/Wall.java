/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.world.map;

import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import speedbomber.Game;

/**
 *
 * @author FalseCAM
 */
public class Wall extends MapObject {

    @Override
    void create() {
        createGround();
        createMidWall();
    }

    private void createGround() {
        Box b = new Box(Vector3f.ZERO, 1, 1, 1);
        Material mat = new Material(Game.getAssetManager(),
                "Common/MatDefs/Light/Lighting.j3md");
        TextureKey tKey = new TextureKey("Textures/Map/Wall.png");
        Texture tex = Game.getAssetManager().loadTexture(tKey);
        tex.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("DiffuseMap", tex);
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.Orange);
        mat.setColor("Diffuse", ColorRGBA.Orange);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 12);
        //mat.setColor("Color", ColorRGBA.Orange);
        Geometry geom = new Geometry("Wall_ground", b);
        geom.setMaterial(mat);
        geom.setLocalTranslation(0, 0f, 0);
        attachChild(geom);

    }

    private void createMidWall() {
        Box b = new Box(Vector3f.ZERO, 1, 0.5f, 1);
        Material mat = new Material(Game.getAssetManager(),
                "Common/MatDefs/Light/Lighting.j3md");
        TextureKey tKey = new TextureKey("Textures/Map/Wall.png");
        Texture tex = Game.instance().getAssetManager().loadTexture(tKey);
        tex.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("DiffuseMap", tex);
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.Orange);
        mat.setColor("Diffuse", ColorRGBA.Orange);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 12);
        //mat.setColor("Color", ColorRGBA.Orange);
        Geometry geom = new Geometry("Wall_mid", b);
        geom.setMaterial(mat);
        geom.setLocalTranslation(0, 1.5f, 0);
        attachChild(geom);
    }
}
