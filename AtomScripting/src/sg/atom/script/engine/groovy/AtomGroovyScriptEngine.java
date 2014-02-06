package sg.atom.script.engine.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hungcuong
 */
public class AtomGroovyScriptEngine {
    
    protected GroovyShell shell;
    protected GroovyScriptEngine scriptEngine;
    protected GroovyClassLoader loader;

    private static final Logger logger = Logger.getLogger(AtomGroovyScriptEngine.class.getName());
    protected boolean viaLoader = false;
    protected Binding binding;

    public AtomGroovyScriptEngine() {
        //factory = new ScriptEngineManager();
        //headerLine = "== Groovy Script Engine . Version 1.0 :";
    }

    public void startEngine(String path) {
        try {
            //genericScriptEngine = factory.getEngineByName("groovy");
            scriptEngine = new GroovyScriptEngine(path);

            binding = new Binding();
            //shell = new GroovyShell(binding);
            ClassLoader parent = getClass().getClassLoader();
            loader = new GroovyClassLoader(parent);
            logger.log(Level.FINE, "Start Script Engine in" + path);


        } catch (IOException ex) {
        }
    }
/*
    public boolean check(String text) {
        try {
            if (text.equals("")) {
                return false;
            }
            shell.parse(text);
        } catch (CompilationFailedException ex) {
            return false;
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
*/
}
