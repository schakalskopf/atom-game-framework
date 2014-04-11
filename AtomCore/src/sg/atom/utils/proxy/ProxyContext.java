package sg.atom.utils.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

/**
 * This context will leverage all its contents and attendants via Proxy
 * goodness.
 *
 * @author cuong.nguyenmanh2
 */
public final class ProxyContext {

    private static ThreadLocal<List<CallableContext>> activeLayers = new ThreadLocal<List<CallableContext>>() {
        @Override
        protected List<CallableContext> initialValue() {
            return new LinkedList<CallableContext>();
        }
    };

    public static Executor with(final CallableContext... layers) {
        return new LayerExecutor(layers);
    }

    @SuppressWarnings("unchecked")
    public static <T> T wrap(final T object) {
        return (T) Proxy.newProxyInstance(
                object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
                        Callable<Object> top = new Callable<Object>() {
                            @Override
                            public Object call() throws Exception {
                                return method.invoke(object, args);
                            }
                        };
                        for (CallableContext layer : activeLayers.get()) {
                            top = layer.chain(object, method, args, top);
                        }
                        return top.call();
                    }
                });
    }

    private static class LayerExecutor implements Executor {

        final CallableContext[] layers;

        public LayerExecutor(CallableContext[] layers) {
            this.layers = layers;
        }

        @Override
        public void execute(Runnable block) {
            List<CallableContext> oldLayers = new LinkedList<CallableContext>(activeLayers.get());
            try {

                activeLayers.get().addAll(Arrays.asList(layers));
                block.run();
            } finally {
                activeLayers.set(oldLayers);
            }
        }
    }
}
