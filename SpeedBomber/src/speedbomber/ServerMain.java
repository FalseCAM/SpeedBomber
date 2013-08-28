/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import speedbomber.controller.GameEvent;
import speedbomber.model.network.CommandMessage;
import speedbomber.model.network.GameClient;
import speedbomber.model.network.GameMessage;
import speedbomber.model.network.GameServer;
import speedbomber.model.network.ServerListener;

/**
 *
 * @author FalseCAM
 */
public class ServerMain extends SimpleApplication {

    private int port = 14589;

    public static void main(String[] args) {
        ServerMain app = new ServerMain();
        app.start(JmeContext.Type.Headless); // headless type for servers!
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void simpleInitApp() {

        GameServer.init(port);

    }
}