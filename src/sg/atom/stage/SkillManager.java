/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage;

import java.util.HashMap;
import sg.atom.gameplay.Skill;

/**
 *
 * @author hungcuong
 */
public class SkillManager {

    private HashMap<Long, Skill> skillList = new HashMap<Long, Skill>();
    private HashMap<Long, String> types = new HashMap<Long, String>();
    long totalId=-1;
    
    public SkillManager() {
        skillList.put(Long.valueOf(1), new Skill("Move"));
        types.put(Long.valueOf(1), "Player");
    }
}
