/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.visibility;

import io.netty.channel.Channel;
import org.apache.commons.math3.geometry.partitioning.Region;
import sg.atom.logic.trigger.Trigger;
import sg.atom.utils.event.EventChannel;
import sg.atom.world.enviroment.AtomEnviroment;

/**
 * Concept borrow from MMO games.
 *
 * <p>A zone is an isolated region we interactions and entities of one have no
 * connection and ability to reach, or aware, knowledge thoose in another Zone.
 * </p>
 *
 * <p>Zone are non overlap and contain a PVSTree. If embed in a GameLevel, Zones
 * are automaticly active and deactive by WorldVisibilityManager and
 * EnviromentManager.
 *
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
