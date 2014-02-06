/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.timing;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class SimpleTimeValue<V> implements TimeValueIndicator<V> {

    private float time;
    private V value;

    public SimpleTimeValue(float time, V value) {
        this.time = time;
        this.value = value;
    }

    @Override
    public float getTime() {
        return time;
    }

    @Override
    public V getValue() {
        return value;

    }
}
