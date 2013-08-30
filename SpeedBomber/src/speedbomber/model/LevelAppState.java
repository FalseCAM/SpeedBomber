/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.Message;
import com.jme3.scene.Node;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import speedbomber.ClientMain;
import speedbomber.Game;
import speedbomber.controller.GameController;
import speedbomber.controller.PlayerController;
import speedbomber.model.network.CommandMessage;
import speedbomber.model.network.GameClient;
import speedbomber.model.player.Player;
import speedbomber.model.units.Bomb;
import speedbomber.model.units.Grenade;
import speedbomber.model.units.Haunter;
import speedbomber.model.units.PlayerObject;
import speedbomber.model.world.map.AbstractMap;
import speedbomber.model.world.map.Map;

/**
 *
 * @author FalseCAM
 */
public class LevelAppState extends AbstractAppState implements GameWorld {

    public static final String mapFile = "Maps/Map.map";
    ClientMain app;
    Node rootNode;
    BulletAppState bulletAppState;
    DirectionalLight sun = new DirectionalLight();
    AmbientLight al = new AmbientLight();
    List<GameObject> gameObjects = new LinkedList<GameObject>();
    List<GameObject> attachList = new LinkedList<GameObject>();
    List<GameObject> detachList = new LinkedList<GameObject>();
    AbstractMap abstractMap;
    Map map;
    List<Player> players = new LinkedList<Player>();
    HashMap<Player, Haunter> haunters = new HashMap<Player, Haunter>();
    GameController gameController;
    PlayerController playerController;
    Statistics statistics;
    int nrPlayer = 0;
    boolean enabled = false;

    public LevelAppState(Integer nrPlayer) {
        super();
        this.nrPlayer = nrPlayer;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (ClientMain) app;
        this.rootNode = new Node();
        gameController = new GameController(this);
        playerController = new PlayerController(this);
        GameClient.getClientListener().setGameController(this.gameController);
        this.app.getInputController().setPlayerController(this.playerController);
        initPhysics();
        initLights();
        initMap();
        initPlayer();
        statistics = new Statistics(players);
        initHaunter();
        initCamera();

        this.app.getRootNode().attachChild(this.rootNode);
        System.out.println("Client Ready");
        Message m = new CommandMessage(CommandMessage.MessageType.READY);
        GameClient.getClient().send(m);

    }

    private void initPhysics() {
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        app.getStateManager().attach(bulletAppState);
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, -9.81f, 0));
        // Debug Physics
        //bulletAppState.getPhysicsSpace().enableDebug(app.getAssetManager());
    }

    private void initLights() {
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        rootNode.addLight(sun);
        al.setColor(ColorRGBA.White.mult(0.4f));
        rootNode.addLight(al);
    }

    private void initMap() {
        abstractMap = AbstractMap.loadMapFile(mapFile);
        map = new Map(abstractMap);
        attachObject(map);
    }

    private void initPlayer() {
        players.clear();
        // init player for min of users connected and spawn points
        int playerCount = Math.min(nrPlayer, map.getSpawnPoints().size());
        for (int i = 0; i < playerCount; i++) {
            players.add(Player.getPlayer(i));
        }
    }

    private void initHaunter() {
        for (int i = 0; i < players.size(); i++) {
            Vector3f charLocation = new Vector3f(map.getSpawnPoint(i).getWorldTranslation().getX(), 0, map.getSpawnPoint(i).getWorldTranslation().getZ());
            Haunter haunter = new Haunter(this, players.get(i), charLocation);
            haunter.setStatistics(this.statistics);
            haunters.put(players.get(i), haunter);
            attachObject(haunter);
        }
    }

    private void initCamera() {

        app.getCamera().setLocation(new Vector3f(0, 35 * Game.scale, 40 * Game.scale));
        app.getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
    }

    public void attachObject(GameObject gameObject) {
        attachList.add(gameObject);
        detachList.remove(gameObject);
        //gameObjects.add(gameObject);
        rootNode.attachChild(gameObject.getNode());
        bulletAppState.getPhysicsSpace().addAll(gameObject.getNode());
    }

    public void detachObject(GameObject gameObject) {
        rootNode.detachChild(gameObject.getNode());
        bulletAppState.getPhysicsSpace().removeAll(gameObject.getNode());
        detachList.add(gameObject);
        attachList.remove(gameObject);
        //gameObjects.remove(gameObject);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        this.bulletAppState.cleanup();
        app.getStateManager().detach(this.bulletAppState);
        app.getRootNode().detachChild(this.rootNode);
        this.app.getInputController().setPlayerController(null);
        GameClient.getClientListener().setGameController(null);
    }

    @Override
    public void setEnabled(boolean enabled) {
        // Pause and unpause
        super.setEnabled(enabled);
        if (enabled && isInitialized()) {
        } else {
        }
        bulletAppState.setEnabled(enabled);
    }

    // Note that update is only called while the state is both attached and enabled.
    @Override
    public void update(float tpf) {
        if (!super.isEnabled()) {
            return;
        }
        gameController.update(tpf);


        gameObjects.addAll(attachList);
        gameObjects.removeAll(detachList);
        attachList.clear();
        detachList.clear();


        updateAliveObjects(tpf);
        removeDeadObjects();
        updateDeadHunters(tpf);

        playerController.update(tpf);
    }

    public void throwGrenade(Player player, Vector3f target) {

        Grenade grenade = new Grenade(this, player, target);
        attachObject(grenade);

    }

    public void placeBomb(Player player) {
        Vector3f target = player.getHaunter().getNode().getWorldTranslation();
        target = target.add(player.getHaunter().getCharacterControl().getViewDirection().normalize().mult(2));
        Bomb bomb = new Bomb(this, player, target);
        attachObject(bomb);

    }

    public Haunter getHaunter(Player player) {
        return haunters.get(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    private void updateAliveObjects(float tpf) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject.isAlive()) {
                gameObject.update(tpf);
            }
        }
    }

    private void removeDeadObjects() {
        List<GameObject> deathObjects = new LinkedList<GameObject>();
        for (GameObject gameObject : gameObjects) {
            if (!gameObject.isAlive()) {
                deathObjects.add(gameObject);
            }
        }
        for (GameObject gameObject : deathObjects) {
            detachObject(gameObject);
        }
    }

    public List<PlayerObject> getPlayerObjects() {
        List<PlayerObject> playerObjects = new LinkedList<PlayerObject>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof PlayerObject) {
                playerObjects.add((PlayerObject) gameObject);
            }
        }
        return playerObjects;
    }

    public List<PlayerObject> getPlayerObjects(PlayerObject playerObject, float distance) {
        List<PlayerObject> playerObjects = new LinkedList<PlayerObject>();
        for (PlayerObject object : getPlayerObjects()) {
            if (playerObject.getNode().getWorldTranslation().distance(object.getNode().getWorldTranslation()) < distance) {
                playerObjects.add(object);
            }
        }
        return playerObjects;
    }

    private void updateDeadHunters(float tpf) {
        for (Player player : players) {
            if (!player.getHaunter().isAlive()) {
                player.getHaunter().update(tpf);
            }
        }
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public ClientMain getApp() {
        return app;
    }
}
