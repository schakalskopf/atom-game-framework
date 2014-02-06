/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import java.util.HashMap;

@Deprecated
/**
 * Skill (Common Implementation) is an "unclean" and very "predefined" term
 * which mainly describle a "behavioral design pattern" It usually use in Game
 * programming to indicate a Game object (Actor) have some specification
 * behaviors.
 *
 * <p>It is intended to have two level of nested object (consider common
 * encapsulation level for games).</p>
 *
 * <ul><li>Actor has Skills</li>
 *
 * <li>Skill has Properties. For Custom Properties use Generic Skill</li>
 *
 * <li>Skill usually nested of has its ancestor, form a Tree, usually called
 * Techtree.</li> </ul>
 *
 * <p>NOTE:If you are going to use Component Entity Framework, it's pretty much
 * the same with a nested component with float properties so no need of
 * interfere with this term or use it in lower, higher level of abstraction.</p>
 *
 * @author atomix
 */
public class Skill<T> {

    private String skillName;
    private HashMap<String, T> skillProperties;

    public Skill(String name) {
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    // Properties
    public HashMap<String, T> getSkillProperties() {
        return skillProperties;
    }

    public void getSkillProperties(HashMap<String, T> skillProperties) {
        this.skillProperties = skillProperties;
    }
    // Single property
}
