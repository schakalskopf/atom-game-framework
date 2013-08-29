/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.managex.action;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class AtomFunction {

    LinkedList<AtomAction> actionList = new LinkedList<AtomAction>();
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

    public List<AtomAction> getActionList() {
        return actionList;
    }

    public void setActionList(LinkedList<AtomAction> actionList) {
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
