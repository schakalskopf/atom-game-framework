/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.entity.general;

@Deprecated
/**
 * Facade and Description about the functions of an Entity, can be shared by
 * multi Entities.
 *
 * <p>It share the same abstract level with Component. You can look at it beside
 * of Component as Method and Property in OOP, but not data, its for execution
 * side.</p>
 *
 * <p> This single class is where Atom implementation for Entity is more open
 * (less performance advantage) compare to other pure ES framework. You can also
 * see this as a mistaked duplicated implementation of System or (Aspect), which
 * usually refered as the 3rd in the trie "Entity-Component-System", but in pure
 * ES, System can be anything, so consider this as "Lightweight System" embeded
 * inside of Entity or to see a OOP's Method as Data (So everything can be
 * data!)</p>
 *
 * <p>Note that Atom Entity replicate Object orientted programming and involve
 * Data oriented programming at the same time. Also check Aspect programming and
 * reactive functional programming!</p>
 *
 * Read: http://hub.jmonkeyengine.org/forum/topic/entity-system-topic-united/
 *
 * @author atomix
 */
public interface AbstractEntityFunction {
}
