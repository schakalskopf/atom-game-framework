/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.ui.nifty.components;

import sg.atom.editor.managers.EditorGUIManager;
import sg.atom.editor.ui.nifty.controls.menu.ExMenu;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.PopupBuilder;
import de.lessvoid.nifty.controls.Menu;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.bushe.swing.event.EventTopicSubscriber;
import sg.atom.utils.datastructure.tree.Tree;

/**
 * MainMenuComponent is the topmost component of the editor.
 *
 * @author cuong.nguyenmanh2
 */
public class MainMenuComponent extends ExNiftyComponent implements EventTopicSubscriber<NiftyMousePrimaryClickedEvent> {

    // Replace with a Tree.
    private HashMap<String, ArrayList<String>> menuNames = new HashMap<String, ArrayList<String>>();
    private Menu myMenu;
    private MainMenuComponent mainMenuComp;
    private ExMenu rootMenu;
    private Element menuLayer;
    private Element popup;

    public MainMenuComponent(EditorGUIManager guiManager) {
        super("mainMenu", guiManager);
        
        //FIXME: Replace with Tree.
        //System.out.println("mnuFile".matches("mnu.*"));
        menuNames.put("File", new ArrayList<String>(Arrays.asList(new String[]{"A", "B"})));
        menuNames.put("Edit", new ArrayList<String>(Arrays.asList(new String[]{"A1", "B1"})));
        menuNames.put("View", new ArrayList<String>(Arrays.asList(new String[]{"A2", "B2"})));
        menuNames.put("Render", new ArrayList<String>(Arrays.asList(new String[]{"A3", "B3"})));
        menuNames.put("Run", new ArrayList<String>(Arrays.asList(new String[]{"A4", "B4"})));
        menuNames.put("Help", new ArrayList<String>(Arrays.asList(new String[]{"A5", "B5"})));

    }

    public void putLabels(String... strings){
        
    }
    
    public void putTree(Tree tree){
        
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        super.bind(nifty, screen);
        subscribeEvent(nifty, screen);
    }

    public void subscribeEvent(Nifty nifty, Screen screen) {
        for (Element e : this.getElementsByName("mnu.*")) {
            //System.out.println(" " + e.getId());
            nifty.subscribe(screen, e.getId(), NiftyMousePrimaryClickedEvent.class, this);
        }
    }

    public PanelBuilder createMenu() {
        this.nScreen = getNiftyScreen();

        return new PanelBuilder() {
            {
                id("Menu");
                childLayoutHorizontal();
                height(percentage(100));
                width(percentage(100));
                controller(MainMenuComponent.this);

                for (final String menuName : menuNames.keySet()) {
                    panel(new PanelBuilder("pmnu" + menuName) {
                        {
                            childLayoutVertical();
                            control(new LabelBuilder("mnu" + menuName, menuName) {
                                {
                                    padding("5px");
                                    width("80px");
                                    height("100%");
                                    visibleToMouse();
                                    backgroundColor("#aaaf");
                                }
                            });
                            panel(new PanelBuilder("separator") {
                                {
                                    width("1px");
                                    height("100%");
                                    backgroundColor("#000f");
                                }
                            });
                        }
                    });
                }

            }
        };
    }

    @Override
    public Element build(Nifty nifty, Screen screen, Element parent) {
        menuLayer = new LayerBuilder("topMenuLayer") {
            {
                //childLayoutAbsoluteInside();
                childLayoutAbsolute();
                height(percentage(100));
                width(percentage(100));
//                /visibleToMouse();
            }
        }.build(nifty, screen, parent);
        screen.addLayerElement(menuLayer);
        nifty.subscribe(
                nifty.getCurrentScreen(),
                menuLayer.getId(),
                NiftyMousePrimaryClickedEvent.class,
                MainMenuComponent.this);
        rootElement = createMenu().build(nifty, screen, parent);
        return rootElement;
    }
    /*
     @NiftyEventSubscriber(pattern = "mnu.*")
     public void onClick(String id, NiftyMousePrimaryClickedEvent event) {
     System.out.println("element with id [" + id + "] clicked at [" + event.getMouseX()
     + ", " + event.getMouseY() + "]");
     }
     */

