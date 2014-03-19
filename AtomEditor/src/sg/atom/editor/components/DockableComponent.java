package sg.atom.editor.components;

import sg.atom.editor.EditorGUIManager;
import sg.atom.editor.InGameEditor;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class DockableComponent extends ExNiftyComponent {

    private Element dialogUI;
    private boolean closed = true;
    private InputManager inputManager;

    public DockableComponent(EditorGUIManager guiManager) {
        super("dockablePanel", guiManager);
    }

    void setupInput() {
        inputManager.addMapping("AltKey", new KeyTrigger(KeyInput.KEY_LMENU), new KeyTrigger(KeyInput.KEY_RMENU));
        inputManager.addMapping("ToogleDialog", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addListener(actionListener, "AltKey", "ToogleDialog");
    }
    /**
     * Use ActionListener to respond to pressed/released inputs (key presses,
     * mouse clicks)
     */
    private ActionListener actionListener = new ActionListener() {
        private boolean pressAlt;

        public void onAction(String name, boolean pressed, float tpf) {
            if (name.equals("AltKey")) {
                pressAlt = pressed;
                System.out.println(" Press Alt");
            }
            if (pressAlt) {
                if (name.equals("ToogleDialog") && !pressed) {

                    toogleDialog();
                }
            }
        }
    };

    public void toogleDialog() {
        if (closed) {
            showDialog();
        } else {
            closeDialog();
        }
    }

    public void closeDialog() {
        if (dialogUI != null) {
            //dialogUI.resetAllEffects();
            dialogUI.startEffect(EffectEventId.onCustom, new DialogUINotify(false), "moveOut");
            closed = true;
        }
    }

    public void showDialog() {
        if (dialogUI != null) {
            //dialogUI.resetAllEffects();
            dialogUI.startEffect(EffectEventId.onCustom, new DialogUINotify(true), "moveIn");
            closed = false;
        }
    }

    @Override
    public Element build(Nifty nifty, Screen screen, Element parent) {
        return super.build(nifty, screen, parent);
    }

    public void bind(Nifty nifty, Screen screen) {
        super.bind(nifty, screen);
        dialogUI = nScreen.findElementByName("SideContainer");
    }

    class DialogUINotify implements EndNotify {

        boolean isOpen;

        DialogUINotify(boolean isOpen) {
            this.isOpen = isOpen;
        }

        public void perform() {
            //inventoryOpen = isOpen;
        }
    }

    void createDockHide(Element panel) {
    }
}
