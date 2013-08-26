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
import speedbomber.model.units.Haunter;

/**
 *
 * @author FalseCAM
 */
public class PlayerController {

    private final Level level;
    private final Haunter haunter;
    private final Camera cam;

    public PlayerController(Level level, Camera cam) {
        this.level = level;
        this.cam = cam;
        this.haunter = level.getHaunter();
    }

    void move(Vector2f cursorPosition) {

        Geometry target = getTarget(cursorPosition);
        if (target != null) {
            haunter.move(target);
        }
    }

    void fire(Vector2f cursorPosition) {
        Geometry target = getTarget(cursorPosition);
        if (target != null) {
            level.placeBomb(target);
        }
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
        Node rootNode = Game.instance().getRoodNode();
        rootNode.collideWith(ray, results);
        // Print the results so we see what is going on
        for (int i = 0; i < results.size(); i++) {
            // For each “hit”, we know distance, impact point, geometry.
            float dist = results.getCollision(i).getDistance();
            Vector3f pt = results.getCollision(i).getContactPoint();
            String target = results.getCollision(i).getGeometry().getName();
            System.out.println("Selection #" + i + ": " + target + " at " + pt + ", " + dist + " WU away.");
        }
        // 5. Use the results -- we rotate the selected geometry.
        if (results.size() > 0) {
            // The closest result is the target that the player picked:
            Geometry target = results.getClosestCollision().getGeometry();
            return target;
        }
        return null;
    }
}
