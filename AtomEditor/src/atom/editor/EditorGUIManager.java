package atom.editor;

import atom.editor.components.ExNiftyComponent;
import atom.editor.components.MainMenuComponent;
import atom.editor.components.SceneGraphTreeComponent;
import atom.editor.components.ToolbarComponent;
import atom.managex.action.FunctionSystem;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ControlBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.loaderv2.NiftyLoader;
import de.lessvoid.nifty.loaderv2.types.NiftyType;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class EditorGUIManager implements ScreenController {
    
    Node gizmo = new Node("gizmo");
    private Nifty nifty;
    private Screen nScreen;
    private AssetManager assetManager;
    private InputManager inputManager;
    InGameEditor editor;
    SimpleApplication app;
    private FlyByCamera flyCam;
    private FunctionSystem functionSystem;
    private boolean noXML;
    String defaultIconPath = "Interface/icons/blenderbuttons3.png";
    private HashMap<String, JmeCursor> cursors = new HashMap<String, JmeCursor>();
    private MainMenuComponent menuComp;
    private ToolbarComponent toolbarComp;
    private SceneGraphTreeComponent treeComp;
    private ArrayList<ExNiftyComponent> components;
    
    public EditorGUIManager(InGameEditor editor) {
        this.editor = editor;
        this.app = editor.getApp();
        this.assetManager = app.getAssetManager();
        this.inputManager = app.getInputManager();
        this.flyCam = app.getFlyByCamera();
        
    }
    
    public void startUp(boolean noXML) {
        this.noXML = noXML;
        functionSystem = editor.getFunctionSystem();
        components = new ArrayList<ExNiftyComponent>();
        initCurrors();
        setupNifty();
        setupUI();
        setupInput();
    }
    
    void setupUI() {
        if (noXML) {
            de.lessvoid.nifty.screen.Screen mainScreen = new ScreenBuilder("main") {
                {
                    controller(EditorGUIManager.this);
                    
                    layer(new LayerBuilder("layer") {
                        {
                            backgroundColor("#0000");
                            childLayoutVertical();
                            
                            panel(new PanelBuilder("MenuContainer") {
                                {
                                    childLayoutCenter();
                                    height("25px");
                                    width(percentage(100));
                                    
                                    panel(menuComp.createMenu());
                                }
                            });
                            panel(new PanelBuilder("TreeContainer") {
                                {
                                    childLayoutCenter();
                                    height("*");
                                    width(percentage(20));
                                    panel(treeComp.createTree());
                                }
                            });
                            panel(new PanelBuilder("ToolbarContainer") {
                                {
                                    childLayoutCenter();
                                    height("25px");
                                    width(percentage(100));
                                    panel(toolbarComp.createToolbar());
                                }
                            });
                        }
                    });
                }
            }.build(nifty);
            // ("nifty-default-controls.xml");
            // ("nifty-default-styles.xml");
            nifty.addScreen("InGameEditorScreen", mainScreen);
            nifty.gotoScreen("InGameEditorScreen");
        } else {
            menuComp = new MainMenuComponent(this);
            toolbarComp = new ToolbarComponent(this, functionSystem);
            treeComp = new SceneGraphTreeComponent(this);
            components.add(menuComp);
            components.add(toolbarComp);
            components.add(treeComp);
            nifty.fromXml("Interface/Screens/IGEditorScreen.xml", "InGameEditorScreen",
                    new ScreenController[]{this, menuComp});
        }
        
    }
       
    public void createElements() {
        /*
         Element menuContainer = nScreen.findElementByName("MenuContainer");
         menuContainer.add(menuComp.createMenu().build(nifty, nScreen, menuContainer));
         Element toolbarContainer = nScreen.findElementByName("ToolbarContainer");
         toolbarContainer.add(toolbarComp.createToolbar().build(nifty, nScreen, toolbarContainer));
         Element treeContainer = nScreen.findElementByName("TreeContainer");
         treeContainer.add(treeComp.createTree().build(nifty, nScreen, treeContainer));
         */
        
        putCompToContainer("MenuContainer", menuComp);
        putCompToContainer("ToolbarContainer", toolbarComp);
        putCompToContainer("TreeContainer", treeComp);
        
    }
    
    public void putCompToContainer(String containerName, ControlBuilder builder) {
        Element aContainer = nScreen.findElementByName(containerName);
        aContainer.add(builder.build(nifty, nScreen, aContainer));
    }
    
    void initCurrors() {
        cursors.put("normal", (JmeCursor) assetManager.loadAsset("Textures/Cursors/TRONnormal.ani"));
        inputManager.setMouseCursor(cursors.get("normal"));
        inputManager.setCursorVisible(true);
        //flyCam.setEnabled(false);
        flyCam.setMoveSpeed(40f);
        flyCam.setDragToRotate(true);
    }
    
    void setupNifty() {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                inputManager,
                app.getAudioRenderer(),
                app.getGuiViewPort());
        nifty = niftyDisplay.getNifty();
        //nifty.

        if (noXML) {
            // NORMAL STYLE
            NiftyType niftyType = new NiftyType();
            
            try {
                NiftyLoader niftyLoader = nifty.getLoader();
                niftyLoader.loadStyleFile("nifty-styles.nxs", "nifty-default-styles.xml", niftyType, nifty);
                niftyLoader.loadControlFile("nifty-controls.nxs", "nifty-default-controls.xml", niftyType);
                
                niftyLoader.loadStyleFile("nifty-styles.nxs", "Interface/Styles/Button/ex-button.xml", niftyType, nifty);
                niftyLoader.loadControlFile("nifty-controls.nxs", "Interface/Controls/Button/ex-button.xml", niftyType);
                niftyType.create(nifty, nifty.getTimeProvider());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        }
        app.getGuiViewPort().addProcessor(niftyDisplay);
    }
    
    public void bind(Nifty nifty, Screen aScreen) {
        this.nifty = nifty;
        this.nScreen = aScreen;
        if (!noXML) {
            createElements();
        }
        menuComp.bind(nifty, aScreen);
        toolbarComp.bind(nifty, aScreen);
        treeComp.bind(nifty, aScreen);

        // FINISH
        treeComp.setupTreeBox();
        //nifty.getCurrentScreen().getFocusHandler().resetFocusElements();

    }
    
    public void onStartScreen() {
    }
    
    public void onEndScreen() {
    }
    /*
     void makeTonegodToolbar() {
     screen = new Screen(this, "tonegod/gui/style/def/style_map.xml");
     screen.initialize();
     guiNode.addControl(screen);

     // Add window
     Window win = new Window(screen, "win", new Vector2f(15, 15));

     // create button and add to window
     ButtonAdapter aBtn = new ButtonAdapter(screen, "Move", new Vector2f(15, 55)) {
     @Override
     public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
     }
     };

     // Add it to out initial window
     win.addChild(aBtn);

     // Add window to the screen
     screen.addElement(win);
     }
     * */

    // HANDLE 
    public void doFunction(String functionName) {
        System.out.println(" You clicked " + functionName);
        toolbarComp.setStatusText(functionName);
    }
    
    public InGameEditor getEditor() {
        return editor;
    }

    public SimpleApplication getApp() {
        return app;
    }
    
    public Nifty getNifty() {
        return nifty;
    }
    
    public Screen getNiftyScreen() {
        return nScreen;
    }
    
    public String getDefaultIconPath() {
        return defaultIconPath;
    }
    
    private void setupInput() {
    }
    
    public ArrayList<ExNiftyComponent> getComponents() {
        return components;
    }
    
    public <T> T getComponent(Class<T> clazz) {
        for (ExNiftyComponent comp : components) {
            if (clazz.isInstance(comp)) {
                return (T) comp;
            }
        }
        return null;
    }
}
