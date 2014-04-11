/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.module.plugin;

import com.samskivert.util.DependencyGraph;
import java.util.ArrayList;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.utils.algorimth.relate.Relation;

/**
 * PluginManager is the main class reponsible to manage/load/disable plugins.
 * This implementation is simpler. You should depends in osgi for sostiphicated
 * one.
 *
 * <h4>Features:</h4>
 *
 * <ul> <li>Load things in file or classpath
 *
 * <li>Use Discorvery to find implemenation of plugins.
 *
 * <li>Use Guice to inject
 *
 * <li>Annotations to descript useful implemenataion infos in class.
 * Configurations (XML,Groovy) are also support but simple.
 *
 * <li>Active/Deactive of plugins is handled via cycle and by progressive
 * methods. Force interuption or force close (thread violent intention) are
 * supported but not recommendd!
 *
 * @author CuongNguyen
 */
public class PluginManager {

    ArrayList<Plugin> plugins;
    //DependencyGraph<Plugin,Relation<Plugin>> dependencyGraph;
    DependencyGraph<Plugin> dependencyGraph;

    public void loadPlugin(Plugin plugin) {
    }

    public void findImplementations() {
    }

    public void injectPlugin(Plugin plugin) {
    }

    public void scan() {
    }

    public void cycleActive(Plugin plugin, IGameCycle.LifeCyclePhase phase) {
    }

    public void progressiveActive(Plugin plugin) {
    }

    public void cycleClose(Plugin plugin, IGameCycle.LifeCyclePhase phase) {
    }

    public void progressiveClose(Plugin plugin) {
    }
}
