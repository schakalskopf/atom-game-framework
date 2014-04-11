package sg.atom.core;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.ServiceLoader;
import sg.atom.utils.factory.IAtomFactory;

/**
 * Contains version info and various global variables. This is a most solid
 * "singleton" by design in Atom framework. This singleton in turn maximize the
 * use of design pattern. By default, this global has to be created by Guice!
 *
 * <p>It contains the main registry and global context lookup to looking for
 * property, constant, services and other singleton if any.
 *
 * <p>By default it will have a "strong" link to all first-class level of JME:
 * InputManager, AssetManager, StateManager, RenderManager.. ; and Atom:
 * AtomMain, Guice, EventBus StageManager, GameStateManager, WorldManager,
 * GUIManager, SoundManager...
 *
 * <p>In charge for global localization (beside of GUI).
 *
 * <p>Smart interpreter, mediator used to create, search "something" from some
 * known pattern (Based in Apache Common Discorvery). The main application is to
 * search class in scope of the JVM. Note that this class performance is not
 * efficient to use in real-time. Say you try to query class structure every
 * frame. You should find your service and save the link. Or do the opposite by
 * inject the service with configuration.
 *
 * @author normenhansen, atomix
 */
public final class Globals implements IAtomFactory<Object>, Iterable<AbstractManager> {

//     public static final String VERSION = "v0.1";
//     public static final String DEFAULT_SERVER = "192.168.1.24";
//     public static final String DEFAULT_SERVER = "127.0.0.1";
//     public static final String DEFAULT_SERVER = "jmonkeyengine.com";
//     public static final String DEFAULT_SERVER = "128.238.56.114";
//     public static final int DEFAULT_PORT_TCP = 6143;
//     public static final int DEFAULT_PORT_UDP = 6143;
//     public static int PROTOCOL_VERSION = 1;
//     public static int CLIENT_VERSION = 1;
//     public static int SERVER_VERSION = 1;
//     public static float NETWORK_SYNC_FREQUENCY = 0.25f;
//     public static float NETWORK_MAX_PHYSICS_DELAY = 0.25f;
//     public static int SCENE_FPS = 60;
//     public static float PHYSICS_FPS = 1f / 30f;
//     //only applies for client, server doesnt render anyway
//     public static boolean PHYSICS_THREADED = true;
//     public static boolean PHYSICS_DEBUG = false;
    @Override
    public Object create(Object param) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object create(Object... params) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object cloneObject(Object orginal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator<AbstractManager> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ServiceLoader getGlobalServiceLoader() {
        return null;
    }

    public ClassLoader getGlobalClassLoader() {
        return this.getClass().getClassLoader();
    }

    public static Object getLookup() {
        return null;
    }

    public static Object getRegisteredInstance(Class clazz, Annotation pattern) {
        return null;
    }

    public static void register(Class clazz, Object instance) {
    }

    public ResourceBundle getResourceBundle(Object key) {
        return null;

    }

    @Override
    public Object get() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
