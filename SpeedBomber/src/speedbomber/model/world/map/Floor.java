/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.world.map;

import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import speedbomber.Game;

/**
 *
 * @author FalseCAM
 */
public class Floor extends MapObject {

    @Override
    void create() {
        Box b = new Box(Vector3f.ZERO, 1f, 1f, 1f);
        Material mat = new Material(Game.instance().getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        Geometry geom = new Geometry("Floor", b);
        geom.setShadowMode(RenderQueue.ShadowMode.Receive);
        TextureKey tKey = new TextureKey("Textures/Map/Floor.png");
        tKey.setGenerateMips(true);
        Texture tex = Game.instance().getAssetManager().loadTexture(tKey);
        tex.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("ColorMap", tex);
        mat.setColor("Color", ColorRGBA.Orange);
        geom.setMaterial(mat);
        attachChild(geom);

    }
}
