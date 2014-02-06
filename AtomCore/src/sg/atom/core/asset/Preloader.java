package sg.atom.core.asset;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.atom.utils.monitor.IProgress;
import sg.atom.utils.monitor.NullProgress;
import sg.atom.utils.monitor.Progress;

/**
 * 등록된 IPreload들을 차례대로 수행한다. start(long) 호출시에는 background loading을 수행하고, join해야
 * 한다.
 * <code>
 * add(IPreload);
 * start();
 * ...
 * join();
 * </code>
 *
 * @author mulova
 *
 */
public final class Preloader implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Preloader.class);
    String name;
    Thread thread;
    //IErrorHandler errorHandler;
    IPreload current;
    float currentContribution;
    final ArrayList<IPreload> pool;
    final ArrayList<Float> progressContribution;
    long yieldTime;
    final List<Preloader> prerequisites = new LinkedList<Preloader>();
    IProgress progress = new NullProgress();
    int priority = Thread.NORM_PRIORITY;

    public Preloader(String name) {
        this.name = name;
        this.pool = new ArrayList<IPreload>();
        this.progressContribution = new ArrayList<Float>();
    }

    public void add(IPreload loader, float rate) {
        if (loader == null) {
            return;
        }
        pool.add(loader);
        progressContribution.add(rate);
    }

    /**
     * reentrant
     */
    public void start() {
        if (this.thread != null && this.thread.isAlive()) {
            return;
        }
        this.thread = new Thread(this);
        this.thread.setName(name);
        this.thread.setPriority(priority);
        this.thread.start();
    }

    public void setPriority(int priority) {
        this.priority = priority;
        if (thread != null) {
            thread.setPriority(priority);
        }
    }

    public void interrupt() {
        if (this.thread != null) {
            this.thread.interrupt();
        }
    }

    public boolean isAlive() {
        return thread.isAlive();
    }

    /**
     * @param pre 이전에 마무리 되어야 하는 preloader
     */
    public void addPrerequisite(Preloader pre) {
        this.prerequisites.add(pre);
    }

    public void join() {
        try {
            if (this.thread != null) {
                this.thread.join();
            } else {
                start();
                this.thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /*
     public void setErrorHandler(IErrorHandler handler) {
     errorHandler = handler;
     }
     */

    public void setProgress(IProgress progress) {
        if (progress != null) {
            this.progress = progress;
        } else {
            this.progress = new NullProgress();
        }
        if (current != null) {
            current.setProgress(progress);
            if (progress instanceof Progress) {
                ((Progress) progress).setEnd(currentContribution);
            }
        }
    }

    @Override
    public void run() {
        for (Preloader p : prerequisites) {
            p.join();
        }
        for (int i = 0; i < pool.size(); i++) {
            try {
                current = pool.get(i);
                currentContribution = progressContribution.get(i);
                log.info(current.getName(), " loading start...");
                float time = System.currentTimeMillis();
                setProgress(progress);
                current.preload();
                log.info("{} loading ends. {} msec", current.getName(), System.currentTimeMillis() - time);
                if (Thread.interrupted()) {
                    break;
                }
            } catch (Throwable t) {
                /*
                 if (errorHandler != null) {
                 errorHandler.handleError(t);
                 }
                 */
                log.warn("Preloading fails", t);
            }
        }
        thread = null;
    }

    @Override
    public String toString() {
        return name;
    }
}