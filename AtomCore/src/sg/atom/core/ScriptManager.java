/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core;

import com.google.common.collect.Multimap;
import com.jme3.scene.Spatial;
import java.util.Properties;
import java.util.logging.Logger;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.stage.StageManager;

/**
 * ScriptManager manage script (facade) for GameObjects, Settings. with Groovy
 * by default and (and others).
 *
 * <p>Support dynamic/obsevered script binding and loading and watching specific
 * script folders
 *
 * <p>Atom also offer a simple embedable language based in Preon's Limbo call
 * Atom's Dot. because it contain a lot of dots(.)
 *
 * @author atomix
 */
public class ScriptManager implements IGameCycle {

    protected static final Logger logger = Logger.getLogger(ScriptManager.class.getName());
    private ScriptEngineManager factory;
    private ScriptEngine genericScriptEngine;
    private final StageManager stageManager;
    // Scripts folder
    public static String DEFAULT_SCRIPT_FOLDER = "/Scripts";
    public String scriptFolder = "src/mygame/script";
    public String[] scriptRoots = new String[]{scriptFolder};
    // Management
    protected Multimap<Spatial, CompiledScript> spatialScriptMap;

    public ScriptManager(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public void initScriptManager() {
        // For the script
        initScriptEngine();
        //initCineScript();
    }

//    private void initCineScript() {
//        /*
//         try {
//         // run the Script to Setup Cinematic
//         //gse.run("", binding);
//         } catch (ResourceException ex) {
//         } catch (ScriptException ex) {
//         }
//         */
//    }
    public void initScriptEngine() {
        //CompilerConfiguration compiler = new CompilerConfiguration();
        // The Engine
    }

    public void initCommonBinding() {
    }

    public void setBaseClassForScript() {
        //compiler.setScriptBaseClass("jme3test.animation.CinematicScriptBaseClass");
        //binding = new Binding();
        // The shell
        //shell = new GroovyShell(this.getClass().getClassLoader(), binding, compiler);
    }

    @Override
    public void init() {
        initScriptEngine();
    }

    @Override
    public void load() {
        loadScriptFiles();
    }

    @Override
    public void config(Properties props) {
    }

    @Override
    public void update(float tpf) {
    }

    @Override
    public void finish() {
    }

    @Override
    public LifeCyclePhase getCurrentPhase() {
        return null;
    }

    @Override
    public float getProgressPercent(LifeCyclePhase aPhrase) {
        return 0;
    }

    private void loadScriptFiles() {
    }
}
