package sg.atom2d.geo.tile;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Vector2f;
import java.io.IOException;
import java.util.Properties;

/**
 * A simple version of Tile which work in quite amount of cases.
 *
 * @author cuong.nguyenmanh2
 */
public class Tile2D implements Tilable, Savable {

    Properties properties;
    public Vector2f topLeft;
    public Vector2f topRight;
    public Vector2f bottomRight;
    public Vector2f bottomLeft;

    public Tile2D() {
    }

    public Tile2D(Vector2f topLeft, Vector2f topRight, Vector2f bottomRight, Vector2f bottomLeft) {
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

    public Object getPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getRelative(Object otherPosition) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setRelative(Tilable otherTile, Object otherPosition) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void align(Object position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
