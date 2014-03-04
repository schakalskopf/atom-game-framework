/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.filters;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.post.Filter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.texture.Texture;

/**
 *
 * @author kwando
 */
public class LUTCCFilter extends Filter {

    Texture ccmap;

    @Override
    protected void initFilter(AssetManager manager, RenderManager renderManager, ViewPort vp, int w, int h) {
        //material = new Material(manager, "MatDefs/LUTCC.j3md");
        //ccmap = manager.loadTexture("Shaders/ColorCorrection/ccmap_blue.png");

        material = new Material(manager, "MatDefs/LUTCC256.j3md");
        ccmap = manager.loadTexture(new TextureKey("Shaders/ColorCorrection/LUT_Blurish.png", false));
        //ccmap = manager.loadTexture("Shaders/ColorCorrection/LUT_Sepia.png");

        material.setTexture("CCMap", ccmap);

    }

    @Override
    protected Material getMaterial() {
        return material;
    }
}