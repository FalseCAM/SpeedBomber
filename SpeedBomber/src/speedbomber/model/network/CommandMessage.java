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
public class CommandMessage extends AbstractMessage {

    @Serializable
    public enum MessageType {

        HELLO("Hello"), RESTART("Restart"), QUIT("Quit"), START("Start"), SETNAME("SetName"), READY("Ready");
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
    private Integer id = 0;
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

    public CommandMessage(MessageType type, Integer id) {
        this.type = type;
        this.id = id;
    }

    public CommandMessage(MessageType type, Integer id, String message) {
        this.type = type;
        this.id = id;
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public Integer getId() {
        return id;
    }
}
