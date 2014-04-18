/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.lod;

import com.google.common.collect.TreeTraverser;

/**
 * Cluster/collapse tree node to tweak the detail level of underlying data
 * structure. This is the tweaker for well-defined semantic data.
 *
 * <p>Can be implemented as Behaviour trees, HFSM, HTN.
 *
 * <p>By understanding the structure of the underlying data, the hierarchical
 * tweaker can travel, mark and doing operations upon the entities graph. For
 * example: <ul>
 *
 * <li>Collapse children node to parent node
 *
 * <li>Expand node back to serveral children
 *
 * <li>[Context concepts] Import/export nodes from other hierarchy. This is
 * important for zone changing situatation. We some part of outside zone (like
 * some characters for ex) enter the curernt zone. This will cause the
 * hierarchical tweaker to decide we to put those new node.
 *
 * </ul>
 *
 * <p>In contrast, the {@link ProbabilityTweaker} depend on probability data and
 * statistics to decide what to do. In fact, this two can blend at some degree
 * but the implementations is for future development.
 *
 * @author cuong.nguyenmanh2
 */
public class HierarchicalTweaker<T> implements DetailTweaker<TreeTraverser<T>> {
}
