/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber;

import com.jme3.app.SimpleApplication;
import com.jme3.system.JmeContext;
import speedbomber.model.network.GameServer;

/**
 *
 * @author FalseCAM
 */
public class ServerMain extends SimpleApplication {

    private int port = 14589;

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void simpleInitApp() {

        GameServer.init(port);

    }
}