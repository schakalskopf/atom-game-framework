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
 * A league of a Game envolve Players in a competive enviroment.
 *
 * @author atomix
 */
public class GameLeague<A extends AtomMain, T extends Player> {

    ArrayList<GameZone> zones;

    public List<GameZone> getZones() {
        return zones;
    }
}
