/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world;

import io.netty.channel.Channel;
import org.apache.commons.math3.geometry.partitioning.Region;
import sg.atom.logic.trigger.Trigger;
import sg.atom.utils.event.EventChannel;
import sg.atom.world.enviroment.AtomEnviroment;

/**
 * A Space can be seen as a "non-overlapped" region and container of Points,
 * Shapes, Trees, Graphs. Concept borrow from MMO games.
 *
 * <p>A zone is an isolated region we interactions and entities of one have no
 * connection and ability to reach, or aware, knowledge thoose in another Zone.
 * </p>
 *
 * <p>Zone is not overlapped. If embed in a GameLevel, Zones are automaticly
 * active and deactive by WorldVisibilityManager and EnviromentManager.
 *
 * <p>Nestable counter-part of Zone is Region, Zone contains Regions. Region
 * which use to define "Place", Region contains Points and Shape. Region can be
 * use in various application: from navigation (AI, steering) to spatial
 * indexing. Region manage points, shapes or any presentation of them. So
 * Spatial, Character or common implementation in Atom framework capable to be
 * contained in it.
 *
 * <p>Zone contains (or can be denoted with) Points, BSP, PVSTree, RTree,
 * KDtree, topology graph of regions... This facilities are available on-demand
 * and after that will be reactive (automatic updating) + adaptive (self
 * organizing) + progessive (update by slices)
 *
 * <p>In Atom2D package, GridMap is a replacement of AtomZone. They will both be
 * in a same interface Zone in the near future.
 *
 * @author cuong.nguyenmanh2
 */
public class AtomZone {

    public AtomEnviroment getEnviroment() {
        return null;
    }

    public Trigger getEnteringTrigger() {
        return null;
    }

    public Trigger getExitingTrigger() {
        return null;
    }

    public Region getRegion() {
        return null;
    }

    public EventChannel getEventChannel() {
        return null;
    }

    public Channel getChannel() {
        return null;
    }
}
