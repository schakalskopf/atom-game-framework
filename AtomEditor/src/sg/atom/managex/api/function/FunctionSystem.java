/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.function;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.input.controls.ActionListener;
import java.util.ArrayList;
import sg.atom.managex.api.action.AtomEditorAction;

/**
 *
 * @author hungcuong
 */
public class FunctionSystem extends AbstractAppState implements ActionListener {

    ArrayList<AtomFunction> functionList = new ArrayList<AtomFunction>();
    String mode;
    AtomEditorAction currentAction;
    AtomEditorAction previousAction;
    AtomEditorAction nextAction;
    private final SimpleApplication app;

    public FunctionSystem(SimpleApplication app) {
        this.app = app;
    }

    public AtomEditorAction getNextAction() {
        return this.nextAction;
    }

    public void onAction(String name, boolean pressed, float tpf) {
        if (currentAction.isCancelTrigger(name)) {
            currentAction.actionCancel();
        } else {
            // action perform
            currentAction.actionPerformed(name);
        }
        /*
         if (name.equals("PressH")) {
         System.out.println(name + " = " + pressed);
         } else if (name.equals("PressEsc")) {
         }
         */
    }

    public void addFunction(AtomFunction atomFunction) {
        functionList.add(atomFunction);
    }

    public ArrayList<AtomFunction> getFunctions() {
        return functionList;
    }
}
