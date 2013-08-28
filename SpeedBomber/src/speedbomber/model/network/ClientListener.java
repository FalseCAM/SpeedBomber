/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.network;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

/**
 *
 * @author Dev
 */
public class ClientListener implements MessageListener<Client> {

    public void messageReceived(Client source, Message message) {
        if (message instanceof NetworkMessage) {
            // do something with the message
            NetworkMessage helloMessage = (NetworkMessage) message;
            System.out.println("Client #" + source.getId() + " received: '" + helloMessage.getMessage() + "'");
        } // else...
    }
}