package sg.testbed.game2d

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import sg.atom2d.game2d.graphics.swing.*

public class AtomScriptEngine2D{
    String[] roots = [ "src/groovy/script","src/script/groovy/play2d","src/script/groovy/play3d" ];
    GroovyScriptEngine gse = new GroovyScriptEngine(roots);
    Binding binding = new Binding();
    
    public AtomScriptEngine2D(){
        binding.setVariable("gutil",new GUtil())
    }
    def runScript(path){
        return gse.run(path, binding);
    }
}




