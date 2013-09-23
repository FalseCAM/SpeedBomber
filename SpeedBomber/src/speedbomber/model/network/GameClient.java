/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.network;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import speedbomber.controller.GameEvent;

/**
 *
 * @author FalseCAM
 */
public class GameClient {

    static GameClient singleton = null;
    private static String host;
    private static int port = 14589;
    private static String name = "NoName";
    com.jme3.network.Client myClient = null;
    ClientListener clientListener;

    private GameClient() {
        Serializer.registerClass(CommandMessage.class);
        Serializer.registerClass(GameMessage.class);
        Serializer.registerClass(GameEvent.class);
        Serializer.registerClass(ServerMessage.class);
        Serializer.registerClass(LobbyMessage.class);
    }

    public void init(Client client) {
        myClient = client;
        clientListener = new ClientListener();
        myClient.addMessageListener(clientListener, CommandMessage.class);
        myClient.addMessageListener(clientListener, GameMessage.class);
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

    public static ClientListener getClientListener() {
        return singleton.clientListener;
    }
}
