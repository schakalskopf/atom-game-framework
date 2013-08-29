package sg.testbed.game2d

import groovy.swing.SwingBuilder
import javax.swing.*
import java.awt.BorderLayout as BL
import static java.awt.Color.*
import groovy.ui.ConsoleTextEditor

import javax.swing.event.DocumentListener
import javax.swing.event.DocumentEvent
import javax.swing.text.Document
import sg.testbed.ui.*

ScriptTestBedController testBed = new ScriptTestBedController(this)

swing = new SwingBuilder();
swing.build {
    jFrame = frame( title: 'Play Ground', size: [800,640],
        locationRelativeTo: null, show: true ,defaultCloseOperation: JFrame.EXIT_ON_CLOSE){
        menuBar(){
            menu(text:"File"){
                menuItem(text:"Exit",actionPerformed:{dispose()})
            }
            menu(text:"Script"){
                new File(testBed.scriptPath).eachFile{scriptFile->
                    menuItem(text:scriptFile.name,
                        actionPerformed: {testBed.changeScriptObject(scriptFile.name)}
                    )
                }  
            }
        }
        
        panel(constraints:BL.NORTH){
            borderLayout()
            toolBar(constraints:BL.CENTER){
                button(text:"Ball")
                button(text:"Dog")
                button(text:"Cat")
                separator()
                button(text:"Run", actionPerformed:{
                        println("Complied code !")
                        testBed.updateCurrentScriptSourceCode(codeViewer.text)
                        testBed.reloadCurrentScript();
                    })
            }
        }
        testBedComp=panel(constraints:BL.CENTER, new ScriptTestBedComponent(testBed))
          
        tabbedPane(constraints:BL.SOUTH,preferredSize:[200,200]){
            scrollPane(title:"Debug"){             
                debug = panel(){
                    boxLayout(axis:BoxLayout.Y_AXIS)

                }
            }

            scrollPane(title:"Log"){
                log = textArea(text:"Log")
            }
            
        }
        tabbedPane(constraints:BL.EAST ,preferredSize:[400,200]){
            cte = scrollPane(title:"Source",new ConsoleTextEditor())
            codeViewer = cte.textEditor
            codeViewer.text = "println Hello"
            //codeViewer.getDocument().addDocumentListener(new MyDocumentListener());
        }
        
        
    }
}
class MyDocumentListener implements DocumentListener {
 
    public void insertUpdate(DocumentEvent e) {
        Document doc = (Document)e.getDocument();
        int changeLength = e.getLength();
        log.text += "Inserted in "+e.offset + "<br>";
    }
    public void removeUpdate(DocumentEvent e) {
        Document doc = (Document)e.getDocument();
        int changeLength = e.getLength();
        log.text += "Removed in "+e.offset + "<br>";
    }
    public void changedUpdate(DocumentEvent e) {
        //Plain text components do not fire these events
    }
}
void bindProps(Map vars){
    debug.removeAll()
    vars.each{name,value->
        println name +" : "+value+ " "+value.class;

        debug.add(swing.panel(){
                gridLayout(rows:1,cols:2)
                label(text:name)
                //textField(text:value)

                if (value instanceof Integer || value instanceof Float || value instanceof Double){
                    slider(minimum:-100,maximum :100,value:value)

                } else {
                    textField(text:value)
                }

            }
        )

    }
    debug.validate()
}

void updateCode(String txt){
    codeViewer.text = txt
}
testBed.changeScriptObject("ball.groovy")
