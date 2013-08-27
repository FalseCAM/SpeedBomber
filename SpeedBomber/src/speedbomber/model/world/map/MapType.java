/**
 *
 */
package speedbomber.model.world.map;

/**
 * @author FalseCAM
 *
 */
public enum MapType {

    WALL("X", "Wall"), SPAWNPOINT("0", "SpawnPoint"), FLOOR(" ", "Floor");
    private String chr;
    private String name;

    private MapType(String chr, String name) {
        this.chr = chr;
        this.name = name;
    }

    @Override
    public String toString() {
        return chr;
    }

    public String getName() {
        return name;
    }

    public static MapType get(String chr) {
        if (chr.equals("X")) {
            return WALL;
        } else if (chr.equals("0")) {
            return SPAWNPOINT;
        } else if (chr.equals("1")) {
            return SPAWNPOINT;
        } else if (chr.equals("2")) {
            return SPAWNPOINT;
        } else if (chr.equals("3")) {
            return SPAWNPOINT;
        } else if (chr.equals("4")) {
            return SPAWNPOINT;
        } else if (chr.equals("5")) {
            return SPAWNPOINT;
        } else if (chr.equals("6")) {
            return SPAWNPOINT;
        } else if (chr.equals("7")) {
            return SPAWNPOINT;
        } else {
            return FLOOR;
        }
    }
}
