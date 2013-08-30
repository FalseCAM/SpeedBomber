/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.network;

import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import java.util.LinkedList;
import speedbomber.controller.GameEvent;

/**
 *
 * @author FalseCAM
 */
public class ServerListener implements MessageListener<HostedConnection> {

    public void messageReceived(HostedConnection source, Message message) {
        if (message instanceof GameMessage) {
            GameMessage gMessage = (GameMessage) message;
            GameEvent newEvent = new GameEvent(1f, GameServer.instance().getPlayerClientIds().get(source.getId()), gMessage.getEvent());
            Message bmessage = new GameMessage(newEvent);
            GameServer.getServer().broadcast(bmessage);

        } else if (message instanceof CommandMessage) {
            // do something with the message
            CommandMessage cMessage = (CommandMessage) message;
            System.out.println("Server received '" + cMessage.getType() + " " + cMessage.getMessage() + "' from client #" + source.getId());
            if (cMessage.getType().equals(CommandMessage.MessageType.HELLO)) {
                int id = source.getId();
                String name = cMessage.getMessage();
                GameServer.instance().getClientNames().put(id, name);


            } else if (cMessage.getType().equals(CommandMessage.MessageType.RESTART)) {
                if (source.getId() == 0) {
                    GameServer.instance().restartGame();
                }
            } else if (cMessage.getType().equals(CommandMessage.MessageType.READY)) {
                GameServer.instance().ready();
                if (GameServer.instance().allReady()) {
                    GameServer.instance().startGame();
                }

            }

        }
    }
}
