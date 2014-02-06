/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic.trigger;

import com.google.common.base.Predicate;
import sg.atom.logic.conditions.Conditional;
import sg.atom.logic.trigger.Trigger;

/**
 * This is the common implementation of Trigger which is used a lot in Game.
 *
 * @author cuong.nguyenmanh2
 */
public abstract class ConditionalTrigger implements Trigger, Conditional {
    //TODO:: Change to use Predicate<Trigger> instead of Atom's Condition and Boolean.

    public Predicate<Trigger> activeConditionPredicate;
    public Predicate<Trigger> enableConditionPredicate;
    protected boolean enable;
    protected boolean active;
    protected boolean selfExecuted = true;

    @Override
    public boolean check() {
        this.enable = enableConditionPredicate.apply(this);
        if (this.enable) {
            this.active = activeConditionPredicate.apply(this);

            if (selfExecuted) {
                executeTriggerAction();
            }

        } else {
            return false;
        }
        return this.active;
    }

    public void executeTriggerAction() {
        if (this.active) {
            actived();
        } else {
            deactived();
        }
    }
// SETTER & GETTER

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void toogleEnable() {
        this.enable = !this.enable;
    }

    @Override
    public boolean isEnabled() {
        return this.enable;
    }

    public boolean isActive() {
        return active;
    }

    public void setActiveConditionPredicate(Predicate<Trigger> activeConditionPredicate) {
        this.activeConditionPredicate = activeConditionPredicate;
    }

    public Predicate<Trigger> getActiveConditionPredicate() {
        return activeConditionPredicate;
    }

    public void setEnableConditionPredicate(Predicate<Trigger> enableConditionPredicate) {
        this.enableConditionPredicate = enableConditionPredicate;
    }

    public Predicate<Trigger> getEnableConditionPredicate() {
        return enableConditionPredicate;
    }

    public void setSelfExecute(boolean selfExecuted) {
        this.selfExecuted = selfExecuted;
    }

    public boolean isSelfExecuted() {
        return selfExecuted;
    }
}
