package sg.atom.swing.editor


import javax.swing.*
import javax.swing.filechooser.FileFilter
import java.awt.*
import java.awt.BorderLayout as BL;
import java.awt.GridBagConstraints as GBC;

import groovy.swing.SwingBuilder
import groovy.swing.SwingXBuilder
import groovy.swing.j2d.*

import com.nilo.plaf.nimrod.*
import groovy.util.FactoryBuilderSupport

import ca.odell.glazedlists.*;
import ca.odell.glazedlists.swing.*;
import ca.odell.glazedlists.matchers.*;

import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout;
import org.jdesktop.swingx.MultiSplitLayout.Divider;

import org.jdesktop.swingx.MultiSplitLayout.Leaf;
import org.jdesktop.swingx.MultiSplitLayout.Node;
import org.jdesktop.swingx.MultiSplitLayout.Split;
import org.jdesktop.swingx.treetable.*;

import groovy.swing.j2d.GraphicsBuilder
import groovy.swing.j2d.GraphicsPanel

import net.boplicity.xmleditor.*;

import sg.atom.swing.tools.*
import sg.atom.swing.editor.components.*
import sg.atom.swing.editor.components.tree.*
import sg.atom.swing.editor.components.curves.ui.*

import sg.atom.swing.SwingSimple3DApp

class PrototypeSwingXEditor{
    def swing = new SwingXBuilder()
    def Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    def app3d
    
    def actions
    // UIs
    def toolbar,menubar,userToolbar
    def mainPanel,palletePanel,projectPanel,propertyPanel
    def searchBox
    
    def createNimRODLAF(){
        /*
        NimRODTheme nt = new NimRODTheme();
        nt.setPrimary1( new Color(255,255,255));
        //nt.setPrimary2( new Color(20,20,20));
        //nt.setPrimary3( new Color(30,30,30));
        nt.setPrimary( new Color(0,150,250))
        nt.setBlack( new Color(255,255,250))
        nt.setWhite( Color.lightGray)
        nt.setSecondary( Color.gray)
 
        NimRODLookAndFeel NimRODLF = new NimRODLookAndFeel();
        NimRODLF.setCurrentTheme( nt);

        //lookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel")
        return NimRODLF;
         */
    }

