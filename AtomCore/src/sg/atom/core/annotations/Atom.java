/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.annotations;

/**
 * The first class citizen of the whole framework.
 *
 * <p>"Atom" is a kind of JavaBean, which is lightweight and fast, which can be
 * injected and has translative meaning and embedable in different contexts.</p>
 *
 * <p>Atom and Atom framework was born to encourage Java game developers bring
 * new technologies and concepts to games. Push them to the new climax of
 * creative without scarifing too much efforts. "Atom" bean is by no mean the
 * most security bean, by no mean the most bean should be used for every usecase
 * but the bean for the core of the real-time system. </p> <p> this kind of bean
 * have to be design and implemented carefully to fulfill the whole 7 points
 * below to be used "efficiently" in its real-time enviroment.<ul>
 *
 * <li>To guarantee lightweight and fast : <ol><li>Guarantee lightweight by
 * design.</li>
 *
 * <li> will be compressed by its repository and cache efficiently in
 * runtime/loadtime by its user/context.</li>
 *
 * <li> Guarantee to have optimistic result with concurent processing method as
 * well as load ballance aware of total processing time.</li>
 *
 * <li> Delta only when transmit via network.</ol></li>
 *
 * <li> To be embedable: <ol><li>Can be inject. All its properties are
 * injectable too.</li>
 *
 * <li> Inspectable. No sensitive data. If not, use another.</li>
 *
 * <li> Declarative. Encourage maximum use of annotations and descriptions. Come
 * with generative, this kind of bean must have a way to be generated fully with
 * the gived descriptions or configurations.</li></ol>
 *
 * <p><ul>
 *
 * <li></li>
 *
 * <li></li>
 *
 * </ul></p>
 *
 * <p>Concepts borrow from Dependency injection fws and EJB bean, managed Actor
 * of AKKA, DObj of OOO.</p>
 *
 * @author CuongNguyen
 */
public @interface Atom {
}
