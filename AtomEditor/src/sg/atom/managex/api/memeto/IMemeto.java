/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.memeto;

/**
 * Memeto is the way to store the past. This interface define a generic
 * (class-based) way to handler past storing for a kind of object.
 *
 * <p>This expand Swing undo and redo framework with no generic support. Memeto
 * has few basic types: Properties saving memeto, extract delta (differences)
 * memeto and inversefunction memeto. The details will be explain in each sub
 * interface.
 *
 * @author cuong.nguyenmanh2
 */
public interface IMemeto<T> {

    public long getTimeStamp();
}
