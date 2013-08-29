/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.controller;

import com.jme3.math.Vector3f;
import com.jme3.network.Message;
import speedbomber.model.network.GameClient;
import speedbomber.model.network.GameMessage;

/**
 *
 * @author FalseCAM
 */
public class PlayerController {

    float lastMove = 0;
    float lastBomb = 0;
    float lastGrenade = 0;

    public PlayerController() {
    }

    public void update(float tpf) {
        lastMove += tpf;
        lastBomb += tpf;
        lastGrenade += tpf;
    }

    void bomb() {
        if (lastBomb > 2f) {
            lastBomb = 0;
            GameEvent event = new GameEvent(0f, 0, GameEvent.GameEventType.PLACEBOMB);
            GameMessage message = new GameMessage(event);
            GameClient.getClient().send(message);
        }
    }

    void move(Vector3f pick) {

        if (pick != null && lastMove > 0.1f) {
            lastMove = 0;
            GameEvent event = new GameEvent(0f, 0, GameEvent.GameEventType.MOVETO, pick);
            Message message = new GameMessage(event);
            GameClient.getClient().send(message);
        }
    }

    void grenade(Vector3f pick) {
        if (pick != null && lastGrenade > 0.3f) {
            lastGrenade = 0;
            GameEvent event = new GameEvent(0f, 0, GameEvent.GameEventType.THROWGRENADE, pick);
            Message message = new GameMessage(event);
            GameClient.getClient().send(message);
        }
    }
}
