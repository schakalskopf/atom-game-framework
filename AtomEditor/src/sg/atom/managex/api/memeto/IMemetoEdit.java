/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.memeto;

/**
 * Same as Swing UndoableEdit and StateEdit together.
 *
 * <p>A MemetoEdit is an edit action that contract to generate appropriate
 * memeto for the system. MemetoEdit is also contracted to know how to undoes a
 * single step, which it cause to the model with the info it did generate in
 * that version of model at the time of editing happen.
 *
 * @author cuong.nguyenmanh2
 */
public interface IMemetoEdit<T> {

    public long getTimeStamp();

    public IMemeto<T> generateMemeto(T object);

    public T getOriginal(T currentVersion, IMemeto<T> memeto);

    public T getOriginal(IMemeto<T> memeto);
}
