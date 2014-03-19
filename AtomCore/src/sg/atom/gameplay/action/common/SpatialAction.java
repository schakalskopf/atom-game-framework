/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.action.common;

import sg.atom.core.annotations.Atom;

/**
 * This action affect directly to the scenegraph. If you going to make
 * transaprent activities with scenegraph which is not affect to gameplay, try
 * GameEffect instead.
 *
 * <p>This is a "common" implementation. Actor usually want to affect the
 * enviroment, including the scene always. That's said, this class warp the
 * scenegraph affection in the same conccurent manner which is very useful for
 * game that have "async" action with the Applicaion update cycle. This just a
 * warper for simplize dataflow and better monitoring!
 *
 * <p>For example:
 *
 * @author cuong.nguyenmanh2
 */
@Atom
public abstract class SpatialAction {
}
