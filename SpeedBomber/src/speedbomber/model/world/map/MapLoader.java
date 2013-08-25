/**
 *
 */
package speedbomber.model.world.map;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * @author FalseCAM
 *
 */
public class MapLoader implements AssetLoader {

    private String level;
    private int width;
    private int height;
    private MapType[][] objects;

    public MapLoader() {
    }

    private MapLoader(String level) {
        this.level = level;
        this.width = 0;
        this.height = 0;
    }

    private MapType[][] getObjects() {
        calculateDimensions();
        calculateObjects();
        return objects;
    }

    private void calculateDimensions() {
        String str;
        BufferedReader reader = new BufferedReader(
                new StringReader(level));
        this.width = 0;
        this.height = 0;
        try {
            while ((str = reader.readLine()) != null) {
                if (str.length() > width) {
                    this.width = str.length();
                }
                this.height++;
            }
        } catch (IOException e) {
        }
    }

    private void calculateObjects() {
        this.objects = new MapType[height][width];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.objects[j][i] = MapType.WALL;
            }
        }


        String str;
        BufferedReader reader = new BufferedReader(
                new StringReader(level));
        int j = 0;
        try {
            while ((str = reader.readLine()) != null) {
                for (int i = 0; i < str.length(); i++) {
                    this.objects[j][i] = MapType.get(str.substring(i, i + 1));
                }
                j++;
            }
        } catch (IOException e) {
        }
    }

    public static AbstractMap load(String level) {
        MapLoader mapLoader = new MapLoader(level);
        return new AbstractMap(mapLoader.getObjects());
    }

    @Override
    public Object load(AssetInfo assetInfo) throws IOException {
        InputStream in = assetInfo.openStream();
        StringBuilder levelString = new StringBuilder();
        BufferedReader bR = new BufferedReader(new InputStreamReader(in));
        String line = bR.readLine();
        while (line != null) {
            levelString.append(line).append("\n");
            line = bR.readLine();
        }
        return load(levelString.toString());
    }
}
