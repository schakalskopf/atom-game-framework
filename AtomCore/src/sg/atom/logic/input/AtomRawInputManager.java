/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic.input;

import com.jme3.input.RawInputListener;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;

/**
 * High level input listener, cooporate with StageManager, SelectManager and
 * TriggerManager. Note the different between this class and InputManager. This
 * can by pass InputManager to get Input from the system.
 *
 * <p>Its unit are : Trigger, Command and RawInput.
 *
 * <p>Sequence of Input called pattern are recoginized by pattern matching (by
 * complex determistic finite automata (DFA)).
 *
 * <p>Adapt input filter and producer to input pipeline to reduce or include
 * inputs.
 *
 * <p>It replace the default broadcast mechanism with EventBus.
 *
 * <p>It can serialize input signal to byte stream again!
 *
 * @author atomix
 */
public class AtomRawInputManager implements RawInputListener {

    public boolean transparentInteraction;

    public void beginInput() {
    }

    public void endInput() {
    }

    public void onJoyAxisEvent(JoyAxisEvent evt) {
    }

    public void onJoyButtonEvent(JoyButtonEvent evt) {
    }

    public void onMouseMotionEvent(MouseMotionEvent evt) {
    }

    public void onMouseButtonEvent(MouseButtonEvent evt) {
    }

    public void onKeyEvent(KeyInputEvent evt) {
    }

    public void onTouchEvent(TouchEvent evt) {
    }
}
