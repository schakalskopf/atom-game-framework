/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.presentation;

import java.util.Iterator;
import java.util.Map;
import org.apache.commons.configuration.AbstractConfiguration;
import sg.atom.managex.api.system.EditorSystem;

/**
 * Perspective is a "view" configuration of editing beside of Mode. It drive UI
 * behaviours and user interactions of the {@link EditorSystem}.
 *
 * @author CuongNguyen
 */
public class Perspective extends AbstractConfiguration {

    /**
     * Map of components to default layouts
     */
    protected Map<String, String> defaultLayouts;
    /**
     * Map of components to current layout
     */
    protected Map<String, String> layouts;

    /**
     * Enable or disable this perspective. Then tell the {@link EditorSystem}
     * about it.
     *
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        //Tell the system about the change?
    }

    public void notifySystem() {
        //EventBus.()
    }

    /**
     * return to default layout
     */
    public void reset() {
    }

    /**
     * Apply current layout to all components
     */
    public void updateLayouts() {
    }

    //Configuration ------------------------------------------------------------
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
}
