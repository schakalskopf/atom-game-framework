/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package sg.atom.core.config;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Common implementation
 *
 * @author atomix
 */
public class GameSettings implements Cloneable, Savable {

    public void load(Properties props) {
    }

    public void load(InputStream stream) {
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
    }

    @Override
    public void read(JmeImporter im) throws IOException {
    }
}
