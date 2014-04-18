package sg.atom.script.engine;

import java.io.Reader;
import java.util.logging.Logger;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * AtomScriptEngine is the extension of JSR-223 script engine capable for
 * scripting features that Atom framework support.
 *
 * <p>SemanticContext - which is a Atom term has a lot similar features with
 * ScriptContext but its not extends ScriptContext for a lot of purposes (work
 * with JXPath and Reactive for example). AtomScriptEngine also support this
 * kind of Context or a delegation of ScriptContext get by SemanticContext.
 *
 * <h4>Features</h4><ul>
 *
 * <li>Load other script engine via this class
 *
 * <li>Load and reload script on demand / automaticly
 *
 * <li>Seamlessly event hook and flow of the running game.
 *
 * <li>Interpop between script engines
 *
 * <li>Decorate scripts for UI:
 *
 * <li>Decorate scripts for objects and proxy:
 *
 * <li>Spatials/script mapping
 *
 * <li>Support context! </ul> <p>
 *
 * @author hungcuong
 */
public class AtomScriptEngine implements ScriptEngine {
    //Features

    public static int SUPPORT_SCRIPT_RELOAD = 0;
    public static int SUPPORT_SCRIPT_1 = 0;
    public static int SUPPORT_SCRIPT_2 = 0;
    public static int SUPPORT_SCRIPT_3 = 0;
    public static int SUPPORT_SCRIPT_4 = 0;
    public static int SUPPORT_SCRIPT_5 = 0;
    public static int SUPPORT_SCRIPT_6 = 0;
    public static int SUPPORT_SCRIPT_7 = 0;
    // Script JSR
    private ScriptEngineManager factory;
    private ScriptEngine genericScriptEngine;
    //Loggind
    private String headerLine;
    private static final Logger logger = Logger.getLogger(AtomScriptEngine.class.getName());
    private boolean viaLoader = false;

    public AtomScriptEngine() {
        //factory = new ScriptEngineManager();
        //headerLine = "== Groovy Script Engine . Version 1.0 :";
    }

    public void startEngine(String path) {
    }

    public boolean check(String text) {
        return false;
    }

    private boolean isEngineReady() {
        return false;
    }

    public void openScriptEditor() {
    }
    //public void 

    public void reloadScript() {
    }

    public Object eval(String script, ScriptContext context) throws ScriptException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object eval(Reader reader, ScriptContext context) throws ScriptException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object eval(String script) throws ScriptException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object eval(Reader reader) throws ScriptException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object eval(String script, Bindings n) throws ScriptException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object eval(Reader reader, Bindings n) throws ScriptException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void put(String key, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object get(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Bindings getBindings(int scope) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setBindings(Bindings bindings, int scope) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Bindings createBindings() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ScriptContext getContext() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setContext(ScriptContext context) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ScriptEngineFactory getFactory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
