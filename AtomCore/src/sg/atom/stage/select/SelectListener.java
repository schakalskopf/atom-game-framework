/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select;

import java.util.ArrayList;
import sg.atom.entity.Entity;

/**
 *
 * @author hungcuong
 */
public interface SelectListener {

    public void selected(Entity selectedObject);

    public void deselected(Entity deselectedObject);

    /**
     *
     * @param currentSelection
     */
    public void selectMulti(ArrayList currentSelection);

    /**
     *
     * @param currentSelection
     */
    public void deselectMulti(ArrayList currentSelection);

    public void hover(Entity entity);

    public void outHover(Entity entity);
}
