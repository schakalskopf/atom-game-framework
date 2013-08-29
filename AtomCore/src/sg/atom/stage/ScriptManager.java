/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import sg.atom.core.StageManager;
import org.codehaus.groovy.control.CompilerConfiguration;

/**
 *
 * @author hungcuong
 */
public class ScriptManager {

    private final StageManager stageManager;
    public GroovyScriptEngine gse;
    public Binding binding;
    public GroovyShell shell;

    public ScriptManager(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public void initScriptManager() {
        // For the script
        initScriptEngine();
        initCineScript();

    }

    private void initCineScript() {
        try {
            // run the Script to Setup Cinematic
            gse.run("CineScript1.groovy", binding);
        } catch (ResourceException ex) {
        } catch (ScriptException ex) {
        }
    }

    void initScriptEngine() {

        CompilerConfiguration compiler = new CompilerConfiguration();

        //compiler.setScriptBaseClass("jme3test.animation.CinematicScriptBaseClass");

        binding = new Binding();


        // The shell
        //shell = new GroovyShell(this.getClass().getClassLoader(), binding, compiler);

        // The Engine
        String[] roots = new String[]{"src/mygame/script"};



    }
}
