/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.network;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import speedbomber.controller.GameEvent;

/**
 *
 * @author FalseCAM
 */
@Serializable
public class GameMessage extends AbstractMessage {

    private GameEvent event;

    public GameMessage() {
    }

    public GameMessage(GameEvent event) {
        this.event = event;
    }

    public GameEvent getEvent() {
        return event;
    }
}
