/**
 *
 */
package speedbomber.model.world.map;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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

    private MapLoader(InputStream in) {
        this.width = 0;
        this.height = 0;
        parse(in);
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

    @Override
    public Object load(AssetInfo assetInfo) throws IOException {
        InputStream in = assetInfo.openStream();
        MapLoader mapLoader = new MapLoader(in);
        AbstractMap map = new AbstractMap(mapLoader.getObjects());

        return map;
    }

    private void parse(InputStream in) {
        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document;
        try {
            builder = builderFactory.newDocumentBuilder();
            document = builder.parse(in);
            parse(document);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void parse(Document doc) {
        NodeList player = doc.getElementsByTagName("player");
        NodeList map = doc.getElementsByTagName("mapdata");
        level = map.item(0).getChildNodes().item(1).getNodeValue();
    }
}
