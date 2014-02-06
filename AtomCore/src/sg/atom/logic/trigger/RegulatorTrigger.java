/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic.trigger;

import sg.atom.core.timing.RegularTiming;
import sg.atom.core.timing.Regulator;

/**
 *
 * @author cuong.nguyenmanh2
 */
public abstract class RegulatorTrigger extends ConditionalTrigger implements RegularTiming {

    public float intervalTime;
    public Regulator timer;

    @Override
    public void executeTriggerAction() {
        if (this.active) {
            if (this.timer.isReady()) {
                actived();
            }
        } else {
            deactived();
        }
    }

    @Override
    public void update(float tpf) {
        timer.update(tpf);
    }

    @Override
    public float getInterval() {
        return intervalTime;
    }
}
