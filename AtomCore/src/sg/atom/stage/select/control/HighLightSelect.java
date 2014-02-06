/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select.control;

import com.jme3.material.Material;
import com.jme3.material.MaterialDef;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import sg.atom.stage.select.SpatialSelectControl;
import sg.atom.world.MaterialManager;

/**
 *
 * @author atomix
 */
public class HighLightSelect  extends SpatialSelectControl {

    Geometry targetGeo;

    @Override
    public void doDeselected() {
        // make it normal
        Material targetMat = targetGeo.getMaterial();
        MaterialDef matDef = targetMat.getMaterialDef();
        //System.out.println(matDef.getAssetName() + matDef.getName());
        if (MaterialManager.isLightingMat(matDef)) {
            targetMat.setBoolean("Minnaert", false);
            targetMat.getAdditionalRenderState().setBlendMode(BlendMode.Off);
        }
        if (MaterialManager.isLightBlowMat(matDef)) {
            targetMat.setBoolean("Toon", false);
            targetMat.setFloat("EdgeSize", 0f);
            targetMat.setColor("EdgesColor", ColorRGBA.Blue);
            targetMat.setBoolean("Fog_Edges", false);
            targetMat.getAdditionalRenderState().setBlendMode(BlendMode.Off);
            targetMat.getAdditionalRenderState().setWireframe(false);
        }

    }
}
