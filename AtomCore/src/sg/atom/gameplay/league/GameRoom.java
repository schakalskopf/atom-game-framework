/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.league;

import java.util.ArrayList;
import sg.atom.core.AtomMain;
import sg.atom.gameplay.player.Player;

/**
 * A kind of collection of players which under a specific about the enviroment
 * or style of gameplay. This is pre match group of players.
 *
 * @author atomix
 */
public class GameRoom<GAME extends AtomMain, PLAYER extends Player> {

    public Integer id;
    public String name;
    public PLAYER host;
    public ArrayList<PLAYER> members;
}
