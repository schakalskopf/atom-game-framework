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
public class MaterialUtils {

    public static final String TEXTURE_TYPE_COLOR = "ColorMap";
    public static final String TEXTURE_TYPE_DIFFUSE = "DiffuseMap";
    public static final String TEXTURE_TYPE_NORMAL = "NormalMap";
    public static final String TEXTURE_TYPE_SPECULAR = "SpecularMap";
    public static final String TEXTURE_TYPE_GLOW = "GlowMap";
    public static final String TEXTURE_TYPE_ALPHA = "AlphaMap";
    public static final String TEXTURE_TYPE_LIGHTMAP = "LightMap";
    CommonTool commonTool;

    public MaterialUtils() {
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
