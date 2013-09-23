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
public class ServerMessage extends AbstractMessage {

    private String name;
    private String map;

    public ServerMessage() {
    }

    public ServerMessage(String name, String map) {
        this.name = name;
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public String getMap() {
        return map;
    }
}
