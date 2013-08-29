/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model;

import java.util.List;
import speedbomber.model.units.PlayerObject;

/**
 *
 * @author FalseCAM
 */
public interface GameWorld {

    public void attachObject(GameObject gameObject);

    public void detachObject(GameObject gameObject);

    public List<PlayerObject> getPlayerObjects(PlayerObject playerObject, float distance);
}
