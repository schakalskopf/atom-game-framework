/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.lifecycle;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface IGameCycle {

    public static enum LifeCyclePhase {

        PhraseInit, PhraseLoad, PhraseConfig, PhraseUpdate, PhraseFinish, PhraseStartGame
    }

    public void init();

    public void load();

    public void config();

    public void update(float tpf);

    public void finish();

    public void startGame();

    public ProcessInfo getCurrentProcess();

    public boolean isProcessWatcher();

    public boolean isFinish(LifeCyclePhase aPhrase);

    public boolean isReady(LifeCyclePhase aPhrase);
}
