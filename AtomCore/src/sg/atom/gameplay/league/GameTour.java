/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.league;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import sg.atom.core.AtomMain;
import sg.atom.gameplay.player.Player;

/**
 * Utility for schedule matchs in a league.
 *
 * <h4>Features:</h4><ul>
 *
 * <li>Routine (after matches): Rank-adjustment tournament, elimination
 * tournament, scoring tournament.
 *
 * <li>Schedulers (each match/ turn):
 *
 * <li>
 *
 * <li> </ul>
 *
 *
 * @author atomix
 */
public class GameTour<GAME extends AtomMain, PLAYER extends Player> {

    public static enum CommonGameTourRoutine {

        NORMAL, ELIMINATE, RANK, SCORE
    }
    List<GameMatch> matches;

    public static class Round extends ArrayList<GameMatch> {
    }
}
