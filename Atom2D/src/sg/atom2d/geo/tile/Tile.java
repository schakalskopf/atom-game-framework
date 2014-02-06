package sg.atom2d.geo.tile;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Vector2f;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author cuong.nguyenmanh2
 */
public class Tile implements Savable,Cloneable{

    Properties properties;
    public Vector2f topLeft;
    public Vector2f topRight;
    public Vector2f bottomRight;
    public Vector2f bottomLeft;

    public Tile() {
    }

    public Tile(Vector2f topLeft, Vector2f topRight, Vector2f bottomRight, Vector2f bottomLeft) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
    }

    public Properties getProperties() {
        return properties;
    }

    public void write(JmeExporter ex) throws IOException {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void read(JmeImporter im) throws IOException {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
}
