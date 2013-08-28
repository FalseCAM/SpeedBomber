/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.network;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

/**
 *
 * @author FalseCAM
 */
public class ServerListener implements MessageListener<HostedConnection> {

    public void messageReceived(HostedConnection source, Message message) {
        if (message instanceof NetworkMessage) {
            // do something with the message
            NetworkMessage helloMessage = (NetworkMessage) message;
            System.out.println("Server received '" + helloMessage.getMessage() + "' from client #" + source.getId());
        } // else....
    }
}
