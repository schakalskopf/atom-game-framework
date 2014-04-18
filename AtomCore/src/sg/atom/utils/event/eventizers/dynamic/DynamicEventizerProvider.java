/**
 * // Copyright © 2011-2012, Esko Luontola <www.orfjackal.net> // This software
 * is released under the Apache License 2.0. // The license text is at
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package sg.atom.utils.event.eventizers.dynamic;

import java.util.concurrent.ConcurrentHashMap;
import sg.atom.utils.event.eventizers.Eventizer;
import sg.atom.utils.event.eventizers.EventizerProvider;
import sg.atom.utils.concurrent.Immutable;

/**
 * Supports all actor interfaces using reflection.
 */
@Immutable
public class DynamicEventizerProvider implements EventizerProvider {

    private final ConcurrentHashMap<Class<?>, Eventizer<?>> cache = new ConcurrentHashMap<Class<?>, Eventizer<?>>();

    @SuppressWarnings("unchecked")
    @Override
    public <T> Eventizer<T> getEventizerForType(Class<T> type) {
        Eventizer<T> eventizer = (Eventizer<T>) cache.get(type);
        if (eventizer == null) {
            eventizer = new DynamicEventizer<T>(type);
            cache.put(type, eventizer);
        }
        return eventizer;
    }
}
