package sg.atom.utils.proxy.presentation.pface;

import sg.atom.utils.proxy.delegation.MethodDelegate;
import java.lang.reflect.Proxy;

/**
 * PFace <p> watching <a href="http://bit.ly/5gWWXM">the Go talk at google
 * IO</a>, it seems one of the biggest advantages of Go (and other
 * loosely/dynamically typed languages) is being able to retrospectively treat a
 * class as if it implements a given signature without altering the source
 * class. </p> 
 * 
 * <p> Being a proponent of static typed languages, it is still
 * preferable to use explicit interface implementations where possible.
 * (Otherwise what happens when someone changes a previously inferred type? Or
 * two types are similar but not identical? Where are my compiler warnings?)
 * </p> <p> However, for less verbose programming convenience, you can use
 * {@code PFace} to treat disparate classes as if they implement an interface:
 *
 * <pre>
 *
 * </pre> </p>
 */
public enum PFace {

    ;//

	/**
	 * This is the main entry point. Given an interface class, a proxy will be returned which delegates method calls to
	 * the underlying implementation
	 * 
	 * @param <T>
	 * @param interfaceName
	 * @param objectToCast
	 * @return a new proxy for the given interface and object
	 */
	@SuppressWarnings("unchecked")
    public static <T> T as(final Class<T> interfaceName, final Object objectToCast) {
        // if the object already implements the interface, then there's no need for
        // having to proxy it!
        if (objectToCast.getClass().isInstance(interfaceName)) {
            return (T) objectToCast;
        }

        final MethodDelegate handler = MethodDelegate.wrap(interfaceName, objectToCast);
        final Class<?>[] interfaces = new Class<?>[]{interfaceName};

        return (T) Proxy.newProxyInstance(interfaceName.getClassLoader(), interfaces, handler);
    }
}
