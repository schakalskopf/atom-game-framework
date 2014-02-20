package sg.atom.swing.editor

import java.awt.*
import java.awt.BorderLayout as BL
import javax.swing.*
import groovy.swing.j2d.*
import groovy.swing.SwingBuilder

/**
 *Prototype for a trigger editor
 */
public class PrototypeSwingEditor{

    def Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    def swing = new SwingBuilder();
    def app3d
    
    def actions
    // UIs
    def toolbar,menubar,userToolbar
    def mainPanel,palletePanel,projectPanel,propertyPanel
    def searchBox
    
    def createIcon(String path){
        def icon = null
        try {
            icon = swing.imageIcon(resource:"../../../../images/"+path, class:PrototypeSwingEditor.class)
        } catch (e){
            println ("Can not not the icon in :" + path)
        }
        return icon
    }
    
    def createMenu(swing){
        swing.menuBar() {
            menu(text: "File", mnemonic: 'F') {
                menuItem(text: "Open", mnemonic: 'L', actionPerformed: { })
                menuItem(text: "Save", mnemonic: 'L', actionPerformed: { })
                separator()
                menuItem(text: "Exit", mnemonic: 'X', actionPerformed: {dispose() })
            }
            menu(text: "Edit", mnemonic: 'C') {

            
            }
        
            menu(text: "View", mnemonic: 'C') {
                menuItem(text: "Spectrum Gradient", mnemonic: 'L', actionPerformed: {d.show() })
            
            }

            menu(text: "Window", mnemonic: 'C') {

            
            }
            menu(text: "Help", mnemonic: 'C') {

            
            }
        }
    }

    def createToolbar(swing){
        swing.toolBar(constraints:BL.NORTH){
        
            button("New",toolTipText:"New",icon:createIcon("icons/ToolbarIcons/24/New.png"))
            button("Open",toolTipText:"Open",icon:createIcon("icons/ToolbarIcons/24/Open.png"))
            button("Save",toolTipText:"Save",icon:createIcon("icons/ToolbarIcons/24/Save.png"))
            separator(orientation:SwingConstants.VERTICAL)
            button("Copy",toolTipText:"Copy",icon:createIcon("icons/ToolbarIcons/24/Copy.png"))
            button("Cut",toolTipText:"Cut",icon:createIcon("icons/ToolbarIcons/24/Cut.png"))
            button("Paste",toolTipText:"Paste",icon:createIcon("icons/ToolbarIcons/24/Paste.png"))
            button("Delete",toolTipText:"Delete",icon:createIcon("icons/ToolbarIcons/24/Delete.png"))
            separator(orientation:SwingConstants.VERTICAL)
            
            customToolbarButtons(swing)
        }
    }
    
    def customToolbarButtons(swing){
        swing.toolBar(constraints:BL.NORTH){
            toggleButton("Select",toolTipText:"Select",icon:createIcon("icons/ToolbarIcons/24/Select.png"))
            toggleButton("AddNode",toolTipText:"AddNode",icon:createIcon("icons/ToolbarIcons/24/AddNode.png"),actionPerformed:{
                    /*
                    if (graphTest.isAddingNodeMode()){
                    graphTest.setAddingNodeMode(false);
                    } else {
                    graphTest.setAddingNodeMode(true);
                    }
                     */
                });
            
            separator(orientation:SwingConstants.VERTICAL)
            toggleButton("New",toolTipText:"Play",icon:createIcon("icons/ToolbarIcons/24/Play.png"))
            button("Connect",toolTipText:"Connect",icon:createIcon("icons/ToolbarIcons/24/Connect.png"))
            button("Disconnect",toolTipText:"Disconnect",icon:createIcon("icons/ToolbarIcons/24/Disconnect.png"))
        }
    }

    
    def createUI(title){
        swing.edt {
            jFrame = frame( title: title, size: screenSize,
                locationRelativeTo: null, show: true ,defaultCloseOperation: JFrame.EXIT_ON_CLOSE){
        
                borderLayout()
        
                createMenu(swing)
        
                createToolbar(swing)
        
                mainPanel=panel(constraints:BL.CENTER,id:"gp"){
                    borderLayout()
                    scrollPane(constraints:BL.CENTER){
                        //widget(graphTest.getView())
                        //widget()
                    }
                    /*
                    panel(constraints:BL.CENTER,new GraphZoomScrollPane(vv)){
                    borderLayout()
                    panel(constraints:BL.CENTER,vv)
                    }
                     */
                }
                panel(constraints:BL.WEST){
                    panel(constraints:"up"){
                        borderLayout()
                        panel(constraints:BL.SOUTH,background:Color.white,preferredSize:[220,200]){
                            borderLayout()
                            label(text:"Navigator",constraints:BL.NORTH)

                    
                        }
                        tabbedPane(constraints:BL.CENTER){
                            panel(title:"Project"){
                                borderLayout()
                                scrollPane (constraints:BL.CENTER){
                                    tree() 
                                }  
                            }
                            panel(title:"Palette"){
   
                                borderLayout()
                                button(constraints:BL.NORTH,text:"Add")
                                scrollPane (constraints:BL.CENTER){
                                    panel(constraints:BL.CENTER){
                                        gridLayout(cols:1,rows:5)
                                        (1..5).each{
                                            button(text:"Node"+it)
                                        }
                                    }  
                                }
                            }
                            panel(title:"Structure"){
                                borderLayout()
                                scrollPane (constraints:BL.CENTER){
                                    tree() 
                                }
                            }
                        }
                
                    }

                } 
                panel(constraints:BL.SOUTH){
                    borderLayout()
                    tabbedPane(){
                        scrollPane (title:"Result"){
                            textPane(text:"""
<html>
    <body>
    </body>
</html>
""")
                        }
                        scrollPane (title:"Log"){
                            textArea(text:"Log")
                        }
                    }
                }
                panel(constraints:BL.EAST){
                    borderLayout()
                    button(constraints:BL.NORTH,text:"Preview")
                    //panel(constraints:BL.CENTER,new PreviewPanel(),preferredSize:[120,120])
                    panel(constraints:BL.SOUTH){
                        gridLayout(cols:2,rows:5)
                        /*
                        (1..5).each{
                            label(text:"Properties"+it)
                            if (it%2==0){
                                textField(text:"Node")
                            } else {
                                slider(preferredSize:[120,20])
                            }
                        }
                        */
                    }
                    
                }
            }
        }
    }
}

