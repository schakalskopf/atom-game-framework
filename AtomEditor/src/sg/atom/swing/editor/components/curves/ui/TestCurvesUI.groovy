package atom.swing.editor.ui.curves.ui

import groovy.swing.SwingBuilder
import groovy.swing.j2d.*
import java.awt.BorderLayout as BL
import java.awt.*;
import javax.swing.*
import com.jme3.math.*


def jFrame;
random = new Random()
// CURVE
curvesUI = new CurvesUI()

def showFrame(){
    def swing = SwingBuilder.build {
        jFrame = frame( title: 'Curves UI', size: [800,640],
            locationRelativeTo: null, show: true ,defaultCloseOperation: JFrame.EXIT_ON_CLOSE,){
            panel(new GraphicsPanel(),id:"gp", background:Color.gray, graphicsOperation: curvesUI.getDrawGrid())
                
            panel( constraints:BL.NORTH ){
                button(text:"Run",actionPerformed:{
                    })
            }
        }
    }
}
showFrame()
