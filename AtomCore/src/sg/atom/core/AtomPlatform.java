/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core;

import com.jme3.app.Application;
import org.qi4j.api.activation.ActivationEventListener;
import org.qi4j.api.activation.ActivationException;
import org.qi4j.api.activation.PassivationException;
import org.qi4j.api.structure.ApplicationDescriptor;
import org.qi4j.api.structure.Layer;
import org.qi4j.api.structure.Module;

/**
 * An implementation of Application who get deeper into low level. It is an
 * Application which redefined the tasks, the states and the game cycles of JME3
 * context! Use Guava and Guice natively.
 *
 * <p>FIXME: [Experimental] This design required <b>Qi4j</b> and build a
 * platform with composite architecture!!
 *
 * <p><b>Note</b>To go deeper, in fact to everything from the background up, use
 * NanoMain.</p>
 *
 * @author cuong.nguyenmanh2
 */
public class AtomPlatform extends Application implements org.qi4j.api.structure.Application {

    @Override
    public String name() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String version() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mode mode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Layer findLayer(String string) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Module findModule(String string, String string1) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ApplicationDescriptor descriptor() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void registerActivationEventListener(ActivationEventListener al) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deregisterActivationEventListener(ActivationEventListener al) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void activate() throws ActivationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void passivate() throws PassivationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T metaInfo(Class<T> type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
