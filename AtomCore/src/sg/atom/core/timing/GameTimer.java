/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.timing;

/**
 * A Proxy singleton helper who provide System, JME Context , Application, and
 * GameStage time.
 *
 * @author atomix
 */
public class GameTimer implements TimeProvider {

    private float currentTime;

    @Override
    public float getTime() {
        return currentTime;
    }
}
