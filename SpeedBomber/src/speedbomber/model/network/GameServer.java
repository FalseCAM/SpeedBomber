/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.network;

import com.jme3.network.ConnectionListener;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import speedbomber.controller.GameEvent;

/**
 *
 * @author FalseCAM
 */
public class GameServer implements ConnectionListener {

    public static final String NAME = "Speed Bomber";
    public static final int VERSION = 1;
    static GameServer singleton = null;
    private static String host;
    private static int port = 14589;
    com.jme3.network.Server myServer = null;
    HashMap<Integer, Integer> playerClientIds = new HashMap<Integer, Integer>();

    private GameServer() {

        Serializer.registerClass(CommandMessage.class);
        Serializer.registerClass(GameMessage.class);
        Serializer.registerClass(GameEvent.class);

        try {
            myServer = Network.createServer(NAME, VERSION, port, port);
            myServer.start();
            myServer.addMessageListener(new ServerListener(), CommandMessage.class);
            myServer.addMessageListener(new ServerListener(), GameMessage.class);
            myServer.addConnectionListener(this);
        } catch (IOException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void init(int p) {
        port = p;
        instance();
    }

    public static GameServer instance() {
        if (singleton == null) {
            singleton = new GameServer();
        }
        return singleton;
    }

    public static com.jme3.network.Server getClient() {
        return singleton.myServer;
    }

    public Map<Integer, Integer> getPlayerClientIds() {
        return this.playerClientIds;
    }

    public void connectionAdded(Server server, HostedConnection conn) {
        System.out.println("Client #" + conn.getId() + " connected with IP: " + conn.getAddress());
        if (playerClientIds.isEmpty()) {
            startGame();
        }
    }

    public void connectionRemoved(Server server, HostedConnection conn) {
        System.out.println("Client #" + conn.getId() + " DISconnected with IP: " + conn.getAddress());
    }

    public void startGame() {
        playerClientIds.clear();
        LinkedList<HostedConnection> connections = new LinkedList<HostedConnection>();
        connections.addAll(myServer.getConnections());
        for (int i = 0; i < connections.size(); i++) {
            playerClientIds.put(connections.get(i).getId(), i);
            Message message = new CommandMessage(CommandMessage.MessageType.START, i);
            myServer.broadcast(Filters.in(connections.get(i)), message);
        }

    }
}
