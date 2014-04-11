/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.project;

import com.jme3.asset.AssetManager;
import java.io.File;
import java.util.ArrayList;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import sg.atom.corex.asset.AtomExAssetManager;
//import org.apache.commons.io.

/**
 * Extension of AssetManager.
 *
 * <p>Same as DesktopAssetManager and Netbean DataManager. But single project at
 * a time!
 *
 * <p>It also understand (and use the same) official JME3 project structure.
 *
 * <p>For project external management. Instead of using JME3 asset method, this
 * rely in Apache Common VFS and Jackrabbit (with Sirix extension) to handle
 * Data and hierchical content. Those libraries are big! So just include them if
 * you really know what you are doing.
 *
 * <p>
 *
 * @author cuong.nguyenmanh2
 */
public class ProjectDataManager {

    protected AtomEditorProject currentProject;
    public ArrayList<ProjectResource> resources;
    public AssetManager assetManager;
    public AtomExAssetManager exAssetManager;

    public void resolveResource() {
    }

    /**
     * Watch over the project directory.
     */
    public class FAListener implements FileAlterationListener {

        @Override
        public void onDirectoryChange(File arg0) {
            System.out.println("onDirectoryChange : " + arg0);

        }

        @Override
        public void onDirectoryCreate(File arg0) {
            System.out.println("onDirectoryCreate : " + arg0);

        }

        @Override
        public void onDirectoryDelete(File arg0) {
            System.out.println("onDirectoryDelete : " + arg0);
        }

        @Override
        public void onFileChange(File arg0) {
            System.out.println("onFileChange : " + arg0);
            //backup(arg0);
        }

        @Override
        public void onFileCreate(File arg0) {
            System.out.println("onFileCreate : " + arg0);
            //backup(arg0);

        }

        @Override
        public void onFileDelete(File arg0) {
            System.out.println("onFileDelete : " + arg0);

        }

        @Override
        public void onStart(FileAlterationObserver arg0) {
            // System.out.println("onStart !!!!!");
        }

        @Override
        public void onStop(FileAlterationObserver arg0) {
            // System.out.println("onStop !!!!!");
        }
    }

    public void loadResources() {
        // List all support extension.
        // add monitoring
    }

    public void freeResources() {
        // try to help GC old resources.
    }

    public void pack(String targetPath) {
    }

    public void unpack(String targetPath) {
    }

    public void runTask(String task) {
        // Ant task or Maven goal
    }

    public void decorate(String name,Object flavour) {
    }
    
    /**
     * The project require the editor to be update or include modules.
     */
    public void updateNeeded(){
        
    }
    
    
}
