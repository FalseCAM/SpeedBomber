/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model;

import speedbomber.model.player.Player;

/**
 *
 * @author FalseCAM
 */
public class User {

    Player player;

    public User() {
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
