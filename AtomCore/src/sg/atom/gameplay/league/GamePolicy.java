/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.league;

import java.util.Iterator;
import org.apache.commons.configuration.AbstractConfiguration;
import sg.atom.core.AtomMain;
import sg.atom.gameplay.player.Player;

/**
 * GamePolicy for Player/Match in a kind of Game.
 *
 * <h4>Features: </h4><ul>
 *
 * <li>Hierarchical and casscade!
 *
 * <li>Reactive. use archaius!
 *
 * </ul>
 *
 * @author atomix
 */
public class GamePolicy<GAME extends AtomMain, PLAYER extends Player> extends AbstractConfiguration {
    

    @Override
    protected void addPropertyDirect(String key, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsKey(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getProperty(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator<String> getKeys() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
