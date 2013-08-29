package script.groovy.swing

import groovy.swing.SwingBuilder
import javax.swing.*
import java.awt.*

swing = new SwingBuilder()

def onGUI(){
    swing.panel() {
       label("Test Scipt Panel")
       label("Test Scipt Panel1")
       label("Test Scipt Panel2")
       label("Test Scipt Panel3")
    }
}
//__me={}
return this

