/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.network;

import com.jme3.network.Message;
import com.jme3.network.Network;
import com.jme3.network.Server;
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
public class GameServer {

    public static final String NAME = "Speed Bomber";
    public static final int VERSION = 1;
    static GameServer singleton = null;
    private static String host;
    private static int port = 14589;
    com.jme3.network.Server myServer = null;

    private GameServer() {

        Serializer.registerClass(CommandMessage.class);
        Serializer.registerClass(GameMessage.class);
        Serializer.registerClass(GameEvent.class);

        try {
            myServer = Network.createServer(NAME, VERSION, port, port);
            myServer.start();
            myServer.addMessageListener(new ServerListener(), CommandMessage.class);
            myServer.addMessageListener(new ServerListener(), GameMessage.class);

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
}
