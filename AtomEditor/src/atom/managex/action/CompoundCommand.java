/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.managex.action;

import java.util.ArrayList;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class CompoundCommand extends AtomCommand{

    public ArrayList<AtomCommand> getCommands() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public CompoundCommand(AtomEditorAction parent) {
        super(parent);
    }
    
}
