/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select;

import com.jme3.asset.AssetManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import sg.atom.stage.select.ui.SelectRectUI;

/**
 *
 * @author atomix
 */
public abstract class AbstractSpatialSelectCondition implements SelectCondition {

    /**
     *
     * @param cam
     * @param selectRectUI
     * @return
     */
    public abstract AbstractSpatialSelectCondition createCondition(Camera cam, SelectRectUI selectRectUI);

    @Override
    public boolean isSelected(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public abstract Spatial getDebugShape(AssetManager assetManager);
}
