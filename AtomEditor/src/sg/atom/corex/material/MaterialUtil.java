/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.material;

import sg.atom.corex.common.CommonTool;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

/**
 * Util that help Editing material problem, register its self to CommonTool.
 *
 * Use Atom MaterialManager instead.
 *
 * @author hungcuong
 */
@Deprecated
public class MaterialUtil {

    CommonTool commonTool;

    public MaterialUtil() {
        this.commonTool = CommonTool.getDefault(null);
    }

    public Material getHighlightMaterial() {
        return new Material();
    }

    public Material getHighlightMaterial(Material oldMat) {
        return new Material();
    }

    public Material getDefaultMaterial() {
        Material mat = new Material(commonTool.getAssetManager(), "Unshaded.j3md");
        //mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", ColorRGBA.Green);
        return mat;
    }
}
