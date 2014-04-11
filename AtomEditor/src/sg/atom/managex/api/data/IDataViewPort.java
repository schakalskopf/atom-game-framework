/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.data;

import sg.atom.corex.asset.DataStructure;

/**
 * Viewport is the same concept as Viewport + Camera in JME3, is a frame, mean
 * limited area with boundary of Data which user are interested in or showing up
 * area of the Data where user interact, and commonly a focus point of interest
 * which called the cursor.
 *
 * <p>One common implementation of this term refer a viewport with a Rectangle.
 * This just consider is a context or an embedable enviroment.</p>
 *
 * @author cuong.nguyenmanh2
 */
public interface IDataViewPort<T extends DataStructure>{

    public Object getBoundary();

    public Object getDataAtCursor();

    public Object getCursor();

    public int getCursorIndex();

    public T getDataStructure();

    public void validate();
}
