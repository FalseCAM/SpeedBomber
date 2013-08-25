/**
 *
 */
package speedbomber.model.world.map;

import speedbomber.Game;

/**
 * @author FalseCAM
 *
 */
public class AbstractMap {

    private MapType[][] objects;
    private Integer width;
    private Integer height;

    public AbstractMap(MapType[][] objects) {
        this.objects = objects;
        this.width = objects.length;
        this.height = objects[0].length;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                builder.append(objects[j][i].toString());
            }
            if (j < height - 1) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public MapType get(Integer x, Integer y) {
        return objects[x][height - y - 1];
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public static AbstractMap loadMapFile(String file) {
        Game.instance().getAssetManager().registerLoader(MapLoader.class, "map");
        return (AbstractMap) Game.instance().getAssetManager().loadAsset(file);
    }
}
