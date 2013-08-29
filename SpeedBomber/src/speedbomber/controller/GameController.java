/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.controller;

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

    LevelAppState level;

    public GameController(LevelAppState level) {
        this.level = level;
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
}
