/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.MaterialDef;
import com.jme3.texture.Texture;
import sg.atom.stage.WorldManager;

/**
 *
 * @author hungcuong
 */
public class MaterialManager {

    AssetManager assetManager;

    public MaterialManager(WorldManager worldManager) {
        this.assetManager = worldManager.getAssetManager();
    }

    public static boolean isLightBlowMat(MaterialDef matDef) {
        return (matDef.getAssetName().endsWith("LightBlow.j3md"));
    }

    public static boolean isLightingMat(MaterialDef matDef) {
        return (matDef.getAssetName().endsWith("Lighting.j3md"));
    }

    public Material convertToLightBlow(Material orgMat) {
        // check if org is Lighting.jmd
        if (isLightingMat(orgMat.getMaterialDef())) {
            // construct the clone Mat

            Material newMat = new Material(assetManager, "MatDefs/LightBlow/LightBlow.j3md");
            Texture diffuseMap = orgMat.getTextureParam("DiffuseMap").getTextureValue();
            newMat.setTexture("DiffuseMap", diffuseMap);
            return newMat;
        } else {
            return orgMat;
        }
    }
}
