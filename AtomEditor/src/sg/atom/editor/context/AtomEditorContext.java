/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.context;

import javolution.context.AbstractContext;
import sg.atom.managex.api.config.EditorConfiguration;
import sg.atom.managex.api.system.TransactionalEditingSystem;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class AtomEditorContext extends AbstractContext{

    EditorConfiguration config;

    @Override
    protected AbstractContext inner() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
