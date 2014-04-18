// Copyright © 2011-2012, Esko Luontola <www.orfjackal.net>
// This software is released under the Apache License 2.0.
// The license text is at http://www.apache.org/licenses/LICENSE-2.0
package sg.atom.core.execution.workers;

import java.util.concurrent.Executor;
import sg.atom.utils.event.eventizers.EventizerProvider;
import sg.atom.core.exceptions.FailureHandler;
import sg.atom.utils.event.listeners.MessageListener;
import sg.atom.utils.event.message.MessageProcessor;
import sg.atom.utils.concurrent.ThreadSafe;

/**
 * Multi-threaded actors container for production use. Each {@link ActorThread}
 * will be backed by a thread from the {@link Executor} which is given to the
 * constructor of this class.
 */
@ThreadSafe
public class MultiThreadedWorkers extends Workers {

    private final Executor executor;

    public MultiThreadedWorkers(Executor executor, EventizerProvider eventizerProvider, FailureHandler failureHandler, MessageListener messageListener) {
        super(eventizerProvider, failureHandler, messageListener);
        this.executor = executor;
    }

    @Override
    void startActorThread(MessageProcessor actorThread) {
        executor.execute(new BlockingActorProcessor(actorThread));
    }

    @ThreadSafe
    private static class BlockingActorProcessor implements Runnable {

        private final MessageProcessor actorThread;

        public BlockingActorProcessor(MessageProcessor actorThread) {
            this.actorThread = actorThread;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    actorThread.processNextMessage();
                }
            } catch (InterruptedException e) {
                // actor was told to exit
            }
        }
    }
}
