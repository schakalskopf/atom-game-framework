package sg.atom.editor.ui.nifty.components;

import sg.atom.editor.managers.EditorGUIManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class CodeEditorComponent extends ExNiftyComponent {
    
    public CodeEditorComponent(EditorGUIManager guiManager) {
        super("codeEditor",guiManager);
    }

    @Override
    public Element build(Nifty nifty, Screen screen, Element parent) {
        return super.build(nifty, screen, parent);
    }
       

    public void bind(Nifty nifty, Screen screen) {
        super.bind(nifty, screen);
        nScreen.findElementByName("txtCode").setFocus();
    }



}
