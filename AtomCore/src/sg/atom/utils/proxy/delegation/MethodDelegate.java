/**
 *
 */
package sg.atom.utils.proxy.delegation;

import sg.atom.utils.proxy.presentation.pface.MethodMatchers;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import sg.atom.utils._commons.reflect.MethodKey;
import sg.atom.utils.proxy.presentation.pface.MethodMatchers;

/**
 * A {@code MethodDelegate} is used to wrap an object which will be forwarded on
 * method calls from a given interface
 */
public class MethodDelegate implements InvocationHandler {

    private final Map<MethodKey, Method> methodByKey;
    private final Object implementation;

    private MethodDelegate(final Map<MethodKey, Method> mbk, final Object impl) {
        this.methodByKey = mbk;
        this.implementation = impl;
    }

    public static MethodDelegate wrap(final Class<?> c1ass, final Object implementation) {
        final Map<MethodKey, Method> methodByKey = new HashMap<MethodKey, Method>();

        final Method[] allMethods = c1ass.getMethods();
        for (final Method m : allMethods) {
            final Method impl = MethodMatchers.matchMethod(m, implementation);
            methodByKey.put(new MethodKey(m), impl);
        }

        return new MethodDelegate(methodByKey, implementation);
    }

    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final MethodKey key = new MethodKey(method);
        final Method impl = this.methodByKey.get(key);
        if (impl != null) {
            final Object result = impl.invoke(this.implementation, args);
            return result;
        }
        throw new IllegalStateException(String.format("%s does has no implementation for method %s",
                this.implementation, method.getName()));
    }
}