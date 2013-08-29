package editor.app2d.components

import java.awt.BorderLayout as BL;
import java.awt.*
import javax.swing.JPanel
import org.jdesktop.swingx.JXPanel;
import geo.panel.app.*

class LayoutCreatorComponent extends JXPanel{
    def layoutEditorPanel = null
    def actions = [:]
    LayoutCreatorComponent(app){
        super()
        setLayout(new BL())
        add(app.swing.panel{
                borderLayout()
                panel(constraints:BL.WEST,preferredSize:[50,500]){

                    button( action: app.actions["Align"])
                    button( action: app.actions["Crop"])
                    button( action: app.actions["Flip"])
                    button( action: app.actions["Scissor"])
                    button( action: app.actions["Move"])
                    button( action: app.actions["Rotate"])
                    button( action: app.actions["Scale"])
                    button( action: app.actions["Zoom"])
                    button( action: app.actions["Text"])
                    button( action: app.actions["Picker"])
                    button( toolTipText:"Color",background:Color.black,preferredSize:[50,50])
                }
                    
                panel(background:Color.darkGray,constraints:BL.CENTER){
                        
                    gridLayout(rows:2)
                    panel(background:Color.darkGray){
                        borderLayout()
                        scrollPane(){
                            //layoutEditorPanel=widget(new LayoutEditorPanel(),constraints:BL.CENTER)
                        }
                    }
                    panel(background:Color.gray){
                        borderLayout()
                    }

                }
            },BL.CENTER)
        
        
    }
    
    def reloadImage(){
        //layoutEditorPanel.reloadImage("D:/JGE/MY GAMES/CityGen/src/images/facade3.jpg")
    }
}