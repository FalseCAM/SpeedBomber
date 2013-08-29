/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.units;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import speedbomber.Game;
import speedbomber.model.GameObjectGroup;
import speedbomber.model.GameWorld;
import speedbomber.model.player.Player;

/**
 *
 * @author FalseCAM
 */
public class Bomb extends PlayerObject {

    private float lifeTime = 0;
    private final static float maxLifeTime = 10;
    private final static float explosionRadius = 5f;
    BetterCharacterControl character;
    private Vector3f target;

    public Bomb(GameWorld world, Player player, Vector3f startPoint) {
        this.world = world;
        this.player = player;
        this.target = startPoint;

        node = new Node("Bomb");
        group = GameObjectGroup.WEAPON;

        create();
        character = new BetterCharacterControl(0.25f, 0.5f, 2f);
        node.addControl(character);
        character.warp(target);
    }

    private void create() {
        Spatial spatial = Game.getAssetManager().loadModel("Models/Bomb.j3o");
        node.attachChild(spatial);
    }

    @Override
    public void update(float tpf) {
        lifeTime += tpf;
        if (isAlive()) {
            calculateTarget();
            if (target != null && node.getWorldTranslation().distance(target) > 0.5f) {
                Vector3f dir = this.target.subtract(node.getWorldTranslation());
                character.setWalkDirection(dir.normalize().mult(15*Game.scale));
                character.setViewDirection(new Vector3f(dir.normalize().x, 0, dir.normalize().z));
            } else {
                this.target = null;
                character.setWalkDirection(Vector3f.ZERO);
            }

        }
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

    private void calculateTarget() {
        for (PlayerObject playerObject : world.getPlayerObjects(this, 50)) {
            if (playerObject instanceof Haunter) {
                if (playerObject != player.getHaunter()) {
                    Vector3f position = this.node.getWorldTranslation();
                    Vector3f haunter = playerObject.getNode().getWorldTranslation();

                    if (target == null || position.distance(target) > position.distance(haunter)) {
                        this.target = haunter;
                    }
                }
            }
        }
    }
}
