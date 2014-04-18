/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.managers;

import java.util.HashMap;
import sg.atom.gameplay.GameItem;
import sg.atom.gameplay.league.GameLeague;
import sg.atom.gameplay.player.Player;

/**
 * GamePlayerManager manage {@link Player}s of the {@link GameLeague}.
 * GamePlayerManager bridge between the abstract and the common (practical)
 * implementation of player management, that's bridge between League API and
 * Party API (which for human and users)
 *
 * <p>Features:<ul>
 *
 * <li>Manage members, policies between {@link GameTeam}
 *
 * <li>Manage scheduler of matches, reservation for matches, request for matches
 * between players, etc...
 *
 * <li>Manage player's info, leaderboard and social aspects.
 *
 * </ul>
 *
 * @author atomix
 */
public class GamePlayerManager {

    HashMap<Long, GameItem> gameItemList = new HashMap<Long, GameItem>();
}
