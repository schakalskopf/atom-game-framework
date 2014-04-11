/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.function;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import java.util.ArrayList;
import sg.atom.managex.api.action.AtomEditorAction;

/**
 * Should be called Functional/Modal/Formal System instead.
 * <h4>FunctionalSytem</h4> FunctionalSytem drive the Application in formal way,
 * that the user's interactions captured and translated by a meaningful way or
 * ignored. It provide expected results or raising errors instead of giving
 * nothing back. You can see a FormalSystem work extractly like a giant
 * FiniteStateMachine. Common CCD programs implement this kind of system
 * intensively.
 *
 * <p>FunctionalSystem has an cursor/state indicate the current status, position
 * or intention, focus point of the whole system. Hence the "current" keywork
 * here support that meaning.
 *
 * @author hungcuong
 */
public abstract class FunctionSystem extends AbstractAppState {

    ArrayList<AtomFunction> functionList = new ArrayList<AtomFunction>();
    String currentMode;
    AtomEditorAction currentAction;
//    AtomEditorAction previousAction;
//    AtomEditorAction nextAction;
    private final SimpleApplication app;
    private EditorMode editorMode;

    public FunctionSystem(SimpleApplication app) {
        this.app = app;
    }

//    public AtomEditorAction getNextAction() {
//        return this.nextAction;
//    }
//
//    public void onAction(String name, boolean pressed, float tpf) {
//        if (currentAction.isCancelTrigger(name)) {
//            currentAction.actionCancel();
//        } else {
//            // action perform
//            currentAction.actionPerformed(name);
//        }
//        /*
//         if (name.equals("PressH")) {
//         System.out.println(name + " = " + pressed);
//         } else if (name.equals("PressEsc")) {
//         }
//         */
//    }
    public abstract void setupFunctions();

    public void addFunction(AtomFunction atomFunction) {
        functionList.add(atomFunction);
    }

    public ArrayList<AtomFunction> getFunctions() {
        return functionList;
    }

    public void switchMode(EditorMode editorMode) {
        this.editorMode = editorMode;
    }
}
