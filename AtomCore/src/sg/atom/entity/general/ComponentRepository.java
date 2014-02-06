/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.entity.general;

/**
 * ComponentHolder is a mark for class which act like a "repository" for
 * Component.
 *
 * <p>Atom's Entity framework have no contract about the enviroment of Entities
 * and Components. This term commonly refered as a compounded World of Entities
 * and Components, which is an unnessary design. The Entities can be managed in
 * an sequential/concurent enviroment. Components also can be kept in another
 * sequential/concurent enviroment. The extra futher works belong to
 * implementation! </p>
 *
 * Read: http://hub.jmonkeyengine.org/forum/topic/entity-system-topic-united/
 *
 * @author cuong.nguyenmanh2
 */
public interface ComponentRepository {
}
