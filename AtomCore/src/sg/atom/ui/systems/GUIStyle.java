/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.systems;

import java.util.Properties;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface GUIStyle<T extends GUISystemService, E> {

    public Object getStyleDefinition();

    //public GUIStyle<T, E> getParent();

    public GUIStyle<T, E> getAppliedStyle(GUIElement<T, E> element);

    public void apply(GUIElement<T, E> element);

    public void config(Properties props);
}
