package editor.app2d

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

import codegen.*
import editor.curves.ui.*
import net.boplicity.xmleditor.*;
import editor.particle.*
import editor.particle.ui.*
import editor.app2d.components.*
import editor.app2d.components.tree.*

swing = new SwingXBuilder()
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
app = new Particle3DApp()
actions=[:]

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

def createFriendPopupMenu(def builder){
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
        
        button( text:"New",icon:createIcon("ToolbarIcons/24/New.png"))
        button( text:"Open")
        button( text:"Save")
        separator(orientation:SwingConstants.VERTICAL)
    }
    return toolbar
}


def createIcon(String path){
    return swing.imageIcon(resource:"../../images/"+path,class:Game2DEditor.class)
}

swing.edt{
    //lookAndFeel("com.oyoaha.swing.plaf.oyoaha.OyoahaLookAndFeel")

    //lookAndFeel(createNimRODLAF())
    actions["Align"] = action(shortDescription :"Align",smallIcon:createIcon("gimp_icon/DPixel-black/images/align-22.png"))
    actions["Crop"] = action(shortDescription :"Align",smallIcon:createIcon("gimp_icon/DPixel-black/images/crop-22.png"))
    actions["Flip"] = action(shortDescription :"Align",smallIcon:createIcon("gimp_icon/DPixel-black/images/flip-22.png"))
    actions["Scissor"] = action(shortDescription :"Align",smallIcon:createIcon("gimp_icon/DPixel-black/images/iscissors-22.png"))
    actions["RectSelect"] = action(shortDescription :"Align",smallIcon:createIcon("gimp_icon/DPixel-black/images/rect-select-22.png"))
    actions["Move"] = action(shortDescription :"Align",smallIcon:createIcon("gimp_icon/DPixel-black/images/move-22.png"))
    actions["Rotate"] = action(shortDescription :"Align",smallIcon:createIcon("gimp_icon/DPixel-black/images/rotate-22.png"))
    actions["Scale"] = action(shortDescription :"Align",smallIcon:createIcon("gimp_icon/DPixel-black/images/scale-22.png"))
    actions["Zoom"] = action(shortDescription :"Align",smallIcon:createIcon("gimp_icon/DPixel-black/images/zoom-22.png"))
    actions["Text"] = action(shortDescription :"Align",smallIcon:createIcon("gimp_icon/DPixel-black/images/text-22.png"))
    actions["Picker"] = action(shortDescription :"Align",smallIcon:createIcon("gimp_icon/DPixel-black/images/color-picker-22.png"))
                    
    frame(title: 'Game Creator', defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
        size: screenSize, 
        minimumSize: [350,500],
        show: true, locationRelativeTo: null) {
        
        borderLayout()
        toolBar(rollover:true, constraints:BorderLayout.NORTH,floatable :true) {
        
            button( toolTipText:"New",icon:createIcon("ToolbarIcons/24/New.png"))
            button( toolTipText:"Open",icon:createIcon("ToolbarIcons/24/Open.png"))
            button( toolTipText:"Save",icon:createIcon("ToolbarIcons/24/Save.png"))
            separator(orientation:SwingConstants.VERTICAL)
            button( toolTipText:"Copy",icon:createIcon("ToolbarIcons/24/Copy.png"))
            button( toolTipText:"Cut",icon:createIcon("ToolbarIcons/24/Cut.png"))
            button( toolTipText:"Paste",icon:createIcon("ToolbarIcons/24/Paste.png"))
            button( toolTipText:"Delete",icon:createIcon("ToolbarIcons/24/Delete.png"))
            separator(orientation:SwingConstants.VERTICAL)
        }
        
        //toolbar = createToolbar(swing)
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

                panel(title:"Layout", new LayoutCreatorComponent(this)){

                }
                panel(title:"Particle Editor"){
                    borderLayout()
                    widget(app.createAndStartCanvas(800,600),constraints:BL.CENTER)
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



