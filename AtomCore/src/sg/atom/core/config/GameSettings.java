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
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.ConfigurationException;

/**
 * Common implementation.
 *
 * @author atomix
 */
public class GameSettings implements Cloneable, Savable, AtomConfiguration {

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

    @Override
    public AbstractConfiguration getConfiguration() throws ConfigurationException {
        return new AbstractFileConfiguration("") {
            @Override
            public void load(Reader in) throws ConfigurationException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void save(Writer out) throws ConfigurationException {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    @Override
    public void apply() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
