/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.network;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author FalseCAM
 */
@Serializable
public class NetworkMessage extends AbstractMessage {

    String message;

    public NetworkMessage() {
    }

    public NetworkMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
