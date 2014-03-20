/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.memeto;

/**
 * Same as Swing UndoableEdit and StateEdit together.
 *
 * <p>A MemetoEdit is an edit action that contract to generate appropriate
 * memeto for the system. It has a characteristic, which is inversable. That
 * mean by envolve the resulted object with the memeto again in forward or
 * backward direction, it return original.
 * http://en.wikipedia.org/wiki/Inverse_function
 *
 * @author cuong.nguyenmanh2
 */
public interface IMemetoEdit<T> {

    public long getTimeStamp();
}
