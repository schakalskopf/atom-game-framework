package sg.atom.swing.editor


import javax.swing.*
import javax.swing.filechooser.FileFilter
import java.awt.*
import java.awt.BorderLayout as BL;
import java.awt.GridBagConstraints as GBC;

import groovy.swing.SwingBuilder
import groovy.swing.SwingXBuilder
import groovy.swing.j2d.*

import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout;
import org.jdesktop.swingx.MultiSplitLayout.Divider;

import org.jdesktop.swingx.MultiSplitLayout.Leaf;
import org.jdesktop.swingx.MultiSplitLayout.Node;
import org.jdesktop.swingx.MultiSplitLayout.Split;
import org.jdesktop.swingx.treetable.*;

import groovy.swing.j2d.GraphicsBuilder
import groovy.swing.j2d.GraphicsPanel

/*
import com.nilo.plaf.nimrod.*
import groovy.util.FactoryBuilderSupport

import net.boplicity.xmleditor.*;
import sg.atom.swing.tools.*
import sg.atom.swing.editor.components.*
import sg.atom.swing.editor.components.tree.*
import sg.atom.swing.editor.components.curves.ui.*
 */

import sg.atom.swing.SwingSimple3DApp

/**
 * Expasion of Prototype Swing which support SwingX and other components, docking frameworks. 
 * 
 * <p>This expansion support perspective, mode and the method to change layouts.</p>
 * 
 */
public class PrototypeSwingXEditor extends PrototypeSwingEditor{
    def swing = new SwingXBuilder() 
    def perspective
    def mode
    def multiSplitPane1
    
    def createLayout(){
        String layoutDef ="(ROW (COLUMN weight=0.9 (LEAF name=leftup weight=0.8) (LEAF name=leftdown weight=0.2)) (COLUMN weight=0.1 (LEAF name=up weight=0.5) (LEAF name=down weight=0.5)))";
        MultiSplitLayout.Node modelRoot = MultiSplitLayout.parseModel(layoutDef);

        multiSplitPane1 = new JXMultiSplitPane();
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
    }
    def createUI(title){
        swing.edt{
            frame(title: title, defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
                size: screenSize, 
                minimumSize: [350,500],
                show: true, locationRelativeTo: null) {
        
                borderLayout()

                toolbar = createToolbar(swing)

                menubar = createMenu(swing)
        
                createLayout()
                
                multiSplitPane(multiSplitPane1,constraints:BL.CENTER){
                    tabbedPane(constraints:"leftup"){

                        panel(title:"Particle Editor"){
                            borderLayout()
                            //widget(app3d.createAndStartCanvas(800,600),constraints:BL.CENTER)
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
                            textArea(text:"Result")
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
                            
                    
                        }
                        tabbedPane(constraints:BL.CENTER){
                            panel(title:"History"){
                                borderLayout()
                                
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

}

