/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.memeto;

import java.util.Hashtable;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface PropertiesMemeto<T> extends IMemeto<T> {

    /**
     * Upon receiving this message the receiver should place any relevant state
     * into state.
     *
     * @param state
     */
    public void storeState(Hashtable<Object, Object> state);

    /**
     * Upon receiving this message the receiver should extract any relevant
     * state out of state.
     *
     * @param state
     */
    public void restoreState(Hashtable<?, ?> state);
}
