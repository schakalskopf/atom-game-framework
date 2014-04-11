/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.memeto;

/**
 * Memeto is the way to store the past. This interface define a generic
 * (class-based) way to handler past storing for a kind of object.
 *
 * <p>This is the enhancement from Delta framework, which doesn't have concept
 * of Edit.
 *
 * <p>This expand Swing undo and redo framework with no generic support. This
 * implementation resemble the insfrastructure of git (but inmemory and with
 * only one user, multi thread).
 *
 * <p>Memeto has few basic types: (Objective) Snapshot memeto, Properties saving
 * memeto & extract delta (differences) memeto and (Functional) inverse function
 * memeto. The details will be explain in each sub interface.
 *
 * <p>Read: http://en.wikipedia.org/wiki/Memento_pattern
 * http://c2.com/cgi/wiki?FacadeMementoStrategy
 *
 * <p><b>How it works:</b> Memeto pattern force the edit reify its editing into
 * a piece of infos (a Command and a Memeto). The Command will be save to the
 * chain (or a tree like git branch). Memeto will be use to re-create
 *
 * @author cuong.nguyenmanh2
 */
public interface IMemeto<T> {

    public long getTimeStamp();

    public boolean isExpired();

    public Object getSavedInfo();
}
