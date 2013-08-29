/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select.condition;

import sg.atom.entity.SpatialEntity;
import sg.atom.stage.select.EntitySelectCondition;

/**
 *
 * @author CuongNguyen
 */
public class EmptyEntitySelectCondition extends EntitySelectCondition {

    @Override
    public boolean isSelected(SpatialEntity se) {
        return true;
    }

    @Override
    public boolean isHovered(SpatialEntity se) {
        return true;
    }
}
