/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.managex.action;

/**
 * Command is a transferable medium of a Action. Which can be sent from one to
 * another attedent in the system.
 *
 * Command is a kind of Event but specific for Action communicating and
 * messaging.
 *
 * @author cuong.nguyenmanh2
 */
public class AtomCommand {

    private final AtomEditorAction parent;

    public AtomCommand(AtomEditorAction parent) {
        this.parent = parent;
    }
}
