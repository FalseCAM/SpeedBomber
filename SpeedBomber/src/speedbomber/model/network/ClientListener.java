/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.network;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import speedbomber.Game;
import speedbomber.controller.GameController;

/**
 *
 * @author FalseCAM
 */
public class ClientListener implements MessageListener<Client> {

    public void messageReceived(Client source, Message message) {
        if (message instanceof GameMessage) {
            GameMessage gMessage = (GameMessage) message;
            GameController.instance().doEvent(gMessage.getEvent());
        } else if (message instanceof CommandMessage) {
            // do something with the message
            CommandMessage cMessage = (CommandMessage) message;
            if (cMessage.getType().equals(CommandMessage.MessageType.START)) {
                Game.instance().restart(cMessage.getId());
            }
        }
    }
}
