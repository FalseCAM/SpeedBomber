/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model.player;

import com.jme3.math.ColorRGBA;
import speedbomber.model.units.Haunter;

/**
 *
 * @author FalseCAM
 */
public class Player {

    private String name = "Noob";
    private ColorRGBA color = ColorRGBA.White;
    private Haunter haunter;

    public Player(PlayerType type) {
        this.name = type.getName();
        this.color = type.getColor();
    }

    public String getName() {
        return name;
    }

    public ColorRGBA getColor() {
        return color;
    }

    public static Player getPlayer(String chr) {
        PlayerType type = PlayerType.get(chr);
        return new Player(type);
    }

    public static Player getPlayer(int nr) {
        return getPlayer(String.valueOf(nr));
    }

    public Haunter getHaunter() {
        return this.haunter;
    }

    public void setHaunter(Haunter haunter) {
        this.haunter = haunter;
    }
}
