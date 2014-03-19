/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.execution;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater;
import sg.atom.utils.collection.AtomicArray;

/**
 * LoadBalancerExecutor try to bring load balance and pioritized tasks to JME
 * enviroment. A simple load-balancing executor. If no delegate executors are
 * defined, then tasks are rejected. Executors are chosen in a round-robin
 * fashion. Can config to Use ratelimiter under.
 *
 * Inspired by : package org.jboss.threads;
 *
 * @author cuong.nguyenmanh2
 */
public class LoadBalancingExecutor implements Executor {

    private volatile Executor[] executors = null;
    private final AtomicInteger seq = new AtomicInteger();
    private final Lock writeLock = new ReentrantLock();
    private static final AtomicArray<LoadBalancingExecutor, Executor> executorsUpdater = AtomicArray.create(newUpdater(LoadBalancingExecutor.class, Executor[].class, "executors"), new AtomicArray.Creator<Executor>() {
        public Executor[] create(final int len) {
            return new Executor[len];
        }
    });

    /**
     * Construct a new instance.
     */
    public LoadBalancingExecutor() {
        executorsUpdater.clear(this);
    }

    /**
     * Construct a new instance.
     *
     * @param executors the initial list of executors to delegate to
     */
    public LoadBalancingExecutor(Executor... executors) {
        if (executors != null && executors.length > 0) {
            final Executor[] clone = executors.clone();
            for (int i = 0; i < clone.length; i++) {
                if (clone[i] == null) {
                    throw new NullPointerException("executor at index " + i + " is null");
                }
            }
            executorsUpdater.set(this, clone);
        } else {
            executorsUpdater.clear(this);
        }
    }

    /**
     * Execute a task.
     *
     * @param command the task to execute
     * @throws RejectedExecutionException if no executors are available to run
     * the task
     */
    public void execute(final Runnable command) throws RejectedExecutionException {
        final Executor[] executors = this.executors;
        final int len = executors.length;
        if (len == 0) {
            throw new RejectedExecutionException("No executors available to run task");
        }
        executors[seq.getAndIncrement() % len].execute(command);
    }

    /**
     * Clear out all delegate executors at once. Tasks will be rejected until
     * another delegate executor is added.
     */
    public void clear() {
        executorsUpdater.clear(this);
    }

    /**
     * Add a delegate executor.
     *
     * @param executor the executor to add
     */
    public void addExecutor(final Executor executor) {
        if (executor == null) {
            throw new NullPointerException("executor is null");
        }
        final Lock lock = writeLock;
        lock.lock();
        try {
            executorsUpdater.add(this, executor);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Remove a delegate executor.
     *
     * @param executor the executor to remove
     */
    public void removeExecutor(final Executor executor) {
        if (executor == null) {
            return;
        }
        final Lock lock = writeLock;
        lock.lock();
        try {
            executorsUpdater.remove(this, executor, true);
        } finally {
            lock.unlock();
        }
    }
}
