/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.units;

import com.jme3.audio.AudioNode;
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
import speedbomber.model.GameWorld;
import speedbomber.model.effects.Explosion;
import speedbomber.model.player.Player;

/**
 *
 * @author FalseCAM
 */
public class Grenade extends PlayerObject {

    private float lifeTime = 0;
    private final static float maxLifeTime = 12;
    private final static float explosionRadius = 5f;
    AudioNode sound;

    public Grenade(GameWorld world, Player player, Vector3f target) {
        this.player = player;
        this.world = world;
        node = new Node("Grenade");

        sound = new AudioNode(Game.getAssetManager(), "Sounds/small-explosion.wav");

        Haunter haunter = player.getHaunter();

        Vector3f translation = new Vector3f(haunter.getNode().getWorldTranslation()).add(0, 2f, 0);
        createPhysic();
        create();
        physics.setPhysicsLocation(translation);
        Vector3f dir = target.subtract(translation);
        dir = dir.setY(0);
        physics.setLinearVelocity(dir);

    }

    private void create() {
        Sphere sphere = new Sphere(8, 8, 0.2f, true, false);
        sphere.setTextureMode(TextureMode.Projected);
        Geometry geometry = new Geometry("Grenade", sphere);

        //Material mat = new Material(Game.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        //mat.setColor("Color", player.getColor());
        Material mat = new Material(Game.getAssetManager(),
                "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", player.getColor());
        mat.setColor("Diffuse", player.getColor());
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 12);
        geometry.setMaterial(mat);
        node.attachChild(geometry);

        this.node.attachChild(sound);
        node.addControl(physics);
        physics.setFriction(1f);
    }

    private void createPhysic() {
        physics = new RigidBodyControl(0.5f);
        group = GameObjectGroup.WEAPON;
        physics.setCollisionGroup(group.getPhysicsGroup());
        physics.setCollideWithGroups(GameObjectGroup.MAP.getPhysicsGroup());
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
        Explosion explosion = new Explosion(node.getWorldTranslation(), explosionRadius * Game.scale);
        world.attachObject(explosion);
        sound.play();
        for (PlayerObject playerObject : world.getPlayerObjects(this, explosionRadius * Game.scale)) {
            if (playerObject != this) {
                playerObject.doDamage(this);
            }
        }
    }

    @Override
    public void doDamage(PlayerObject origin) {
        if (lifeTime < maxLifeTime - 0.5f) {
            lifeTime = maxLifeTime - 0.5f;
        }
    }
}
