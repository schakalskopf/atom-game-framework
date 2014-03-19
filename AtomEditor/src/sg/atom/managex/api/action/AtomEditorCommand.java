/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.action;

/**
 * Command is a transferable medium of a Action. Which can be sent from one to
 * another attendent in the system.
 *
 * <p>Command is a kind of Event but specific for Action communicating and
 * messaging. Concept borrowed from Apache Chain.
 *
 * @author cuong.nguyenmanh2
 */
public class AtomEditorCommand {

    private final AtomEditorAction parent;

    public AtomEditorCommand(AtomEditorAction parent) {
        this.parent = parent;
    }
}
