/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.network;

import com.jme3.network.Message;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import speedbomber.ServerMain;
import speedbomber.controller.GameEvent;

/**
 *
 * @author FalseCAM
 */
public class GameClient {

    static GameClient singleton = null;
    private static String host;
    private static int port = 14589;
    com.jme3.network.Client myClient = null;

    private GameClient() {
        Serializer.registerClass(CommandMessage.class);
        Serializer.registerClass(GameMessage.class);
        Serializer.registerClass(GameEvent.class);

        try {
            myClient = Network.connectToServer(GameServer.NAME, GameServer.VERSION, host, port, port);
            myClient.start();
            myClient.addMessageListener(new ClientListener(), CommandMessage.class);
            myClient.addMessageListener(new ClientListener(), GameMessage.class);
            Message message = new CommandMessage(CommandMessage.MessageType.HELLO, "Hello World!");
            myClient.send(message);
        } catch (IOException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void init(String h, int p) {
        host = h;
        port = p;
        instance();
    }

    public static GameClient instance() {
        if (singleton == null) {
            singleton = new GameClient();
        }
        return singleton;
    }

    public static com.jme3.network.Client getClient() {
        return singleton.myClient;
    }
}
