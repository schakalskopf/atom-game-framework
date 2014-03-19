/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.action.skill;

import com.jme3.app.Application;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import sg.atom.core.AbstractManager;
import sg.atom.stage.actor.AtomActor;
import sg.atom.gameplay.GameCharacter;
import sg.atom.gameplay.Skill;
import sg.atom.gameplay.action.ExternalAction;

/**
 *
 * @author atomix
 */
public class SkillAction extends ExternalAction {

    private Skill skillName;
    private HashMap<String, Float> skillProperty;

    public SkillAction(Skill skillName, HashMap<String, Float> skillProperty) {
        this.skillName = skillName;
        this.skillProperty = skillProperty;

    }

    /**
     * @return the skillName
     */
    public Skill getSkillName() {
        return skillName;
    }

    /**
     * @param skillName the skillName to set
     */
    public void setSkillName(Skill skillName) {
        this.skillName = skillName;
    }

    /**
     * @return the skillProperty
     */
    public HashMap getSkillProperty() {
        return skillProperty;
    }

    /**
     * @param skillProperty the skillProperty to set
     */
    public void setSkillProperty(HashMap<String, Float> skillProperty) {
        this.skillProperty = skillProperty;
    }

    public boolean canDo(AtomActor who) {
        if (who instanceof GameCharacter) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void actionContact(Object obj) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void react(Object obj) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actionStart() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actionEnd() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float canAffect(Object obj) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return 0;
    }

    @Override
    public AtomicInteger getIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
