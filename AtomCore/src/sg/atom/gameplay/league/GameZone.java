/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.league;

import sg.atom.core.AtomMain;
import sg.atom.gameplay.player.Player;

/**
 * Abstract grouping methods to partitioning players acording to some category
 * or criteria. Concepts borrow from MMO.
 *
 * <h4>Zone concepts:</h4> See threering's Clyde Tudey + Narya Crowd + vilya's
 *
 * Whirled
 *
 * <p>https://github.com/threerings/narya/
 *
 * <p>https://github.com/threerings/clyde/
 *
 * <p>https://github.com/threerings/vilya/
 *
 * <h4>What it try to solve?</h4> "Zone" focus in sort, partition, tag, cluster
 * players into separate topics, parts, groups like sets, graphs.
 *
 * <p>The similarities - differences, interacting - updating - exchanging
 * between zones raised various problems: geometric or perceptional. The lower
 * level of GameZone is AtomZone in World package which take are of the geometry
 * level, that's the interaction between moving shapes for example. Zone solve
 * the perceptional and more abstract and structured level of the problem, so
 * it's fair enough to call Zone is a "context" for interactions.
 *
 * <h4>Context</h4> is fundamental concept in Atom framework. You can read about
 * it here. Zone is a context of notions of players. Character (the Notion) is a
 * representaion of player, a proxy of player interactions. This proxy in turn
 * is a mediator so its still remain when the actual owner changed, like when
 * the actual Character's Model change level of detail, or Player connection
 * missed (out of server) in a fraction of time.
 *
 * <h4>The interactions:</h4>
 *
 * @see Context
 *
 * @author atomix
 */
public abstract class GameZone<GAME extends AtomMain, PLAYER extends Player> {
}
