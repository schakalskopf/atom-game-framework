/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.systems;

/**
 * Reference to a Screen of an GUISystemService.
 *
 * <p>Remove this, or replace with AssetKey and Cache facilities.
 *
 * @author cuong.nguyenmanh2
 */
@Deprecated
public class GUIScreenInfo<T extends GUISystemService> {

    public String filePath;
    public boolean loaded = false;

    public GUIScreenInfo(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isLoaded() {
        return loaded;
    }
}
