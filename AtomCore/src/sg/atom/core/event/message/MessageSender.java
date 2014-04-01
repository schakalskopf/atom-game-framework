// Copyright © 2011-2012, Esko Luontola <www.orfjackal.net>
// This software is released under the Apache License 2.0.
// The license text is at http://www.apache.org/licenses/LICENSE-2.0

package sg.atom.core.event.message;

/**
 * FIXME: Replace with EventBus producer.
 * @author CuongNguyen
 * @param <T> 
 */
public interface MessageSender<T> {

    public void send(T message);
}
