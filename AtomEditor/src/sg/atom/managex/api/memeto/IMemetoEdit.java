/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.memeto;

import sg.atom.managex.api.action.AtomEditorCommand;

/**
 * Same as Swing UndoableEdit and StateEdit together. Play CareTaker in Memeto
 * pattern upon object of class T. Mixed between Command and Memeto pattern, so
 * called Facade Stragegey Memeto pattern.
 *
 * <p>A MemetoEdit is an edit action that contract to generate appropriate
 * memeto for the system. MemetoEdit is also contracted to know how to undoes a
 * single step, which it cause to the model with the info it did generate in
 * that version of model at the time of editing happen.
 *
 * <p>After finishing editing, a Memeto edit turn its self into a Command Read:
 * http://c2.com/cgi/wiki?CommandPattern
 *
 * @author cuong.nguyenmanh2
 */
public interface IMemetoEdit<T> {

    public long getTimeStamp();

    public T getOriginal(T currentVersion, IMemeto<T> memeto);

    public T getOriginal(IMemeto<T> memeto);

    public AtomEditorCommand finish();
}
