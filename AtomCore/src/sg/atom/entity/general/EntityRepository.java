/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.entity.general;

/**
 * is a mark for class which act like a "repository" for Entity.
 *
 * <p>Atom's Entity framework have no contract about the enviroment of Entities
 * and Components. This term commonly refered as a compounded World of Entities
 * and Components, which is an unnessary design. The Entities can be managed in
 * an sequential/concurent enviroment. Components also can be kept in another
 * sequential/concurent enviroment. The extra futher works belong to
 * implementation! </p>
 *
 * EntityRepository try to procedure a "managed view", a fullfilled view of
 * available Entity to the outside interesters/obsversers.
 *
 * Sensitive information may be hidden away without notification to the
 * outsider. This contract is very important to fix the common mistake of other
 * Entity repository which exposed all the inside Entities.
 *
 * @author cuong.nguyenmanh2
 */
public interface EntityRepository {
}
