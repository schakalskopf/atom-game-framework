/*
 *    Copyright 2008 Tim Jansen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package sg.atom.core.execution.workers;

import sg.atom.utils.concurrent.ThreadState;
import sg.atom.core.actor.controller.Controller;
import sg.atom.core.actor.internal.ActorState;
import sg.atom.core.actor.internal.KeepRunningInterface;
import sg.atom.utils._commons.MultiThreadLogger;

/**
 * Worker is the Runnable thread that is managed by
 * {@link ControllerImplementation} to execute messages.
 * 
 * <p>Context bound (thread) progress
 */
public final class Worker implements Runnable {

    private final static MultiThreadLogger log = new MultiThreadLogger(Worker.class);
    private final Controller controller;
    private final KeepRunningInterface keepRunning;

    /**
     * Creates a new instance.
     *
     * @param controller the Controller to use
     * @param keepRunning the kill interface
     */
    public Worker(Controller controller, KeepRunningInterface keepRunning) {
        this.controller = controller;
        this.keepRunning = keepRunning;
    }

    /**
     * Keeps processing messages until there are no messages left.
     */
    public void run() {
        ThreadState ts = ThreadState.get();
        ts.initWorker(controller);
        try {
            controller.getActorLock().lock();
            try {
                while (keepRunning.shouldContinue()) {
                    ActorState a = controller.getNextFromQueueUnsynchronized();
                    if (a == null) {
                        break;
                    }
                    int executed = a.executeAllQueuedMessagesUnsynchronized(ts, keepRunning);
                    if (executed == 0) {
                        log.error("Warning: actor state on queue had no messages!");
                    }
                }
            } catch (InterruptedException e) {
                log.info("Got interrupted exception in worker thread:");
                e.printStackTrace();
            } finally {
                controller.getActorLock().unlock();
            }
        } catch (RuntimeException e) {
            log.error("Got unexpected exception in worker thread:");
            e.printStackTrace();
            throw e;
        } finally {
            ts.uninitWorker();
        }
    }
}
