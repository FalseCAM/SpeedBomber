/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import speedbomber.Game;
import speedbomber.model.LevelAppState;
import speedbomber.model.player.Player;
import speedbomber.model.units.Haunter;

/**
 *
 * @author FalseCAM
 */
public class GameController {

    float time = -0.1f;
    LevelAppState level;
    List<GameEvent> gameEvents = new LinkedList<GameEvent>();

    public GameController(LevelAppState level) {
        this.level = level;
    }

    public void addEvent(final GameEvent event) {
        Game.getMain().enqueue(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                gameEvents.add(event);
                return true;
            }
        });
    }

    public void doEvent(final GameEvent event) {
        if (level.getPlayers().size() < 1) {
            return;
        }
        if (event.getType().equals(GameEvent.GameEventType.MOVETO)) {
            Player player = level.getPlayers().get(event.getPlayerID());
            Haunter haunter = level.getHaunter(player);
            haunter.move(event.getPosition());
        } else if (event.getType().equals(GameEvent.GameEventType.THROWGRENADE)) {
            final Player player = level.getPlayers().get(event.getPlayerID());
            Game.getMain().enqueue(new Callable<Boolean>() {
                public Boolean call() throws Exception {
                    level.throwGrenade(player, event.getPosition());
                    return true;
                }
            });
        } else if (event.getType().equals(GameEvent.GameEventType.PLACEBOMB)) {
            final Player player = level.getPlayers().get(event.getPlayerID());
            Game.getMain().enqueue(new Callable<Boolean>() {
                public Boolean call() throws Exception {
                    level.placeBomb(player);
                    return true;
                }
            });
        }
    }

    public void setLevel(LevelAppState level) {
        this.level = level;
    }

    public void setPlayerName(Integer id, String message) {
        this.level.getPlayers().get(id).setName(message);
    }

    public void update(float tpf) {
        time += tpf;
        List<GameEvent> doneEvents = new LinkedList<GameEvent>();
        for (GameEvent event : gameEvents) {
            if (event.getTimeStamp() < time) {
                doEvent(event);
                doneEvents.add(event);
            }
        }
        gameEvents.removeAll(doneEvents);
    }
}
