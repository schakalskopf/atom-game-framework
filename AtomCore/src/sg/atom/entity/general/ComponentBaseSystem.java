/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.entity.general;

@Deprecated
/**
 * ComponentBaseSystem is the mark for class who intend to contract strictly
 * with the EntitySystem and the EntityManager.
 *
 * <p>In pure ES, System can be anything. This open bridge to more strictly
 * implementation such as Artesmis. By stricly three are 3 things that should be
 * mentioned:
 *
 * <ul> <li>work under a specific, assigned workflow</li>
 *
 * <li>take a specific, assigned workload</li>
 *
 * <li>input and ouput specific, assigned data</li> </ul></p>
 *
 * Read: http://hub.jmonkeyengine.org/forum/topic/entity-system-topic-united/
 *
 * @author atomix
 */
public interface ComponentBaseSystem {
}
