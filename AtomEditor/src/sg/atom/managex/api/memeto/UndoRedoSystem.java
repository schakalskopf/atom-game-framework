/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.memeto;

import java.util.ArrayList;
import sg.atom.managex.api.action.AtomEditorAction;

/**
 * UndoRedoSystem - so call Transactio System, handle the undo redo changes upon
 * chains of IMemetoEdit.
 *
 * <p>How it work: assume that there is a list of "Model of class T", each Model
 * instance registed with. Then, for that specific model, the chain of
 * IMemetoEdits are recorded. Later, user want to redo or undo that model, they
 * just need to call the appropriate methods.
 *
 * <p>This implementation use EventBus as the messaging medium to record changes
 * by default. The simple property change listener also supported but not
 * recommended.
 *
 * <p><b>Note:</b>Other CCD used to have troubles with their undo and redo
 * system. Because every action is recoreded with this system, cause this system
 * to become the black hole of failure, which is not a good design scenario!
 * Best practices show that this System should only record lightweight changes,
 * heavy changes (usually refered as signification changes) are transfered to
 * another system in another thread. So the multi-thread scenario is made up, in
 * this implementation use a Timestamp base concurrent control - a kind of MVCC
 * data structure to handle changes stamps in highly concurent enviroment.
 * http://en.wikipedia.org/wiki/Timestamp-based_concurrency_control
 *
 * <p>In this version, .In next version, this going to use Atom's Repository for the design
 * consistent purpose! 
 *
 *
 *
 * @author hungcuong
 */
public class UndoRedoSystem {

    ArrayList<IMemetoEdit> actions;

    public void undo(AtomEditorAction action) {
        //action.getCommand().undo();
    }

    public void redo() {
    }

    public void traceBack(int level) {
    }
}
