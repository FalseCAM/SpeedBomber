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
import speedbomber.model.network.NetworkMessage;
import speedbomber.model.network.ServerListener;

/**
 *
 * @author FalseCAM
 */
public class ServerMain extends SimpleApplication {

    public static final String NAME = "Speed Bomber";
    public static final int VERSION = 1;
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
        System.out.println("Port: " + port);
        Server myServer = null;
        Serializer.registerClass(NetworkMessage.class);
        try {
            myServer = Network.createServer(NAME, VERSION, port, port);
            myServer.start();
            myServer.addMessageListener(new ServerListener(), NetworkMessage.class);
            // Keep running basically forever
            synchronized (NAME) {
                try {
                    NAME.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}