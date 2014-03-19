/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.timing;

/**
 * TimeProvider is decoration/or a mark for object participate in real-time
 * application as the Provider of Timing job. This interface abstract out the
 * resolution and such, as JDK Timer and JME Timer did! The GameTimer
 * implementation do more jobs.
 *
 * <p>
 *
 * @author cuong.nguyenmanh2
 */
public interface TimeProvider {

    public float getTimeInSeconds();
}
