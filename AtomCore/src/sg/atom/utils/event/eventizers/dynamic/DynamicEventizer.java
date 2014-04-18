/**
 * // Copyright © 2011-2012, Esko Luontola <www.orfjackal.net> // This software
 * is released under the Apache License 2.0. // The license text is at
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package sg.atom.utils.event.eventizers.dynamic;

import sg.atom.utils.event.message.MessageSender;

import java.lang.reflect.Proxy;
import sg.atom.utils.event.Event;
import sg.atom.utils.event.eventizers.Eventizer;
import sg.atom.utils.event.eventizers.Eventizers;
import sg.atom.utils.concurrent.Immutable;

/**
 * Supports any actor interface using reflection.
 */
@Immutable
public class DynamicEventizer<T> implements Eventizer<T> {

    private final Class<T> type;

    public DynamicEventizer(Class<T> type) {
        Eventizers.validateActorInterface(type);
        this.type = type;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public T newFrontend(MessageSender<Event<T>> target) {
        return type.cast(Proxy.newProxyInstance(
                type.getClassLoader(),
                new Class<?>[]{type},
                new DynamicListenerToEvent<T>(target)));
    }

    @Override
    public MessageSender<Event<T>> newBackend(T target) {
        return new EventToDynamicListener<T>(target);
    }
}
