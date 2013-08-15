/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.common;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.atom.core.GameGUIManager;

/**
 *
 * @author hungcuong
 */
public class UILoadingScreenController implements ScreenController {

    private final GameGUIManager gameGUIManager;
    private Element loadingBarElement;
    private UILoadingBarController controller;
    private boolean binded = false;
    private TextRenderer textRenderer;

    public UILoadingScreenController(GameGUIManager gameGUIManager) {
        this.gameGUIManager = gameGUIManager;
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        loadingBarElement = screen.findElementByName("loadingBar");
        Element textElement = screen.findElementByName("loadingText");
        textRenderer = textElement.getRenderer(TextRenderer.class);
        controller = loadingBarElement.getControl(UILoadingBarController.class);

        binded = true;
    }

    public void setProgress(final float progress, String loadingText) {
        controller.setProgress(progress, loadingText);
        textRenderer.setText(loadingText);
    }

    public void onStartScreen() {
        Logger.getLogger(UILoadingScreenController.class.getName()).log(Level.INFO, "Loading screen start !");
    }

    public void onEndScreen() {
        Logger.getLogger(UILoadingScreenController.class.getName()).log(Level.INFO, "Loading screen end !");
    }

    public boolean isBinded() {
        return binded;
    }
}
