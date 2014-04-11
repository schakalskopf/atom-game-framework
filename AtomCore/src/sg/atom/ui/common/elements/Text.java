/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.common.elements;

import sg.atom.ui.systems.GUIScreenInfo;

/**
 * Build out of bitmap font or mesh.
 *
 * <p>In the future going to support OpenGV's text.
 *
 * @author CuongNguyen
 */
public class Text {

    String text;

    public void applyLocale(String locale) {
    }

    public void display(GUIScreenInfo screen) {
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
