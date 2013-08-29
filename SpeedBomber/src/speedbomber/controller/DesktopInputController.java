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
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.Message;
import speedbomber.Game;
import speedbomber.model.network.CommandMessage;
import speedbomber.model.network.GameClient;

/**
 *
 * @author FalseCAM
 */
public class DesktopInputController implements InputController, ActionListener, AnalogListener {

    private InputManager inputManager;
    private PlayerController pC;

    public DesktopInputController() {
    }

    @Override
    public void initInput(InputManager inputManager) {
        this.inputManager = inputManager;
        inputManager.addMapping("Bomb",
                new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Restart",
                new KeyTrigger(KeyInput.KEY_R));
        inputManager.addMapping("Grenade",
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("Move",
                new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(this, "Bomb", "Restart");
        inputManager.addListener(this, new String[]{"Move", "Grenade"});
    }

    public void cleanup() {
        inputManager.deleteMapping("Bomb");
        inputManager.deleteMapping("Restart");
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
        this.pC = playerController;
        System.out.println("not null + " + playerController);
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("Bomb") && !isPressed) {
            if (pC != null) {
                pC.bomb();
            }
        } else if (name.equals("Restart") && !isPressed) {
            Message message = new CommandMessage(CommandMessage.MessageType.RESTART);
            GameClient.getClient().send(message);
        }
    }

    public void onAnalog(String name, float value, float tpf) {
        if (name.equals("Move")) {
            if (pC != null) {
                pC.move(getMousePick(inputManager.getCursorPosition()));
            }

        } else if (name.equals("Grenade")) {
            if (pC != null) {
                pC.grenade(getMousePick(inputManager.getCursorPosition()));
            }
        }
    }
}
