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
import com.jme3.network.Message;
import speedbomber.Game;
import speedbomber.model.network.CommandMessage;
import speedbomber.model.network.GameClient;
import speedbomber.model.network.GameMessage;

/**
 *
 * @author FalseCAM
 */
public class DesktopInputController implements InputController {

    private final InputManager inputManager;

    public DesktopInputController() {
        this.inputManager = Game.getInputManager();
    }

    @Override
    public void initInput() {
        // Mouse movements
        inputManager.addMapping("Up",
                new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        inputManager.addMapping("Down",
                new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        inputManager.addMapping("Left",
                new MouseAxisTrigger(MouseInput.AXIS_X, false));
        inputManager.addMapping("Right",
                new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inputManager.addMapping("In",
                new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        inputManager.addMapping("Out",
                new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        inputManager.addMapping("Bomb",
                new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Restart",
                new KeyTrigger(KeyInput.KEY_R));
        inputManager.addMapping("Grenade",
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("Move",
                new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(actionListener, "Bomb", "Restart");
        inputManager.addListener(analogListener, new String[]{"Up", "Down",
                    "Left", "Right", "In", "Out", "Move", "Grenade"});
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Bomb") && !keyPressed) {
                GameEvent event = new GameEvent(0f, 0, GameEvent.GameEventType.PLACEBOMB);
                GameMessage message = new GameMessage(event);
                GameClient.getClient().send(message);
            } else if (name.equals("Restart") && !keyPressed) {
                Message message = new CommandMessage(CommandMessage.MessageType.RESTART);
                GameClient.getClient().send(message);
            }
        }
    };
    private AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float intensity, float tpf) {
            if (name.equals("Move")) {
                Vector3f pick = getMousePick(inputManager.getCursorPosition());
                if (pick != null) {
                    GameEvent event = new GameEvent(0f, 0, GameEvent.GameEventType.MOVETO, pick);
                    Message message = new GameMessage(event);
                    GameClient.getClient().send(message);
                }
            } else if (name.equals("Grenade")) {
                Vector3f pick = getMousePick(inputManager.getCursorPosition());
                if (pick != null) {
                    GameEvent event = new GameEvent(0f, 0, GameEvent.GameEventType.THROWGRENADE, pick);
                    Message message = new GameMessage(event);
                    GameClient.getClient().send(message);
                }
            }
        }
    };

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
}
