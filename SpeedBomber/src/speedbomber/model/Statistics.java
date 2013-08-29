/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import speedbomber.model.player.Player;

/**
 *
 * @author FalseCAM
 */
public class Statistics {

    private Map<Player, Integer> kills = new HashMap<Player, Integer>();

    Statistics(List<Player> players) {
        for (Player player : players) {
            kills.put(player, 0);
        }
    }

    public void killFor(Player player) {
        int number = kills.get(player);
        number++;
        kills.put(player, number);
    }

    public int getKills(Player player) {
        return kills.get(player);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Player player : kills.keySet()) {
            sb.append(player.getName());
            sb.append("\t\t");
            sb.append(kills.get(player));
            sb.append("\n");
        }
        return sb.toString();
    }
}
