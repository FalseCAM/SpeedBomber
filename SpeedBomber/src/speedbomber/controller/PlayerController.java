/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.controller;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import speedbomber.Game;
import speedbomber.model.Level;
import speedbomber.model.User;
import speedbomber.model.units.Haunter;

/**
 *
 * @author FalseCAM
 */
public class PlayerController {

    private final Level level;
    private final Haunter haunter;
    private final Camera cam;
    private final User user;

    public PlayerController(User user, Level level, Camera cam) {
        this.user = user;
        this.level = level;
        this.cam = cam;
        this.haunter = level.getHaunter(user.getPlayer());
    }

    private Geometry getTarget(Vector2f cursorPosition) {
        // Reset results list.
        CollisionResults results = new CollisionResults();
        // Convert screen click to 3d position
        Vector2f click2d = cursorPosition;
        Vector3f click3d = cam.getWorldCoordinates(
                new Vector2f(click2d.getX(), click2d.getY()), 0f);
        Vector3f dir = cam.getWorldCoordinates(
                new Vector2f(click2d.getX(), click2d.getY()), 1f).subtractLocal(click3d);
        // Aim the ray from the clicked spot forwards.
        Ray ray = new Ray(click3d, dir);

        // Collect intersections between ray and all nodes in results list.
        Node rootNode = Game.getRoodNode();
        rootNode.collideWith(ray, results);
        if (results.size() > 0) {
            Geometry target = results.getClosestCollision().getGeometry();
            return target;
        }
        return null;
    }
}
