/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.managers;

import com.jme3.niftygui.NiftyJmeDisplay;
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
import java.util.Properties;
import sg.atom.editor.InGameEditor;
import sg.atom.editor.ui.nifty.components.ExNiftyComponent;
import sg.atom.editor.ui.nifty.components.MainMenuComponent;
import sg.atom.editor.ui.nifty.components.SceneGraphTreeComponent;
import sg.atom.editor.ui.nifty.components.ToolbarComponent;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class EditorNiftyGUIManager extends EditorGUIManager implements ScreenController {
    //Nifty specific ===========================================================

    private Nifty nifty;
    private Screen nScreen;
    private boolean noXML;
    //Components -------------------------------------
    private ArrayList<ExNiftyComponent> components;
    private MainMenuComponent menuComp;
    private ToolbarComponent toolbarComp;
    private SceneGraphTreeComponent treeComp;

    public EditorNiftyGUIManager(InGameEditor editor) {
        super(editor);
    }

    @Override
    public void startUp() {
        super.startUp();
        components = new ArrayList<ExNiftyComponent>();

        setupNifty();
        setupUI();
        setupInput();
    }

    public void setupUI() {
        if (noXML) {
            de.lessvoid.nifty.screen.Screen mainScreen = new ScreenBuilder("main") {
                {
                    controller(EditorNiftyGUIManager.this);

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

    protected void setupNifty() {
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

    public Nifty getNifty() {
        return nifty;
    }

    public Screen getNiftyScreen() {
        return nScreen;
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

    @Override
    public void config(Properties props) {
        super.config(props);
        this.noXML = (Boolean) props.get("NoXML");

    }

    @Override
    public void doFunction(String functionName) {
        System.out.println(" You clicked " + functionName);
        toolbarComp.setStatusText(functionName);
    }

    @Override
    public void setupInput() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
