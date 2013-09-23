/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model;

import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import speedbomber.Game;

/**
 *
 * @author FalseCAM
 */
public class GameCamera {

    private InputManager inputManager;
    private Camera camera;
    ChaseCamera chaseCam;

    public GameCamera(Camera cam, InputManager inputManager, Spatial spatial) {
        this.inputManager = inputManager;
        this.camera = cam;
        chaseCam = new ChaseCamera(camera, spatial, inputManager);
        initChaseCam();
        initCamera();
        inputManager.setCursorVisible(true);
    }

    private void initChaseCam() {

        chaseCam.setDefaultDistance(35);
        chaseCam.setMaxDistance(60);
        chaseCam.setLookAtOffset(new Vector3f(0, 5, 0));
        chaseCam.setDragToRotate(false);
        chaseCam.setEnabled(false);

    }

    private void initCamera() {
        camera.setLocation(new Vector3f(0, 35 * Game.scale, 40 * Game.scale));
        camera.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
    }

    public void up() {
        if (chaseCam.isEnabled()) {
            chaseCam.setLookAtOffset(chaseCam.getLookAtOffset().add(0, 1, 0));
        } else {
            camera.setLocation(camera.getLocation().add(new Vector3f(0, 0, -Game.scale)));
        }
    }

    public void down() {
        if (chaseCam.isEnabled()) {
            chaseCam.setLookAtOffset(chaseCam.getLookAtOffset().add(0, -1, 0));
        } else {
            camera.setLocation(camera.getLocation().add(new Vector3f(0, 0, Game.scale)));
        }
    }

    public void left() {
        if (chaseCam.isEnabled()) {
            camera.setRotation(Quaternion.IDENTITY);
        } else {
            camera.setLocation(camera.getLocation().add(new Vector3f(-Game.scale, 0, 0)));
        }
    }

    public void right() {
        if (chaseCam.isEnabled()) {
        } else {
            camera.setLocation(camera.getLocation().add(new Vector3f(+Game.scale, 0, 0)));
        }
    }

    public void in() {
        camera.setLocation(camera.getLocation().add(new Vector3f(0, Game.scale, 0)));
    }

    public void out() {
        camera.setLocation(camera.getLocation().add(new Vector3f(0, -Game.scale, 0)));
    }

    public void setTarget(Spatial spatial) {
        if (spatial == null) {
            chaseCam.setEnabled(false);
            initCamera();
        } else {
            chaseCam.setSpatial(spatial);
            chaseCam.setEnabled(true);

        }
        inputManager.setCursorVisible(true);

    }
}
