/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.timing;

/**
 * Interface to mark RegularTiming action
 * @author cuong.nguyenmanh2
 */
public interface RegularTiming {
    public float getInterval();
    public void update(float tpf);
}
