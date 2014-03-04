/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.input;

import com.jme3.input.RawInputListener;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;

/**
 *
@author atomix
 */
public abstract class MouseRawListener implements RawInputListener {

    public void beginInput() {
    }

    public void endInput() {
    }

    public void onJoyAxisEvent(JoyAxisEvent evt) {
    }

    public void onJoyButtonEvent(JoyButtonEvent evt) {
    }

    public abstract void onMouseMotionEvent(MouseMotionEvent evt);

    public abstract void onMouseButtonEvent(MouseButtonEvent evt);

    public void onKeyEvent(KeyInputEvent evt) {
    }

    public void onTouchEvent(TouchEvent evt) {
    }
}
