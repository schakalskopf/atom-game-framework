// Copyright © 2011-2012, Esko Luontola <www.orfjackal.net>
// This software is released under the Apache License 2.0.
// The license text is at http://www.apache.org/licenses/LICENSE-2.0
package sg.atom.core.event.eventizers.dynamic;

import java.lang.reflect.*;
import sg.atom.core.event.eventizers.Event;
import sg.atom.core.event.queue.MessageSender;
import sg.atom.utils.concurrent.ThreadSafe;

@ThreadSafe
public class DynamicListenerToEvent<T> implements InvocationHandler {

    private final MessageSender<Event<T>> target;

    public DynamicListenerToEvent(MessageSender<Event<T>> target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass().equals(Object.class)) {
            return method.invoke(this, args);
        }
        target.send(new DynamicEvent<T>(method, args));
        return null;
    }
}
