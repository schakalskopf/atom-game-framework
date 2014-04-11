/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.services.spritegui;

import java.util.List;
import sg.atom.fx.sprite.Sprite;
import sg.atom.ui.systems.GUIElement;
import sg.atom.ui.systems.GUIInteraction;
import sg.atom.ui.systems.GUIStyle;

/**
 * Use sprite as unit element. Reactive!
 *
 * @author cuong.nguyenmanh2
 */
public class SpriteGUIElement implements GUIElement<SpriteGUI, SpriteGUIElement> {

    public void asNinePatch() {
    }

    @Override
    public void doInteraction(GUIInteraction<SpriteGUI> interaction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void interceptInteraction(GUIInteraction<SpriteGUI> interaction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<GUIInteraction<SpriteGUI>> getInteractions() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SpriteGUIElement getElement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GUIStyle<SpriteGUI, SpriteGUIElement> getElementStyle() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<SpriteGUIElement> getChildren() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
