/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.uix.common.wireframe;

/**
 * Wireframe layout, use reflection, bean mapping and proxy to arrange, align 2D
 * elements and a-like.
 *
 * <p>How it works: First it ask (or set by) GUILayoutManager the methods to arrange and align
 * the elements. It then generate a fluent proxy upon the element + screen
 * boundary and layout model. Finally the method will be reative-lize.
 *
 * @author cuong.nguyenmanh2
 */
public class GUIAutoLayout {
}
