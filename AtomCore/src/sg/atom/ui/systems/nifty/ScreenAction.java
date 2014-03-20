/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.systems.nifty;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author cuong.nguyenmanh2
 */
public abstract class ScreenAction implements Runnable {

    @Override
    public void run() {
        run(null, null, null);
    }

    public abstract void run(Nifty nifty, Screen screen, ScreenController screenController);
}
