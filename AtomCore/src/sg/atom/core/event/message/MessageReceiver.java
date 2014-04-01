// Copyright © 2011-2012, Esko Luontola <www.orfjackal.net>
// This software is released under the Apache License 2.0.
// The license text is at http://www.apache.org/licenses/LICENSE-2.0
package sg.atom.core.event.message;

public interface MessageReceiver<T> {

    public T take() throws InterruptedException;

    public T poll();
}
