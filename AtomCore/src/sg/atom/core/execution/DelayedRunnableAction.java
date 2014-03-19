/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.execution;

import groovy.lang.Closure;

/**
 * Expand version of Timed Runable but with a Context.
 *
 * <p> This is the bridge between Runnable, Closure and TimerTask. The different
 * is it has two step paradigm instead of one!
 *
 * @author cuong.nguyenmanh2
 */
public class DelayedRunnableAction {

    float delayTime;
    Runnable start;
    Runnable end;
    Object callee;

    public DelayedRunnableAction(float delayTime, Closure start, Closure end, Object callee) {
        this.delayTime = delayTime;
        this.start = start;
        this.end = end;
        this.callee = callee;
    }

    public DelayedRunnableAction(float delayTime, Runnable start, Runnable end) {
        this.delayTime = delayTime;
        this.start = start;
        this.end = end;
    }
}
