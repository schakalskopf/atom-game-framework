/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.system;

import com.jme3.app.SimpleApplication;
import sg.atom.managex.api.function.AtomFunction;
import sg.atom.managex.api.function.FunctionSystem;

/**
 *
 * @author CuongNguyen
 */
public class AtomEditorFunctionSystem extends FunctionSystem {

    public AtomEditorFunctionSystem(SimpleApplication app) {
        super(app);
    }

    public void setupFunctions() {
        addFunction(new AtomFunction("Move", "Move", "sprite:20,23,0"));
        addFunction(new AtomFunction("Rotate", "Rotate", "sprite:20,23,1"));
        addFunction(new AtomFunction("Scale", "Scale", "sprite:20,23,2"));
        addFunction(new AtomFunction("Select", "Select", "sprite:20,23,46"));
    }
}
