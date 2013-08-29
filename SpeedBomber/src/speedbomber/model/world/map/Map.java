/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.world.map;

import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.LinkedList;
import java.util.List;
import speedbomber.Game;
import speedbomber.model.GameObject;
import speedbomber.model.GameObjectGroup;

/**
 *
 * @author FalseCAM
 */
public class Map extends GameObject {

    private final AbstractMap abstractMap;
    //private RigidBodyControl physics;
    private CollisionShape collision;
    private List<SpawnPoint> spawnPoints = new LinkedList<SpawnPoint>();
    private List<Wall> walls = new LinkedList<Wall>();

    public Map(AbstractMap abstractMap) {
        this.abstractMap = abstractMap;

        node = new Node("Map");
        group = GameObjectGroup.MAP;

        create();
        collision = new BoxCollisionShape(new Vector3f(abstractMap.getWidth() * Game.scale, 1, abstractMap.getHeight() * Game.scale));
        //collision = CollisionShapeFactory.createMeshShape(node);
        physics = new RigidBodyControl(collision, 0.0f);
        node.addControl(physics);

        physics.setCollisionGroup(group.getPhysicsGroup());

        for (Wall wall : walls) {
            wall.addControl(new RigidBodyControl(0f));
        }

    }

    private void create() {
        float x = -abstractMap.getWidth() * Game.scale;
        float z = -abstractMap.getHeight() * Game.scale;
        for (int i = 0; i < abstractMap.getWidth(); i++) {
            for (int j = 0; j < abstractMap.getHeight(); j++) {
                MapType mapType = abstractMap.get(i, j);

                Node element = MapObjectFactory.create(mapType);
                element.scale(Game.scale);
                if (mapType.equals(MapType.SPAWNPOINT)) {
                    SpawnPoint spawnPoint = (SpawnPoint) element;
                    spawnPoints.add(spawnPoint);
                } else if (mapType.equals(MapType.WALL)) {
                    walls.add((Wall) element);
                }
                element.setLocalTranslation(x + Game.scale * 2 * i, 0, z + Game.scale * 2 * j);
                node.attachChild(element);
            }
        }
        node.updateModelBound();
    }

    public SpawnPoint getSpawnPoint(int nr) {
        return spawnPoints.get(nr);
    }

    public List<SpawnPoint> getSpawnPoints() {
        return spawnPoints;
    }

    @Override
    public void update(float tpf) {
    }
}
