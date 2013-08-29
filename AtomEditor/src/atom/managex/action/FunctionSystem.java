/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.managex.action;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.input.controls.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author hungcuong
 */
public class FunctionSystem extends AbstractAppState implements ActionListener {

    ArrayList<AtomFunction> functionList = new ArrayList<AtomFunction>();
    String mode;
    AtomAction currentAction;
    AtomAction previousAction;
    AtomAction nextAction;
    private final SimpleApplication app;

    public FunctionSystem(SimpleApplication app) {
        this.app = app;
    }

    public AtomAction getNextAction() {
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
