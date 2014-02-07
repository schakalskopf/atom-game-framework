/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.function;

import sg.atom.managex.api.action.AtomEditorAction;
import java.util.LinkedList;
import java.util.List;

/**
 * Description of an Action. Different from AWT implemenatation!
 * 
 * @author cuong.nguyenmanh2
 */
public class AtomFunction {

    LinkedList<AtomEditorAction> actionList = new LinkedList<AtomEditorAction>();
    String name;
    String description;
    String icon;
    String scope;
    String type;
    boolean enable = true;

    public AtomFunction() {
    }

    public AtomFunction(String name) {
        this.name = name;
    }

    public AtomFunction(String name, String description, String icon) {
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public List<AtomEditorAction> getActionList() {
        return actionList;
    }

    public void setActionList(LinkedList<AtomEditorAction> actionList) {
        this.actionList = actionList;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }
}
