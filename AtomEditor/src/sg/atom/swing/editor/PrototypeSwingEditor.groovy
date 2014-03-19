package sg.atom.swing.editor

import java.awt.*
import java.awt.BorderLayout as BL
import javax.swing.*
import groovy.swing.j2d.*
import groovy.swing.SwingBuilder

/*
import ca.odell.glazedlists.*;
import ca.odell.glazedlists.swing.*;
import ca.odell.glazedlists.matchers.*;
 */
/**
 * Prototype for a swing editor. This is the base class only use Swing and GroovySwingBuilder.
 * 
 * By prototype, it's not (by any mean) try to replace a full-fledged swing framework. But a handy ultilities. For an full application framework, try Griffon, OpenSwing or Netbean Platform.
 * 
 * <p>This Editor use API provided by AtomEditor framework! So File, Anotations, bean API along with Spatial supposed to work normally here! Guava and Guice, GlazedList are your friends.</p>
 * 
 * <p><b>Config</b>Customization and configs are loaded via GroovyConfig and CommonConfig.</p>
 * 
 * <p><b>Layout</b>It has some predefined placements with layout to put/ inject your own components and actions.</p>
 * 
 * <p><b>Event</b>It also manage event with Guava eventbus beside of normal Swing event machnism.</p>
 * 
 * <p><b>Thread & Task</b>It wrap and queue sensitive non-swing operation, such as JME3 SceneGraph operation. Also support a simple flow to gpars task. Actions with simple undo/redo are also supported.</p>
 * 
 * <p><b>Customization</b>To custom and expand this class we can use normal Java & swing mechanism: extends or addComponent, also Groovy SwingBuilder.register new sub-builder. Support dependency injection for better testing. </p>
 * 
 * <p><ul>Supported components and their layouts:
 * <li>MainView [Dnd]</li>
 * <li>MiniView </li>
 * <li>Pallete [Dnd]</li>
 * <li>Menu</li>
 * <li>PopupMenu</li>
 * <li>Toolbar, UserToolbar</li>
 * <li>Search</li>
 * <li>Properties</li>
 * <li>Monitor, Log, Result (common called Output)</li>
 * <li>Navigator</li>
 * <li>Project, DetailedStructure (common refered as Master-Detail metaphor)</li>
 * <li>Data with tree,list and table.</li>
 * </ul>
 * <b>Components and Modules</b> can be configs before load via simple module descriptions or anytime with complex AtomEditor plugin mechanism!
 * </p>
 */
public class PrototypeSwingEditor{

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    def swing = new SwingBuilder();
    def app3d
    /*
     * A simple predefined, named style for the swing application, this will affect the initial layout, Swing's UI of all components. Components customization still work normally as Swing do.
     */ 
    def globalLayoutStyle 
    def modules, modulesDescription
    
    def actions = [:]
    def menuActions = [:]
    
    // UIs
    def toolbar,menubar,userToolbar
    def mainPanel,palletePanel,projectPanel,propertyPanel
    def searchBox
    
    // LookAndFeels
    def setupLAF(){
        
    }
        
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
    // Icon and images
    def createIcon(String path){
        //FIXME: Load icon from different kind of sources also.
        def icon = null
        try {
            icon = swing.imageIcon(resource:"../../../../images/"+path, class:PrototypeSwingEditor.class)
        } catch (e){
            println ("Can not not the icon in :" + path)
        }
        return icon
    }
    // Menu ==============================================
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
    // Popup =============================================
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
    // Data ===========================================
    def createDataComponent(){
        swing.table {
            data = [[first:'qwer', last:'asdf'],
                [first:'zxcv', last:'tyui'],
                [first:'ghjk', last:'bnm']]
            tableModel( list : data,constraints:BL.CENTER ) {
                propertyColumn(header:'First Name', propertyName:'first')
                propertyColumn(header:'last Name', propertyName:'last')
            }
        }
    }
    // Properties =============================================
    def createPropertyComponent(){
        swing.panel(){
            (1..5).each{
                label(text:"Properties"+it)
                if (it%2==0){
                    textField(text:"Node")
                } else {
                    slider(preferredSize:[120,20])
                }
            }
        }
         
    }
    def inspectProperties(properties) { 
        //effect = app.currentParticle;
        properties.each { prop, val ->
            println prop;
        }
    }
    // TOOLBAR =============================================
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
    //Monitors
    def createLogComponent(){
        textArea(text:"Log")
        /*
        textPane(text:"""
        <html>
        <body>
        </body>
        </html>
        """)
         */
    }
    def createResultComponent(){
        
    }
    def createMonitorComponent(){

    }
    def createStatusComponent(){
        swing.panel(constraints:BL.SOUTH){
            label(text:"Ready")
            separator(orientation:SwingConstants.VERTICAL)
            label(text:"ms")
            label(text:"ms")
            label(text:"File")
            
        }
    }
    def createProgressComponent(){

    }
    // Search ========================================
    def createSearchComponent(){
        
    }
    // Navigator =====================================
    // 
    //
    def createNavigatorComponent(){
        /*
        panel(constraints:BL.CENTER,new GraphZoomScrollPane(vv)){
        borderLayout()
        panel(constraints:BL.CENTER,vv)
        }
         */
    }
    // Pallete =========================================
    def createPalette(){
        swing{
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
    }
    // Structure =======================================
    def createProjectComponent(){ // GlobalStructure
        swing.scrollPane (constraints:BL.CENTER){
            tree() 
        }  
    }
    def createDetailStructureComponent(){
        swing.scrollPane (constraints:BL.CENTER){
            tree() 
        }
    
    }
    // MainView ========================================
    def createMainComponent(){
        //widget(graphTest.getView())
        //widget()
        swing {
            borderLayout()
            scrollPane(constraints:BL.CENTER){

            }
        }
    }
    def createMiniViewComponent(){
        swing.panel(){
            button(constraints:BL.NORTH,text:"Preview")

            panel(constraints:BL.SOUTH){
                gridLayout(cols:2,rows:5)
            }
        }
        //panel(constraints:BL.CENTER,new PreviewPanel(),preferredSize:[120,120])
    }
    //CREATE FRAME UI =============================================
    def createUI(title){
        swing.edt {
            jFrame = frame( title: title, size: screenSize,
                locationRelativeTo: null, show: true ,defaultCloseOperation: JFrame.EXIT_ON_CLOSE){
        
                borderLayout()
        
                createMenu(swing)
        
                createToolbar(swing)
        
                mainPanel=panel(constraints:BL.CENTER,id:"gp"){


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

                            }
                            panel(title:"Palette"){

                            }
                            panel(title:"Structure"){
                                borderLayout()

                            }
                        }
                
                    }

                } 
                panel(constraints:BL.SOUTH){
                    borderLayout()
                    tabbedPane(){
                        scrollPane (title:"Result"){
                            createResultComponent()
                        }
                        scrollPane (title:"Log"){
                            createLogComponent()
                        }
                    }
                }
                panel(constraints:BL.EAST){
                    borderLayout()
                    
                    
                }
            }
        }
    }
    // MVC =============================================
    // Actions  -------------
    
    // Data  -------------
    
    // View -------------
    // List 
    def getRow={list, point->                    
        return list.locationToIndex(point);
    }
    // Tables 
    def addRow(){
    }
}

