/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.network;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import java.util.List;

/**
 *
 * @author FalseCAM
 */
@Serializable
public class LobbyMessage extends AbstractMessage {

    @Serializable
    public enum MessageType {

        UPDATE("Update"), CHAT("Chat"), READY("Ready"), START("Start");
        String name;

        private MessageType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    private MessageType type;
    private String message;
    private List<String> players;

    public LobbyMessage() {
    }

    public LobbyMessage(MessageType type) {
        this.type = type;
    }

    public LobbyMessage(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }

    public LobbyMessage(MessageType type, List<String> players) {
        this.type = type;
        this.players = players;
    }

    public MessageType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getPlayers() {
        return players;
    }
}
