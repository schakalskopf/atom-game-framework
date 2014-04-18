package sg.atom.utils.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import sg.atom.utils._beta.aop.AppliesTo;

/**
 * Callable context introduce a calling methods or execution within a name
 * space, context with named elements.
 *
 * <p>Implementation
 * 
 * <p>FIXME: extends SemanticContext or ScriptingContext?
 *
 * @author cuong.nguyenmanh2
 */
public abstract class CallableContext {

    private Callable<Object> layer(final Callable<?> next, final Object obj, final Method layerMethod, final Object[] args) {
        return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                ArrayList<Object> newArgs = new ArrayList<Object>(Arrays.asList(args == null ? new Object[0] : args));
                newArgs.add(0, obj);
                newArgs.add(0, next);
                return layerMethod.invoke(CallableContext.this, newArgs.toArray());
            }
        };
    }

    Callable<Object> chain(final Object obj, final Method method, final Object[] args, Callable<Object> next) {
        
        // Just invoke the method with appropriate signature
        for (Method layerMethod : getClass().getDeclaredMethods()) {
            AppliesTo at = layerMethod.getAnnotation(AppliesTo.class);
            
            
            for (Class<?> intf : obj.getClass().getInterfaces()) {
                
                
                if (at != null && at.value().equals(intf) && method.getName().equals(layerMethod.getName())) {
                    ArrayList<Class<?>> mParameterTypes = new ArrayList<Class<?>>(Arrays.asList(layerMethod.getParameterTypes()));
                    
                    
                    if (Callable.class.equals(mParameterTypes.remove(0))
                            && at.value().equals(mParameterTypes.remove(0))
                            && mParameterTypes.equals(Arrays.asList(method.getParameterTypes()))) {
                        
                        //Callable evaluation?
                        return layer(next, obj, layerMethod, args);
                    }
                }
            }
        }
        return next;
    }
}
