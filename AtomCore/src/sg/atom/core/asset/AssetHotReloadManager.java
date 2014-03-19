/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.asset;

import com.jme3.app.state.AbstractAppState;

/**
 * An AppState automaticly reload assets when it out of date.
 *
 * <p>This is a different AssetManager that Atom framework provide. This kind of
 * JVM and the HotSpotJVM compare to each other, one for runtime, one better for
 * development time.
 *
 * <p>This class hook into Ant task structure, and only work for standard JME
 * SDK build workflow! So use it with care and only if you know what it does!
 *
 * @author atomix
 */
public class AssetHotReloadManager extends AbstractAppState {
}
