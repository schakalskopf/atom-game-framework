/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.systems;

import java.util.List;

/**
 * Element is Unit of the system. Read below to see what assumtion are made.
 *
 * <p>Element has style. Style casscaded. Style applied make changes to Element
 * properties and Interactions.
 *
 * <p>Interaction attached to Element. Interaction under Element level can not
 * be intercepted. To invoke lower level, you have to cast and use the specific
 * implementation directly.
 *
 * @author cuong.nguyenmanh2
 */
public interface GUIElement<T extends GUISystemService> {

    public void doInteraction(GUIInteraction<T> interaction);

    public void interceptInteraction(GUIInteraction<T> interaction);

    public List<GUIInteraction<T>> getInteractionDescription();
}
