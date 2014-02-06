/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.timing;

/**
 * A Pair of Time and Value. FIXME: in a Map Enviroment?
 *
 * @author cuong.nguyenmanh2
 */
public interface TimeValueIndicator<V> {

    public float getTime();

    public V getValue();

    // Force to have an embeded instance factory!
    //public TimeValueIndicator<V> getInstance(float time, V value);
    //public Entry asEntry();
}
