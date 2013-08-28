/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.network;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
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
            CommandMessage helloMessage = (CommandMessage) message;
            System.out.println("Client #" + source.getId() + " received: '" + helloMessage.getMessage() + "'");
        } // else...
    }
}