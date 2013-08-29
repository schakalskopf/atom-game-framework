package atom.editor.components;

import atom.editor.EditorGUIManager;
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

/**
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

        this.nifty = guiManager.getNifty();
        this.nScreen = guiManager.getNiftyScreen();
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

    List<Element> getElementsByName(String namePattern) {
        ArrayList<Element> result = new ArrayList<Element>();
        for (Element e : rootElement.getElements()) {
            if (e.getId().matches(namePattern)) {
                result.add(e);
            }
        }

        return result;
    }

    Element getElementByName(String name) {
        return rootElement.findElementByName(name);
    }
}
