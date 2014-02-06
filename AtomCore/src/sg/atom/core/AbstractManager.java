/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import sg.atom.core.lifecycle.PiorityInfo;
import sg.atom.core.lifecycle.IGameCycle;

/**
 *
 * @author atomix
 */
/**
 * AbstractManager is a "term" for special kind of "Manager". It's a contracted
 * type of Manager who dedicated to involve strictly in the GameCycle.
 *
 * <br> AbstractManager is the first level citizen of Atom framework, it provide
 * the tree form of hierachy and nested.
 *
 * <br> It also provide execution artifacts as Piority management and Runable
 * execution.
 *
 * <br> If you going to use Component Entity Framework, this can compare to a
 * Strict implementation of a "System".
 *
 * <br> Designed to work within Actor framework, with parallel execution and
 * minimum contend.
 *
 */
public abstract class AbstractManager implements IGameCycle {

    protected HashMap<Class, SoftReference<AbstractManager>> subManagers = new LinkedHashMap<Class, SoftReference<AbstractManager>>(2);
    private PiorityInfo piority = new PiorityInfo();

    public void addSubManager(AbstractManager subManager) {
        subManagers.put(subManager.getClass(), new SoftReference<AbstractManager>(subManager));
    }

    public AbstractManager getSubManager(Class<? extends AbstractManager> aClass) {
        SoftReference<AbstractManager> result = subManagers.get(aClass);
        if (result != null) {
            return result.get();
        }
        return null;
    }

    public void setManagerInfo(Object... params) {
    }

    public void setPiority(Class<? extends AbstractManager> aClass, PiorityInfo piority) {
    }

    public PiorityInfo getPiority() {
        return piority;
    }

    public PiorityInfo getPiority(Class<? extends AbstractManager> aClass) {
        return getSubManager(aClass).getPiority();
    }
}
