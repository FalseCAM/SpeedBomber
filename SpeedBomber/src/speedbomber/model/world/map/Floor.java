/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.world.map;

import com.jme3.asset.TextureKey;
import com.jme3.bullet.collision.shapes.PlaneCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Plane;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.util.BufferUtils;
import speedbomber.Game;

/**
 *
 * @author FalseCAM
 */
public class Floor extends MapObject {

    @Override
    void create() {
        Material mat = new Material(Game.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        Geometry geom = new Geometry("Floor", createMesh());
        geom.setShadowMode(RenderQueue.ShadowMode.Receive);
        TextureKey tKey = new TextureKey("Textures/Map/Floor.png");
        tKey.setGenerateMips(true);
        Texture tex = Game.instance().getAssetManager().loadTexture(tKey);
        tex.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("ColorMap", tex);
        mat.setColor("Color", ColorRGBA.Orange);
        mat.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
        geom.setMaterial(mat);
        attachChild(geom);
    }

    public static Mesh createMesh() {
        Mesh mesh = new Mesh();
        Vector3f[] vertices = new Vector3f[4];
        vertices[0] = new Vector3f(-1, 0, -1);
        vertices[1] = new Vector3f(1, 0, -1);
        vertices[2] = new Vector3f(-1, 0, 1);
        vertices[3] = new Vector3f(1, 0, 1);

        Vector2f[] texCoord = new Vector2f[4];
        texCoord[0] = new Vector2f(0, 0);
        texCoord[1] = new Vector2f(1, 0);
        texCoord[2] = new Vector2f(0, 1);
        texCoord[3] = new Vector2f(1, 1);

        int[] indexes = {1, 0, 2, 2, 3, 1};

        mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        mesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        mesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(indexes));
        mesh.updateBound();
        return mesh;
    }
}
