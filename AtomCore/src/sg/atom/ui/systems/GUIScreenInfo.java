/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.systems;

import com.google.common.reflect.TypeToken;
import com.jme3.asset.AssetKey;

/**
 * Reference to a Screen of an GUISystemService.
 *
 * <p>Remove this, or replace with AssetKey and Cache facilities.
 *
 * @author cuong.nguyenmanh2
 */
@Deprecated
public class GUIScreenInfo<T extends GUISystemService, S> {

    public String filePath;
    public boolean loaded = false;
    public AssetKey assetKey;
    private T system;

    public GUIScreenInfo(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public S getScreen() {
        return null;
    }

    public AssetKey getAssetKey() {
        return assetKey;
    }

    public TypeToken getTypeToken() {
        return TypeToken.of(Object.class);
    }
    
    public T getSystemService(){
        return system;
    }
}
