/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core;

import sg.atom.core.lifecycle.PiorityInfo;
import sg.atom.core.lifecycle.IGameCycle;

/**
 *
 * @author cuong.nguyenmanh2
 */
public abstract class AbstractManager implements IGameCycle {

    public void addSubManager() {
    }

    public void getManager(Class aClass) {
    }

    public void setManagerInfo() {
    }

    public void setPiority(Class<? extends AbstractManager> aClass, PiorityInfo piority) {
    }
}
