/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.execution;

/**
 * Expand version of Timed Runable but with a Context.
 *
 * <p> This is the bridge between Runnable, Closure and TimerTask. The different
 * is it has two step paradigm instead of one!
 *
 * @author cuong.nguyenmanh2
 */
public class DelayedRunnableAction {

    protected float delayTime;
    protected Runnable start;
    protected Object callee;

    public DelayedRunnableAction(float delayTime, Runnable start) {
        this(delayTime, start, null);
    }

    public DelayedRunnableAction(float delayTime, Runnable start, Object callee) {
        this.delayTime = delayTime;
        this.start = start;
        this.callee = callee;
    }

    public float getDelayTime() {
        return delayTime;
    }

    public Runnable getStart() {
        return start;
    }

    public Object getCallee() {
        return callee;
    }
    
    
}
