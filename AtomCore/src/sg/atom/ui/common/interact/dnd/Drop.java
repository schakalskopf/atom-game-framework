/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.common.interact.dnd;

import com.google.common.base.Predicate;
import sg.atom.ui.systems.GUIInteraction;

/**
 * Gesture and interaction recognized as Drop. Common implementation.
 *
 * @author CuongNguyen
 */
public abstract class Drop {

    public abstract Predicate<GUIInteraction> isDropable();
}
