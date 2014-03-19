/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.timing;

import com.jme3.system.Timer;

/**
 * A "Proxy" singleton helper who provide System, JME Context , Application, and
 * GameStage time.
 *
 * <p>Use Guava stop watch underneath.
 *
 * @author atomix
 */
public class GameTimer extends Timer implements TimeProvider {

    private float currentTime;

    public float getTimeInSeconds() {
        return currentTime;
    }

    @Override
    public long getTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long getResolution() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getFrameRate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getTimePerFrame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
