/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.world.map;

import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.texture.Texture;
import speedbomber.Game;
import speedbomber.model.player.Player;

/**
 *
 * @author FalseCAM
 */
public class SpawnPoint extends MapObject {

    @Override
    void create() {
        Material mat = new Material(Game.instance().getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        Geometry geom = new Geometry("Free", Floor.createMesh());
        TextureKey tKey = new TextureKey("Textures/Map/Floor.png");
        tKey.setGenerateMips(true);
        Texture tex = Game.instance().getAssetManager().loadTexture(tKey);
        tex.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("ColorMap", tex);
        mat.setColor("Color", ColorRGBA.Yellow);
        mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        geom.setMaterial(mat);
        attachChild(geom);
    }
}
