/**
 *
 */
package speedbomber.controller;

import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;

/**
 * @author FalseCAM
 *
 */
public interface InputController {

    public void initInput(InputManager inputManager);

    public void setPlayerController(PlayerController playerController);

    public void cleanup();
}
