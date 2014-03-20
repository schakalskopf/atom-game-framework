/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.project;

import com.jme3.asset.AssetManager;
import java.util.ArrayList;

/**
 * Extension of AssetManager.
 *
 * <p>Same as DesktopAssetManager and Netbean DataManager. Instead of using JME3
 * asset method, this rely in Apache Common VFS and Jackrabbit (with Sirix
 * extension) to handle Data and hierchical content. Those libraries are big! So
 * just include them if you really know what you are doing.
 *
 * @author cuong.nguyenmanh2
 */
public class ProjectDataManager {

    public ArrayList<ProjectResource> resources;
    public AssetManager assetManager;

    public void resolveResource() {
    }

    ;
    
    public void loadResources() {
        // List all support extension.
    }

    public void freeResources() {
    }
}
