/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.corex.material;

import atom.corex.common.CommonTool;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

/**
 *
 * @author hungcuong
 */
public class MaterialUtil {

    CommonTool commonTool;

    public MaterialUtil() {
        this.commonTool = CommonTool.getDefault(null);
    }

    Material getHighlightMaterial() {
        return new Material();
    }

    Material getHighlightMaterial(Material oldMat) {
        return new Material();
    }

    public Material getDefaultMaterial() {
        Material mat = new Material(commonTool.getAssetManager(), "Unshaded.j3md");
        //mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", ColorRGBA.Green);
        return mat;
    }
}
