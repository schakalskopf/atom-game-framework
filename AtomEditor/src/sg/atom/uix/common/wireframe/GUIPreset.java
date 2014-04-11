/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.uix.common.wireframe;

import java.util.ArrayList;
import sg.atom.ui.systems.GUIStyle;

/**
 * Arrangment or a decoration, behaviours scenario with styles. This normally
 * refered a tempate.
 *
 * @author cuong.nguyenmanh2
 */
public class GUIPreset {

    ArrayList<GUIStyle> styles;
    ArrayList<GUIDecoration> decorations;
    ArrayList<GUIBehaviour> behaviours;
    String name;
    Integer id;
    String author;
    Integer version;
}