    public void onEvent(String string, NiftyMousePrimaryClickedEvent t) {
        //System.out.println("" + t.toString());
        String id = t.getElement().getId();
        if (!id.equals("topMenuLayer")) {
            guiManager.getComponent(ToolbarComponent.class).setStatusText(t.getElement().getId());
            String realMenuName = id.replaceFirst("mnu", "");
            showMenu(realMenuName);
        } else {
            hideMenu();
        }
    }

    public void createMyPopupMenu(final String currentMenu) {
        //
        new PopupBuilder("niftyPopupMenu") {
            {
                childLayoutAbsoluteInside();
                width("100px");
                backgroundColor("#000a");
                // add the actual popup content here (panels, images, controls)
                panel(createCurrentMenu(currentMenu));
            }
        }.registerPopup(nifty);
        popup = nifty.createPopup("niftyPopupMenu");


        for (final String mnuSubItemName : mainMenuComp.getSubMenu(currentMenu)) {
            nifty.subscribe(
                    nifty.getCurrentScreen(),
                    "mnuItem" + mnuSubItemName,
                    NiftyMousePrimaryClickedEvent.class,
                    MainMenuComponent.this);

        }
        /*
         myMenu = popup.findNiftyControl("#menu", Menu.class);
         myMenu.setWidth(new SizeValue("100px")); // must be set
         popup.setConstraintX(new SizeValue("0px"));
         popup.setConstraintY(new SizeValue("40px"));
         popup.getParent().layoutElements();
         myMenu.addMenuItem("Click me!", "Interface/icons/jme3/bone.png",
         new ExMenuItem("mnuOpen", "Open...")); // menuItem is a custom class
         nifty.subscribe(
         nifty.getCurrentScreen(),
         myMenu.getId(),
         MenuItemActivatedEvent.class,
         this);
         */
    }

    public PanelBuilder createCurrentMenu(final String currentMenu) {
        return new PanelBuilder("_menuPanel") {
            {
                visibleToMouse();
                childLayoutVertical();
                width("150px");
                backgroundColor("#aaaa");
                for (final String mnuSubItemName : getSubMenu(currentMenu)) {
                    control(new LabelBuilder("mnuItem" + mnuSubItemName) {
                        {

                            onHoverEffect(new HoverEffectBuilder("colorBar") {
                                {
                                    effectParameter("color", "#ff44");
                                    post(true);
                                }
                            });
                            onHoverEffect(new HoverEffectBuilder("hint") {
                                {
                                    effectParameter("hintText", mnuSubItemName);
                                }
                            });
                            visibleToMouse();
                            width("100%");
                            height("25px");
                            style("base-font");
                            text(mnuSubItemName);
                        }
                    });

                }
            }
        };
    }

    public void showMenu(String currentMenu) { // the method to trigger the menu
        createTopLayerMenu(currentMenu);
    }

    public void createTopLayerMenu(String currentMenu) {
        hideMenu();
        Element newMenu = createCurrentMenu(currentMenu).build(nifty, nScreen, menuLayer);
        Element sourceMenuElement = nScreen.findElementByName("mnu" + currentMenu);
        System.out.println(currentMenu + " " + sourceMenuElement + " : " + sourceMenuElement.getConstraintX());

        newMenu.setConstraintX(sourceMenuElement.getConstraintX());
        newMenu.setConstraintY(new SizeValue("30px"));
        menuLayer.add(newMenu);
        menuLayer.layoutElements();

    }

    public void hideMenu() {
        clearAllElements(menuLayer);
    }

    public ArrayList<String> getSubMenu(String parent) {
        return menuNames.get(parent);
    }

    private void clearAllElements(Element parent) {
        for (Element e : parent.getElements()) {
            e.markForRemoval();
        }
        parent.layoutElements();
    }
}
