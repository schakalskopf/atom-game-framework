/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.common;

import sg.atom.ui.systems.GUIScreenInfo;
import sg.atom.ui.systems.GUISystemService;

/**
 *
 * @author cuong.nguyenmanh2
 */
public abstract class GUIScreenAction<T extends GUISystemService, S> implements Runnable {

    @Override
    public void run() {
        run(null, null, null);
    }

    public abstract void run(T service, GUIScreenInfo<T, S> screen, GUIScreenController<T, S> screenController);
}
