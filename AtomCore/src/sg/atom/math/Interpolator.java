/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.math;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface Interpolator<V> {
    V interpolate(V a, V b);
}
