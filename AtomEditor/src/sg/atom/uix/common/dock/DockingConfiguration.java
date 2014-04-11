/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.uix.common.dock;

import java.io.File;
import java.util.Iterator;
import org.apache.commons.configuration.AbstractConfiguration;

/**
 *
 * @author CuongNguyen
 */
public class DockingConfiguration extends AbstractConfiguration {

    @Override
    protected void addPropertyDirect(String key, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean containsKey(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getProperty(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Iterator<String> getKeys() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void save(File file) {
    }
}
