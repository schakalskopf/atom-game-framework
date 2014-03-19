/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic.trigger.spatial;

import sg.atom.logic.trigger.ConditionalTrigger;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import sg.atom.logic.trigger.TriggerListener;
import sg.atom.stage.WorldManager;

/**
 * SpatialTrigger is the default implementation of trigger which related to
 * Spatial.
 *
 * <p>By the default implentation use traditional event broadcasting methods via
 * Listener (Observers pattern).
 *
 * <p> From version 1.0, the EventBus methods is supported.
 *
 * @author cuong.nguyenmanh2
 */
public abstract class SpatialTrigger extends ConditionalTrigger {

    /* Spatial information */
    protected WorldManager worldManager;
    protected Spatial spatial;
    // EVENT HANDLE
    @Inject
    EventBus eventBus;
    ArrayList<TriggerListener> listeners = new ArrayList<TriggerListener>();
    boolean useEventBus = true;
    private int EVENT_ENTER = 0;
    private int EVENT_EXIT = 1;

    @Deprecated
    public SpatialTrigger() {
    }

    public SpatialTrigger(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public SpatialTrigger(Spatial spatial, EventBus eventBus) {
        this.spatial = spatial;
        this.eventBus = eventBus;
    }

    public SpatialTrigger(WorldManager worldManager, Spatial spatial, EventBus eventBus) {
        this.worldManager = worldManager;
        this.spatial = spatial;
        this.eventBus = eventBus;
    }

    public void setSpatial(Spatial spatial) {
        this.spatial = spatial;
    }

    public Spatial getSpatial() {
        return spatial;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    @Deprecated
    public void addListener(TriggerListener aListener) {
        listeners.add(aListener);
    }

    public void removeListener(TriggerListener aListener) {
        listeners.remove(aListener);
    }

    @Override
    public void actived() {
        broadcastEvent(EVENT_ENTER);
    }

    @Override
    public void deactived() {
        broadcastEvent(EVENT_ENTER);
    }

    /**
     * Event broadcasting mechanism FIXME: Use GameEventManager instead!
     */
    public void broadcastEvent(int type) {
        if (!useEventBus) {
            for (TriggerListener tl : listeners) {
                if (type == EVENT_ENTER) {
                    tl.enter(this);
                } else {
                    tl.exit(this);
                }
            }
        } else {
            if (eventBus != null) {
                eventBus.post(this);
            } else {
                throw new IllegalStateException("Trigger Eventbus service is null, please set it before using Trigger eventbus!");
            }
        }
    }
}
