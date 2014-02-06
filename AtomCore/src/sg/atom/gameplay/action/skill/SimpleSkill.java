/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.action.skill;

import sg.atom.gameplay.GameAction;
import sg.atom.gameplay.Skill;

/**
 *
 * @author atomix
 */
/**
 * Skill is am "unclean" and very "predefined" term which mainly describle a
 * "behavioral design pattern" It usually use in Game programming to indicate a
 * Game object (Actor) have some specification behaviors.
 *
 * <p>In this "simple" implementation, it is intended to have two level of
 * nested object (consider common encapsulation level for games).Also,
 * SimpleSkill have an associated GameAction. Can be Null.</p>
 *
 * <p>NOTE:If you are going to use Component Entity Framework, it's pretty much
 * the same with a nested component with float properties so no need of
 * interfere with this term or use it in lower, higher level of abstraction.</p>
 *
 * @see sg.atom.gameplay.Skill
 */
public abstract class SimpleSkill extends Skill<Object> {

    public SimpleSkill(String name) {
        super(name);
    }
    
    public abstract GameAction getAction();
}
