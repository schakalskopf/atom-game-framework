/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select;

import com.jme3.input.MouseInput;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import sg.atom.entity.Entity;
import sg.atom.entity.SpatialEntity;
import sg.atom.stage.SelectManager;

/**
 * <b>SelectFunction</b> has a SelectCondition and determine a SelectShape,
 * which will be represent by a SelectUI.
 * 
 * <p>FIXME: Replace with Guava function
 *
 * @author CuongNguyen
 */
public abstract class SelectFunction {

    protected SelectCondition defaultSelectCondition;
    protected SelectShape selectShape;
    protected SelectUI selectUI;
    protected SelectManager selectManager;
    boolean enable = true;
    protected int firstButtonIndex = MouseInput.BUTTON_LEFT;
    protected int secondButtonIndex = MouseInput.BUTTON_RIGHT;

    public SelectFunction(SelectManager manager) {
        this.selectManager = manager;
    }

    public abstract void init();

    public void markAsSelect(SpatialEntity entity) {

        Spatial model = (Spatial) entity.getSpatial();

        SpatialSelectControl control = model.getControl(SpatialSelectControl.class);

        if (control == null) {
            throw new IllegalArgumentException("The SpatialEntity has no SpatialSelectControl. Add it when init!");
        } else {
            control.setSelected(true);
        }
    }

    /**
     * This function create a 2D select rectangle on the screen to select
     * entities<br> The default implement create a green rectangle with
     * SelectRectUI class<br> You can overide it to create you own!
     */
    public void createSelectUI() {
    }

    public SelectUI getSelectUI() {
        return selectUI;
    }

    public void notifySelectionChange() {
        for (SelectListener sl : selectManager.getListeners()) {
            sl.selectMulti(selectManager.getCurrentSelection());
        }
    }

    protected void doSelectSingleEntity(Entity entity) {
    }

    public abstract void mouseMove(MouseMotionEvent evt);

    public abstract void mouseButton(MouseButtonEvent evt);

    public abstract void funcSelect();

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setFirstButtonIndex(int normalButtonIndex) {
        this.firstButtonIndex = normalButtonIndex;
    }

    public int getFirstButtonIndex() {
        return firstButtonIndex;
    }

    public void setSecondButtonIndex(int secondButtonIndex) {
        this.secondButtonIndex = secondButtonIndex;
    }

    public int getSecondButtonIndex() {
        return secondButtonIndex;
    }
}
