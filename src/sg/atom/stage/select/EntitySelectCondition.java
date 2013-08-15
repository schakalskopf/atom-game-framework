/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select;

import sg.atom.entity.SpatialEntity;

/**
 *
 * @author CuongNguyen
 */
public abstract class EntitySelectCondition {
    public abstract boolean isSelected(SpatialEntity se);
    
    public abstract boolean isHovered(SpatialEntity se);
}
