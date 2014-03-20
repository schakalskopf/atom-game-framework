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
public interface GUIStyle<T extends GUISystemService> {

    public Object getStyleDefinition();

    public GUIStyle getParent();

    public void apply(GUIElement element);

    public GUIStyle getStyle(GUIElement element);

    public void config(Properties props);
}
