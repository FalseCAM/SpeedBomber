package speedbomber;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import speedbomber.model.world.map.AbstractMap;
import speedbomber.model.world.map.Map;

/**
 *
 * @author falsecam
 */
public class Main extends SimpleApplication {
    
    public static void main(String[] args) {
        Main app = new Main();
        Game.init(app);
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        AbstractMap abstractMap = AbstractMap.loadMapFile("Maps/Map.map");
        Map map = new Map(abstractMap);
        this.rootNode.attachChild(map);
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }
    
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
