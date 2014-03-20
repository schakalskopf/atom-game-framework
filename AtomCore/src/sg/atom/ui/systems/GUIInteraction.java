/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.systems;

/**
 * Interaction is high level Event watching method of underlying GUI lib.
 *
 * <p>Not every Interaction is intercepted but all interaction can be watched.
 *
 * <p>Effect is a kind of transparent interaction. Use AtomEffect to manage
 * underlying GUI Effect!
 *
 * @author cuong.nguyenmanh2
 */
public interface GUIInteraction<T extends GUISystemService> {
}
