/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.managers;

import com.jme3.input.Input;
import java.util.Properties;
import sg.atom.core.AbstractManager;
import sg.atom.managex.api.action.AtomEditorAction;

/**
 *
 * @author CuongNguyen
 */
public class EditorInputManager extends AbstractManager {

    public void rig(Input input, AtomEditorAction action) {
    }

    public void reset() {
    }

    public void loadConfigs() {
    }

    //Cycle--------------------------------------------------------------------------
    public void init() {
    }

    public void load() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void config(Properties props) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void finish() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public LifeCyclePhase getCurrentPhase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public float getProgressPercent(LifeCyclePhase aPhrase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
