/**
 *
 */
package speedbomber.model.world.map;

/**
 * @author FalseCAM
 *
 */
public enum MapType {

    WALL("X", "Wall"), SPAWNPOINT("S", "SpawnPoint"), FLOOR(" ", "Floor");
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
        } else if (chr.equals("S")) {
            return SPAWNPOINT;
        } else {
            return FLOOR;
        }
    }
}
