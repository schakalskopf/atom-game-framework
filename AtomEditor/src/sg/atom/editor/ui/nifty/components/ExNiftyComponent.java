package sg.atom.editor.ui.nifty.components;

import sg.atom.editor.managers.EditorGUIManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ControlBuilder;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ExNiftyComponent is a ControlBuilder and ScreenController and Controller at
 * the same time.
 *
 * <p>It's shared the same concept with TopComponent in Netbean platform
 * enviroment.
 *
 * @author cuong.nguyenmanh2
 */
public abstract class ExNiftyComponent extends ControlBuilder implements ScreenController, Controller {

    protected Nifty nifty;
    protected EditorGUIManager guiManager;
    protected Screen nScreen;
    Element rootElement;

    public ExNiftyComponent(String id, EditorGUIManager guiManager) {
        super("panel", id);
        this.guiManager = guiManager;
    }

    public void bind(Nifty nifty, Screen screen) {
//        try {
        this.nifty = guiManager.getGUI(Nifty.class);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(ExNiftyComponent.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }

    public void bind(Nifty nifty, Screen screen, Element element, Properties parameter, Attributes controlDefinitionAttributes) {
    }

    public boolean inputEvent(NiftyInputEvent inputEvent) {
        return false;
    }

    public void init(Properties parameter, Attributes controlDefinitionAttributes) {
    }

    public void onFocus(boolean getFocus) {
    }

    public List<Element> getElementsByName(String namePattern) {
        ArrayList<Element> result = new ArrayList<Element>();
        for (Element e : rootElement.getElements()) {
            if (e.getId().matches(namePattern)) {
                result.add(e);
            }
        }

        return result;
    }

    public Element getElementByName(String name) {
        return rootElement.findElementByName(name);
    }

    public Screen getNiftyScreen() {
//        try {
        return guiManager.getGUI(Nifty.class).getCurrentScreen();
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(ExNiftyComponent.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //return null;
    }
}