    def createMenuBar(def builder){
    
        menubar = builder.menuBar() {
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
        return menubar
    }

    def createPopupMenu(def builder){
        /*
        popupMenu = builder.popupMenu {
        //menuItem(text: "Help", mnemonic: 'R', actionPerformed: { })
        //menuItem(text: "About iChat", mnemonic: 'R', actionPerformed: { })
            
        }
         */
   
        def popupMenu = new JPopupMenu();
        popupMenu.add(jmi1= new JMenuItem("Add"));
        popupMenu.add(new JPopupMenu.Separator());
        popupMenu.add(jmi2 = new JMenuItem("Clear"));

        return popupMenu
    }
    def getRow={list, point->
                                
        return list.locationToIndex(point);
    }

    def createToolbar(def builder){
        toolbar = builder.toolBar(rollover:true, constraints:BorderLayout.NORTH,floatable :true) {
        
            button( toolTipText:"New",icon:createIcon("icons/icons/ToolbarIcons/24/New.png"))
            button( toolTipText:"Open",icon:createIcon("icons/icons/ToolbarIcons/24/Open.png"))
            button( toolTipText:"Save",icon:createIcon("icons/icons/ToolbarIcons/24/Save.png"))
            separator(orientation:SwingConstants.VERTICAL)
            button( toolTipText:"Copy",icon:createIcon("icons/icons/ToolbarIcons/24/Copy.png"))
            button( toolTipText:"Cut",icon:createIcon("icons/icons/ToolbarIcons/24/Cut.png"))
            button( toolTipText:"Paste",icon:createIcon("icons/icons/ToolbarIcons/24/Paste.png"))
            button( toolTipText:"Delete",icon:createIcon("icons/icons/ToolbarIcons/24/Delete.png"))
            separator(orientation:SwingConstants.VERTICAL)
        }
        return toolbar
    }
    
    def customToolbarButtons(swing){
        swing.toolBar(constraints:BL.NORTH){
            toggleButton("Select",toolTipText:"Select",icon:createIcon("icons/ToolbarIcons/24/Select.png"))
            toggleButton("AddNode",toolTipText:"AddNode",icon:createIcon("icons/ToolbarIcons/24/AddNode.png"),actionPerformed:{

                });
            
            separator(orientation:SwingConstants.VERTICAL)
            toggleButton("New",toolTipText:"Play",icon:createIcon("icons/ToolbarIcons/24/Play.png"))
            button("Connect",toolTipText:"Connect",icon:createIcon("icons/ToolbarIcons/24/Connect.png"))
            button("Disconnect",toolTipText:"Disconnect",icon:createIcon("icons/ToolbarIcons/24/Disconnect.png"))
        }
    }
    
    def createIcon(String path){
        def icon = null
        try {
            icon = swing.imageIcon(resource:"../../../../../images/"+path,class:Game2DEditor.class)
        } catch (e){
            println ("Can not not the icon in :" + path)
        }
        return icon
    }

    def createUI(title){
        swing.edt{
            //lookAndFeel("com.oyoaha.swing.plaf.oyoaha.OyoahaLookAndFeel")

            //lookAndFeel(createNimRODLAF())

                    
            frame(title: title, defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
                size: screenSize, 
                minimumSize: [350,500],
                show: true, locationRelativeTo: null) {
        
                borderLayout()

                toolbar = createToolbar(swing)

                menubar = createMenuBar(swing)
        
                String layoutDef ="(ROW (COLUMN weight=0.9 (LEAF name=leftup weight=0.8) (LEAF name=leftdown weight=0.2)) (COLUMN weight=0.1 (LEAF name=up weight=0.5) (LEAF name=down weight=0.5)))";
                MultiSplitLayout.Node modelRoot = MultiSplitLayout.parseModel(layoutDef);

                JXMultiSplitPane multiSplitPane1 = new JXMultiSplitPane();
                multiSplitPane1.getMultiSplitLayout().setModel(modelRoot);
    
                /*
                def children =[ 
                new Leaf("left"),
                new Divider(), 
                new Leaf("right")];
                Split modelRoot = new Split();
                modelRoot.setChildren(children);

                JXMultiSplitPane multiSplitPane1 = new JXMultiSplitPane();
                multiSplitPane1.getMultiSplitLayout().setModel(modelRoot);
                 */
        

                multiSplitPane1.setDividerSize(3)
                multiSplitPane(multiSplitPane1,constraints:BL.CENTER){
                    tabbedPane(constraints:"leftup"){
                        /*
                        panel(title:"Layout", new LayoutCreatorComponent(this)){

                        }
                         */
                        panel(title:"Particle Editor"){
                            borderLayout()
                            widget(app3d.createAndStartCanvas(800,600),constraints:BL.CENTER)
                        }
                
                        panel(title:"Text"){
                            borderLayout()
                            scrollPane (constraints:BL.CENTER){
                                textArea(text:"sampleText")
                            }
                        }

                    }
                    tabbedPane(constraints:"leftdown"){
                        scrollPane (title:"Result"){
                            textPane(new XmlTextPane(),text:"""
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
            
                    panel(constraints:"down",preferredSize:[300,500]){
                        borderLayout()
                        tabbedPane(constraints:BL.CENTER){
                            panel(title:"Properties"){
                                borderLayout()

                                label(text:"Properties",constraints:BL.NORTH)       
                                table {
                                    data = [[first:'qwer', last:'asdf'],
                                        [first:'zxcv', last:'tyui'],
                                        [first:'ghjk', last:'bnm']]
                                    tableModel( list : data,constraints:BL.CENTER ) {
                                        propertyColumn(header:'First Name', propertyName:'first')
                                        propertyColumn(header:'last Name', propertyName:'last')
                                    }
                                }
                            }

                        }
                    }
                    panel(constraints:"up"){
                        borderLayout()
                        panel(constraints:BL.SOUTH,background:Color.white,preferredSize:[200,200]){
                            borderLayout()
                            label(text:"Preview",constraints:BL.NORTH)
                            previewPanel=widget(new TexturePanel(),constraints:BL.CENTER)
                    
                        }
                        tabbedPane(constraints:BL.CENTER){
                            panel(title:"History"){
                                borderLayout()
                                panel(new HistoryTreeComponent(this),constraints:BL.CENTER)
                            }
                            panel(title:"Project"){
                                borderLayout()
                                tree(constraints:BL.CENTER)    
                            }
                            panel(title:"Palette"){
                                borderLayout()
                                tree(constraints:BL.CENTER)    
                            }

                        }
                
                    }
                }
                panel(constraints:BL.SOUTH){
                    label(text:"Ready")
                    separator(orientation:SwingConstants.VERTICAL)
                    label(text:"ms")
                    label(text:"ms")
                    label(text:"File")
            
                }
            }
    
        }
    }
    def addRow(){
    }
    //controlsPanel.add()
    def inspectApp ={ 
        effect = app.currentParticle;
        effect.properties.each { prop, val ->
            println prop;
        }
    }
    //app.enqueue(inspectApp)




}

