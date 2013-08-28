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
public class CommandMessage extends AbstractMessage {

    @Serializable
    public enum MessageType {

        HELLO("Hello"), RESTART("Restart"), QUIT("Quit");
        String name;

        private MessageType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    private String message;
    private MessageType type;

    public CommandMessage() {
    }

    public CommandMessage(MessageType type) {
        this.type = type;
    }

    public CommandMessage(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
