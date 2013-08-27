/**
 *
 */
package speedbomber.model.player;

import com.jme3.math.ColorRGBA;

/**
 * @author FalseCAM
 *
 */
public enum PlayerType {

    Player0("0", "Player 1", ColorRGBA.Red), Player1("1", "Player 2", ColorRGBA.Blue),//
    Player2("2", "Player 3", ColorRGBA.Green), Player3("3", "Player 4", ColorRGBA.Gray),//
    Player4("4", "Player 5", ColorRGBA.Red), Player5("5", "Player 6", ColorRGBA.Blue),//
    Player6("6", "Player 7", ColorRGBA.Red), Player7("7", "Player 8", ColorRGBA.Blue);
    private String chr;
    private String name;
    private ColorRGBA color;

    private PlayerType(String chr, String name, ColorRGBA color) {
        this.chr = chr;
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return chr;
    }

    public String getName() {
        return name;
    }

    public ColorRGBA getColor() {
        return color;
    }

    public static PlayerType get(String chr) {
        if (chr.equals("0")) {
            return Player0;
        } else if (chr.equals("1")) {
            return Player1;
        } else if (chr.equals("2")) {
            return Player2;
        } else if (chr.equals("3")) {
            return Player3;
        } else {
            return Player0;
        }
    }
}
