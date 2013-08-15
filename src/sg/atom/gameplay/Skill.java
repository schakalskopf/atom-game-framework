/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import java.util.HashMap;

/**
 *
 * @author hungcuong
 */
public class Skill {

    private String skillName;
    private HashMap<String, Float> skillProperty;

    public Skill(String name){
        
    }
    
    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public HashMap<String, Float> getSkillProperty() {
        return skillProperty;
    }

    public void setSkillProperty(HashMap<String, Float> skillProperty) {
        this.skillProperty = skillProperty;
    }
    
    
}
