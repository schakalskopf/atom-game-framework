package sg.atom.script.engine.groovy;

//setup Script Engine
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import org.codehaus.groovy.control.CompilerConfiguration;
import groovy.swing.SwingBuilder
import javax.swing.*
import java.awt.*

//println "Execute Script"
swing = new SwingBuilder()
frame = swing.frame(visible:true,size:[400,400]){
    borderLayout()
}
def useGSE(){
    String[] roots =[ "src/script/groovy" ] as String[];
    GroovyScriptEngine gse = new GroovyScriptEngine(roots);
    Binding binding = new Binding();
    //binding.setVariable("swing", swing);
    //gse.run("swing/Cal.groovy", binding);
    Script scriptObj = gse.run("swing/Cal.groovy", binding);
    Object[] args = [] as Object[];
    def panel = scriptObj.invokeMethod("onGUI", args);
    //println scriptObj.binding.getVariable("swing")
    //def panel = scriptObj.onGUI()
    frame.add(panel,BorderLayout.CENTER)
    frame.validate()
}

def useShell(){
    def compiler = new CompilerConfiguration()
    compiler.setScriptBaseClass("jme3.games.script.GameObjectScript")
    def binding=new Binding()
    //binding.setVariable("name", "Cuong Nguyen Manh");
    def shell = new GroovyShell(this.class.classLoader, binding, compiler)
    def me="who"
    binding.setVariable("me", me);
    shell.evaluate("onGameStart(me,'really')")
}

def testClassLoad(){
    // execute script
    /*
    ClassLoader parent = getClass().getClassLoader();
    GroovyClassLoader loader = new GroovyClassLoader(parent);
    Class groovyClass = loader.parseClass(new File("src/jme3test/games/script/GameObjectScript.groovy"));

    // let's call some method on an instance
    GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
    Object[] args = {};
    groovyObject.invokeMethod("run", args);
     */
}

useGSE()


    
// start game

// timer update every 1 seconds. 20 s

// quit game