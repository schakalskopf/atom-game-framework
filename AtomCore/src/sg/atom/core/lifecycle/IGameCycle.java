/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.lifecycle;

import java.util.Properties;

/**
 * A GameCycle interface for attendant who join a strict routine of
 * init/load/config/update/finish.
 *
 * Open hook to use Atom's Progress which can ultilize GPar Task or Guava
 * Service and also JME3's AppState.
 *
 * @author atomix
 */
public interface IGameCycle {

    public static enum LifeCyclePhase {

        PhaseInit, PhaseLoad, PhaseConfig, PhaseUpdate, PhaseFinish, PhaseStartGame
    }

    public void init();

    public void load();

    public void config(Properties props);

    public void update(float tpf);

    public void finish();

    // Monitoring
    public LifeCyclePhase getCurrentPhase();

    public float getProgressPercent(LifeCyclePhase aPhrase);
}
