/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.league;

import java.util.ArrayList;
import sg.atom.gameplay.player.Player;

/**
 * A named group of players, ultilty for using player management. Search,filters
 * etc
 *
 * @author atomix
 */
public class GameGroup<T extends Player> {

    public Integer id;
    public String name;
    public ArrayList<T> members;
}
