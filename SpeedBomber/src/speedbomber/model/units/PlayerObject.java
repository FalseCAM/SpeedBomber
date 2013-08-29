/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.units;

import speedbomber.model.GameObject;
import speedbomber.model.player.Player;

/**
 *
 * @author FalseCAM
 */
public abstract class PlayerObject extends GameObject {

    Player player = null;

    public Player getOwner() {
        return player;
    }
}
