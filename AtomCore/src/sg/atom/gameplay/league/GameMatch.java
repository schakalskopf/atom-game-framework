/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.league;

import java.util.ArrayList;
import sg.atom.core.AtomMain;
import sg.atom.gameplay.player.Player;

/**
 *
@author atomix
 */
public class GameMatch<A extends AtomMain, T extends Player> {
    public Integer id;
    public String name;
    public ArrayList<T> players;
}
