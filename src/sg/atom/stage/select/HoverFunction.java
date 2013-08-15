/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select;

import com.jme3.collision.CollisionResults;
import com.jme3.scene.Spatial;
import sg.atom.entity.SpatialEntity;
import sg.atom.entity.SpatialEntityControl;
import sg.atom.stage.SelectManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class HoverFunction {

    SelectManager selectManager;
    private SpatialEntity oldHoverEntity;

    public HoverFunction(SelectManager selectManager) {
        this.selectManager = selectManager;
    }

    public void funcHover() {
        CollisionResults results = selectManager.getCurrentHoverResults();
        SpatialEntityControl spatialEntityControl = selectManager.findSpatialEntityControl(results);

        if (spatialEntityControl != null) {
            SpatialEntity entity = spatialEntityControl.getEntity();
            if (selectManager.getEntitySelectCondition() != null && selectManager.getEntitySelectCondition().isHovered(entity)) {
                if (entity != oldHoverEntity) {
                    if (oldHoverEntity != null) {
                        markAsHover(oldHoverEntity, false);
                    }
                    markAsHover(entity, true);
                    doHoverSingleEntity(entity);
                    oldHoverEntity = entity;
                    for (SelectListener sl : selectManager.getListeners()) {
                        sl.hover(entity);
                    }
                }
            }
        } else {
            if (oldHoverEntity != null) {
                markAsHover(oldHoverEntity, false);
                oldHoverEntity = null;
            }
        }
    }

    public void markAsHover(SpatialEntity entity, boolean isHovered) {
        Spatial model = (Spatial) entity.getSpatial();

        SpatialSelectControl control = model.getControl(SpatialSelectControl.class);

        if (control == null) {
            throw new IllegalArgumentException("The SpatialEntity has no SpatialSelectControl. Add it when init!");
        } else {
            control.setHovered(isHovered);
        }
    }

    public void doHoverSingleEntity(SpatialEntity entity) {
    }

    public void notifyHover() {
        for (SelectListener sl : selectManager.getListeners()) {
            sl.outHover(oldHoverEntity);
        }
    }
}
