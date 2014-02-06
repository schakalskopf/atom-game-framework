/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select.function;

import com.jme3.collision.CollisionResults;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import sg.atom.entity.SpatialEntity;
import sg.atom.entity.SpatialEntityControl;
import sg.atom.stage.SelectManager;
import sg.atom.stage.select.SelectFunction;

/**
 *
@author atomix
 */
public class SingleSelectFunction extends SelectFunction {

    public SingleSelectFunction(SelectManager manager) {
        super(manager);
    }

    @Override
    public void init() {
    }

    @Override
    public void funcSelect() {
        selectManager.processSelectFunction();
        CollisionResults results = selectManager.getCurrentResults();
        SpatialEntityControl spatialEntityControl = selectManager.findSpatialEntityControl(results);

        // Reach an Entity _ marked with SpatialEntityControl!!!!
        if (spatialEntityControl != null) {
            SpatialEntity entity = spatialEntityControl.getEntity();

            switch (selectManager.getSelectOperationType()) {
                case Normal:
                    selectManager.deselectAll();
                    break;
                case Substract:
                    Add:
                    Union:
                    break;
            }
            //spatialEntityControl.setSelected(true);
            // deselect current selection

            if (selectManager.getEntitySelectCondition() != null && selectManager.getEntitySelectCondition().isSelected(entity)) {
                if (!selectManager.getCurrentSelection().contains(entity)) {
                    selectManager.getCurrentSelection().add(entity);
                    markAsSelect(entity);
                    doSelectSingleEntity(entity);
                    notifySelectionChange();
                }
            }
        } else {
            // nothing in shootable ?
            selectManager.deselectAll();
        }
    }

    @Override
    public void mouseMove(MouseMotionEvent evt) {
    }

    @Override
    public void mouseButton(MouseButtonEvent evt) {
        if (evt.getButtonIndex() == firstButtonIndex && !evt.isPressed()) {
            funcSelect();
        }
    }
}
