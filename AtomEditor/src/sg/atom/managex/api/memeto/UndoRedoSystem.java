/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.memeto;

import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import sg.atom.managex.api.action.AtomEditorAction;
import sg.atom.managex.api.system.TransactionalEditingSystem;

/**
 * UndoRedoSystem - so call TransactionalEditingSystem, handle the undo redo
 * changes upon chains of IMemetoEdit.
 *
 * <p>How it work: assume that there is a list of "Model of class T", each Model
 * instance registed with. Then, for that specific model, the chain of
 * IMemetoEdits are recorded. A single edit is mapped to its generated memeto at
 * the time. Later, user want to redo or undo that model, they just need to call
 * the appropriate methods.
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
 * another system in another thread. So the multi-thread scenario is made up!
 *
 * <p>To solve this problem, in this implementation use a Timestamp base
 * concurrent control - a kind of MVCC data structure to handle changes stamps
 * in highly concurent enviroment.
 * http://en.wikipedia.org/wiki/Timestamp-based_concurrency_control Edit
 * actually doesn't directly make changes to content but an active area of
 * content. The view of the content is then gathered and appear to user in
 * appropriate way. All model object marked with "delta-MultiVersioned" will be
 * treated this way.
 *
 * <p>Another optimization is the process of updating can be lazy if model
 * object marked with 'delta-incremental".
 *
 * <p>In this version, .In next version, this going to use Atom's Repository for
 * the design consistent purpose!
 *
 * @author hungcuong
 */
public class UndoRedoSystem implements TransactionalEditingSystem {

    ArrayList<IMemetoEdit> edits;

    public void undo(AtomEditorAction action) {
        //action.getCommand().undo();
    }

    public void undo(IMemetoEdit edit){
        
    }
    public void redo() {
    }

    public void traceBack(int level) {
    }

    @Subscribe
    public void reportEdit(IMemetoEdit edit) {
        edits.add(edit);
    }
}
