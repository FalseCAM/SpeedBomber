/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.network;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import speedbomber.controller.GameEvent;

/**
 *
 * @author FalseCAM
 */
public class ServerListener implements MessageListener<HostedConnection> {

    public void messageReceived(HostedConnection source, Message message) {
        if (message instanceof GameMessage) {
            GameMessage gMessage = (GameMessage) message;
            GameEvent newEvent = new GameEvent(1f, source.getId(), gMessage.getEvent());
            Message bmessage = new GameMessage(newEvent);
            GameServer.getClient().broadcast(bmessage);

        } else if (message instanceof CommandMessage) {
            // do something with the message
            CommandMessage helloMessage = (CommandMessage) message;
            System.out.println("Server received '" + helloMessage.getMessage() + "' from client #" + source.getId());
        }
    }
}
