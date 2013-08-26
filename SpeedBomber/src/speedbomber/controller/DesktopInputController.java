/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.controller;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import speedbomber.Game;

/**
 *
 * @author Uni
 */
public class DesktopInputController implements InputController {

    private final InputManager inputManager;
    private PlayerController playerController;

    public DesktopInputController() {
        this.inputManager = Game.instance().getInputManager();
    }

    @Override
    public void initInput() {
        // Mouse movements
        inputManager.addMapping("Up", new MouseAxisTrigger(MouseInput.AXIS_Y,
                true));
        inputManager.addMapping("Down", new MouseAxisTrigger(MouseInput.AXIS_Y,
                false));
        inputManager.addMapping("Left", new MouseAxisTrigger(MouseInput.AXIS_X,
                false));
        inputManager.addMapping("Right", new MouseAxisTrigger(
                MouseInput.AXIS_X, true));
        inputManager.addMapping("In", new MouseAxisTrigger(
                MouseInput.AXIS_WHEEL, false));
        inputManager.addMapping("Out", new MouseAxisTrigger(
                MouseInput.AXIS_WHEEL, true));
        inputManager.addMapping("Action", new KeyTrigger(KeyInput.KEY_SPACE),
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("Move", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(analogListener, new String[]{"Up", "Down",
                    "Left", "Right", "In", "Out", "Move", "Action"});
    }
    private AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float intensity, float tpf) {
            if (name.equals("Move")) {
                playerController.move(inputManager.getCursorPosition());
            }
        }
    };

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }
}
