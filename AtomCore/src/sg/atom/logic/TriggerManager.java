package sg.atom.logic;

import com.google.common.collect.BiMap;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.Iterator;
import sg.atom.core.timing.RegularTiming;
import sg.atom.logic.conditions.Conditional;
import sg.atom.logic.trigger.Trigger;

/**
 *
 * @author atomix
 */
/**
 * Should be AppState and Replaced with loose couple version with Guava
 * predicate and Eventbus
 * <ul>
 * <li>As Trigger Factory : create, compose, validate</li>
 *
 * <li>As Trigger Manager : manage list</li>
 *
 * <li>As Trigger Event Handler : broadcast</li>
 * </ul>
 */
public class TriggerManager {

    public ArrayList<Trigger> triggerList;
    /* Management purpose*/
    public BiMap<Trigger, Spatial> triggerSpatialMap;
    public BiMap<Trigger, String> triggerCatMap;
    public BiMap<Trigger, Integer> triggerPiorityMap;

    public void update(float tpf) {
        remindAllTriggers(tpf);
    }

    public void remindAllTriggers(float tpf) {
        // FIXME: Use Guava iterator instead to add much more features!
        Iterator<Trigger> iterator = triggerList.iterator();
        while (iterator.hasNext()) {
            Trigger t = iterator.next();

            if (!t.isEnabled()) {
                t.deactived();
            } else {
                if (t instanceof RegularTiming) {
                    ((RegularTiming) t).update(tpf);
                }
                if (t instanceof Conditional) {
                    ((Conditional) t).check();
                } else {
                    //FIXME: Remind this trigger everytime. Should only use with System trigger only!
                    t.actived();
                }
                // Call every trigger 
                // to update  
                // call the appropriate function in listener 
                // and fire event on chained trigger
            }

        }
    }

    public void addTrigger(Trigger t) {
        triggerList.add(t);
    }
    /* Management methods */

    public void associateTriggerWith(Trigger t, Object... links) {
    }

    public void getTriggerByCat() {
    }
}
