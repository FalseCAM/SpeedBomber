/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.controller;

import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import speedbomber.Game;

/**
 *
 * @author FalseCAM
 */
public class DesktopInputController implements InputController, ActionListener, AnalogListener {

    private InputManager inputManager;
    private PlayerController playerController;

    public DesktopInputController() {
    }

    @Override
    public void initInput(InputManager inputManager) {
        this.inputManager = inputManager;
        inputManager.addMapping("Bomb",
                new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("ChangeCamera",
                new KeyTrigger(KeyInput.KEY_C));
        inputManager.addMapping("Statistics",
                new KeyTrigger(KeyInput.KEY_TAB));
        inputManager.addMapping("CameraUp",
                new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("CameraDown",
                new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("CameraLeft",
                new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("CameraRight",
                new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("CameraIn",
                new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        inputManager.addMapping("CameraOut",
                new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        inputManager.addMapping("Grenade",
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("Move",
                new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(this, new String[]{"Bomb", "ChangeCamera", "Statistics"});
        inputManager.addListener(this, new String[]{"Move", "Grenade", "CameraUp",
                    "CameraDown", "CameraLeft", "CameraRight", "CameraIn", "CameraOut"});
    }

    public void cleanup() {
        inputManager.deleteMapping("Bomb");
        inputManager.deleteMapping("Restart");
        inputManager.deleteMapping("Statistics");
        inputManager.deleteMapping("Move");
        inputManager.deleteMapping("Grenade");
        inputManager.removeListener(this);
        inputManager.removeListener(this);
    }

    public Vector3f getMousePick(Vector2f cursorPosition) {
        Vector3f pick = null;
        CollisionResults results = new CollisionResults();

        Vector2f click2d = cursorPosition;
        Vector3f click3d = Game.getCam().getWorldCoordinates(
                new Vector2f(click2d.getX(), click2d.getY()), 0f);
        Vector3f dir = Game.getCam().getWorldCoordinates(
                new Vector2f(click2d.getX(), click2d.getY()), 1f).subtractLocal(click3d);
        // Aim the ray from the clicked spot forwards.
        Ray ray = new Ray(click3d, dir);
        Game.getRoodNode().collideWith(ray, results);

        if (results.size() > 0) {
            pick = results.getClosestCollision().getGeometry().getWorldTranslation();
        }
        return pick;
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("Bomb") && !isPressed) {
            if (playerController != null) {
                playerController.bomb();
            }
        } else if (name.equals("ChangeCamera") && !isPressed) {
            if(playerController != null){
                playerController.changeCamera();
            }
        } else if (name.equals("Statistics")) {
            if (playerController != null) {
                playerController.showStatistics(isPressed);
            }
        }
    }

    public void onAnalog(String name, float value, float tpf) {
        if (name.equals("Move")) {
            if (playerController != null) {
                playerController.move(getMousePick(inputManager.getCursorPosition()));
            }

        } else if (name.equals("Grenade")) {
            if (playerController != null) {
                playerController.grenade(getMousePick(inputManager.getCursorPosition()));
            }
        } else if (name.equals("CameraUp")) {
            if (playerController != null) {
                playerController.moveCamUp();
            }
        } else if (name.equals("CameraDown")) {
            if (playerController != null) {
                playerController.moveCamDown();
            }
        } else if (name.equals("CameraLeft")) {
            if (playerController != null) {
                playerController.moveCamLeft();
            }
        } else if (name.equals("CameraRight")) {
            if (playerController != null) {
                playerController.moveCamRight();
            }
        } else if (name.equals("CameraIn")) {
            if (playerController != null) {
                playerController.moveCamIn();
            }
        } else if (name.equals("CameraOut")) {
            if (playerController != null) {
                playerController.moveCamOut();
            }
        }
    }
}
