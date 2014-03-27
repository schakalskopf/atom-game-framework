// Copyright © 2011-2012, Esko Luontola <www.orfjackal.net>
// This software is released under the Apache License 2.0.
// The license text is at http://www.apache.org/licenses/LICENSE-2.0

package sg.atom.core.event.eventizers.dynamic;

import sg.atom.core.event.eventizers.Event;
import sg.atom.core.event.queue.MessageSender;

import sg.atom.utils.concurrent.ThreadSafe;

@ThreadSafe
public class EventToDynamicListener<T> implements MessageSender<Event<T>> {

    private final T target;

    public EventToDynamicListener(T target) {
        this.target = target;
    }

    @Override
    public void send(Event<T> message) {
        message.fireOn(target);
    }
}
