/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.league;

import java.util.ArrayList;
import sg.atom.gameplay.player.Player;

/**
 * A named group of players, who play in a league.
 *
 * @author atomix
 */
public class GameTeam<T extends Player, L extends GameLeague> {
    public GameLeague league;

    public Integer id;
    public String name;
    public ArrayList<T> members;
    
    public GameTeam(GameLeague league) {
        this.league = league;
    }

    public GameLeague getLeague() {
        return this.league;
    }
}
