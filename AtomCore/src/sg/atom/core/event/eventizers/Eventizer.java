// Copyright © 2011-2012, Esko Luontola <www.orfjackal.net>
// This software is released under the Apache License 2.0.
// The license text is at http://www.apache.org/licenses/LICENSE-2.0

package sg.atom.core.event.eventizers;

import sg.atom.utils.event.Event;
import sg.atom.core.event.message.MessageSender;

/**
 * Converts method calls to event objects, and those event objects back to method calls.
 */
public interface Eventizer<T> {

    Class<T> getType();

    T newFrontend(MessageSender<Event<T>> target);

    MessageSender<Event<T>> newBackend(T target);
}
