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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import speedbomber.Game;
import speedbomber.controller.GameEvent;

/**
 *
 * @author FalseCAM
 */
public class GameServer implements ConnectionListener {

    public static final String NAME = "Speed Bomber";
    public static final int VERSION = 1;
    public static final int STDPORT = 35386;
    static GameServer singleton = null;
    private static int port = 14589;
    private float gameTime = 0;
    com.jme3.network.Server server = null;
    // <ClientId, PlayerNumber>
    HashMap<Integer, Integer> playerClientIds = new HashMap<Integer, Integer>();
    // <ClientId, Name>
    HashMap<Integer, String> clientNames = new HashMap<Integer, String>();
    int ready = 0;

    private GameServer() {

        Serializer.registerClass(CommandMessage.class);
        Serializer.registerClass(GameMessage.class);
        Serializer.registerClass(GameEvent.class);
        Serializer.registerClass(ServerMessage.class);
        Serializer.registerClass(LobbyMessage.class);

        try {
            server = Network.createServer(NAME, VERSION, port, port);
            server.start();
            server.addMessageListener(new ServerListener(), CommandMessage.class);
            server.addMessageListener(new ServerListener(), GameMessage.class);
            server.addMessageListener(new ServerListener(), LobbyMessage.class);
            server.addConnectionListener(this);
        } catch (IOException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void init(int p) {
        port = p;
        instance();
    }

    public static void stop() {
        singleton.server.close();
    }

    public static GameServer instance() {
        if (singleton == null) {
            singleton = new GameServer();
        }
        return singleton;
    }

    static Float getTime() {
        return singleton.gameTime;
    }

    public static void update(float tpf) {
        singleton.gameTime += tpf;
    }

    public static com.jme3.network.Server getServer() {
        return singleton.server;
    }

    public Map<Integer, Integer> getPlayerClientIds() {
        return this.playerClientIds;
    }

    public Map<Integer, String> getClientNames() {
        return this.clientNames;
    }

    public void connectionAdded(Server server, HostedConnection conn) {
        ServerMessage sm = new ServerMessage(Game.instance().getName(), Game.instance().getMap());
        conn.send(sm);
        System.out.println("Client #" + conn.getId() + " connected with IP: " + conn.getAddress());
    }

    public void connectionRemoved(Server server, HostedConnection conn) {
        System.out.println("Client #" + conn.getId() + " DISconnected with IP: " + conn.getAddress());
    }

    public void restartGame() {
        System.out.println("Restart Game");
        gameTime = 0;
        ready = 0;
        playerClientIds.clear();
        LinkedList<HostedConnection> connections = new LinkedList<HostedConnection>();
        connections.addAll(server.getConnections());
        int players = connections.size();
        for (int i = 0; i < players; i++) {
            playerClientIds.put(connections.get(i).getId(), i);
            // start game with number of connections players, with 8 players if number of connections is 1
            Message message = new CommandMessage(CommandMessage.MessageType.START, (players > 1 ? players : 8));
            server.broadcast(Filters.in(connections.get(i)), message);
        }
    }

    public void updateLobby() {
        Message message = new LobbyMessage(LobbyMessage.MessageType.UPDATE, new LinkedList<String>(clientNames.values()));
        server.broadcast(message);
    }

    public void ready() {
        ready++;
    }

    public boolean allReady() {
        return ready >= playerClientIds.size();
    }

    void startGame() {
        System.out.println("Start Game");
        synchronized (NAME) {
            try {
                NAME.wait(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Message message = new LobbyMessage(LobbyMessage.MessageType.START);
        server.broadcast(message);

        LinkedList<HostedConnection> connections = new LinkedList<HostedConnection>();
        connections.addAll(GameServer.getServer().getConnections());
        for (int i = 0; i < connections.size(); i++) {
            String n = GameServer.instance().getClientNames().get(connections.get(i).getId());
            int id = GameServer.instance().getPlayerClientIds().get(connections.get(i).getId());
            Message m = new CommandMessage(CommandMessage.MessageType.SETNAME, id, n);
            GameServer.getServer().broadcast(m);
        }
    }
}
