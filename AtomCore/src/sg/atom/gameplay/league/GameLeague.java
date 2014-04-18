/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.league;

import java.util.ArrayList;
import java.util.List;
import sg.atom.core.AtomMain;
import sg.atom.gameplay.player.Player;

/**
 * A league of a Game envolve Players in a competitive enviroment, that's
 * Matches.
 *
 * <h4>Designs:</h4> The league should be a highly configurable low latency
 * object. It depend on Netflix Archaius to being dynamic and reactive at the
 * same time.
 *
 * <h4>Features:</h4>
 *
 * @author atomix
 */
public class GameLeague<GAME extends AtomMain, PLAYER extends Player> {

    ArrayList<GameZone> zones;

    public List<GameZone> getZones() {
        return zones;
    }
}
