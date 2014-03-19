/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.common;

import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;

/**
 *
 * @author atomix
 */
public class UILoadingBarController implements Controller {

    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;
    private Element progressBarElement;
    private float frameCount = 0;
    private boolean load = false;
    private boolean binded = false;

    public void setProgress(final float progress, String loadingText) {
        final int MIN_WIDTH = 32;
        int pixelWidth = (int) (MIN_WIDTH + (progressBarElement.getParent().getWidth() - MIN_WIDTH) * progress);
        progressBarElement.setConstraintWidth(new SizeValue(pixelWidth + "px"));
        progressBarElement.getParent().layoutElements();


    }

    public void bind(Nifty nifty, Screen screen, Element element, Properties parameter, Attributes controlDefinitionAttributes) {
        progressBarElement = element.findElementByName("progressbar");
        binded = true;
    }

    public void init(Properties parameter, Attributes controlDefinitionAttributes) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onFocus(boolean getFocus) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean inputEvent(NiftyInputEvent inputEvent) {
        return false;
    }

    public void onStartScreen() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
