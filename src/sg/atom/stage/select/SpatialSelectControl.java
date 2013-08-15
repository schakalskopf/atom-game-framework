/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 *
 * @author hungcuong
 */
public class SpatialSelectControl extends AbstractControl {

    private boolean selected;
    private boolean selectable;
    private boolean hovered;
    private boolean hoverable =true;

    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    protected void doSelected() {
    }

    protected void doDeselected() {
    }

    protected void doHovered() {
    }

    @Override
    public void setSpatial(Spatial spatial) {
        // do something
        //targetGeo = SceneGraphHelper.getFirstGeo(spatial);
        this.spatial = spatial;
        setSelected(false);
        setSelectable(true);

    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        if (this.selected != selected) {
            this.selected = selected;
            if (selected) {
                doSelected();
            } else {
                doDeselected();
            }
        }
    }

    /**
     * @return the selectable
     */
    public boolean isSelectable() {
        return selectable;
    }

    /**
     * @param selectable the selectable to set
     */
    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        if (this.hovered != hovered) {
            this.hovered = hovered;
            if (hovered) {
                doHovered();
            } else {
                doOutHovered();
            }
        }
    }

    protected void doOutHovered() {
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return this;
    }

    public boolean isHoverable() {
        return hoverable;
    }

    public void setHoverable(boolean hoverable) {
        this.hoverable = hoverable;

        if (!hoverable) {
            if (isHovered()) {
                doOutHovered();
            }
        }
    }
}
