package speedbomber;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import speedbomber.model.units.Bomb;
import speedbomber.model.units.Haunter;
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
        Bomb bomb = new Bomb();
        Haunter haunter = new Haunter();
        this.rootNode.attachChild(map);
        this.rootNode.attachChild(bomb);
        this.rootNode.attachChild(haunter);

        createLight();
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void createLight() {
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        rootNode.addLight(sun);
    }
}
