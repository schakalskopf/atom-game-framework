package sg.testbed.ui

import javax.swing.*
import java.awt.*
import java.awt.BorderLayout as BL
import static java.awt.Color.*
import groovy.ui.ConsoleTextEditor
import sg.testbed.game2d.AtomScriptEngine2D

class ScriptTestBedController{
    String scriptPath = "src/script/groovy/play2d/"
    AtomScriptEngine2D scriptEngine = new AtomScriptEngine2D();
    def scriptObject = null;
    String currentScriptName = ""
    Map oldVars = [:]
    def preservedVarNames = ["x","y"]
    def main
    public ScriptTestBedController(def main){
        this.main = main
    }
    
    def getTestObject(){
        return scriptObject;
    }


    def changeScriptObject(name){
        try {
            //testBed.setAnimation(false);

            // preserve some binded vars
            if (scriptObject!=null && scriptEngine.binding!=null){
                vars = scriptEngine.binding.getVariables()

                preservedVarNames.each{varName->
                    oldVars[varName]=vars[varName]
                }
            }

            // load the script object
            def path = scriptPath + name
            currentScriptName = name
            updateCode(new File(path).text)
            scriptObject = scriptEngine.runScript(name)
            //println(scriptObject)

            // put preserved vars back in to binding
            preservedVarNames.each{varName->
                if (oldVars[varName]!=null){
                    scriptEngine.binding.setVariable(varName,oldVars[varName])
                }
            }
            bindProps()
            main.testBedComp.setAnimation(true);
        } catch (Exception e){
            main.log.text += e.message + "<br>";
        }
        //groovy.inspect.swingui.ObjectBrowser.inspect(scriptObject)
    }

    def bindProps(){
        /*
        scriptObject.metaClass.properties.each{p->
            println p.name + " : ";
            //debug.add(new JLabel(name))
        }
        */
        
        main.bindProps(scriptEngine.binding.getVariables())
        
    }
    
    void updateCode(String code){
        main.updateCode(code);
    }
    void updateCurrentScriptSourceCode(String code){
        new File(scriptPath + currentScriptName).text = code
    }
    
    void reloadCurrentScript(){
        changeScriptObject(currentScriptName)
    }
}

