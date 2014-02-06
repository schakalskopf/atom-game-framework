/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic.command;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import sg.atom.stage.WorldManager;

/**
 *
@author atomix
 */
/**
 * Handles the command queue of an AI entity.
 * @author normenhansen
 */
public class CommandControl implements Control {

    protected Spatial spatial;
    protected boolean enabled = true;
    protected LinkedList<Command> commands = new LinkedList<Command>();
    protected Command defaultCommand;
    protected long playerId;
    protected long entityId;
    protected WorldManager world;

    public CommandControl(WorldManager world, long playerId, long entityId) {
        this.world = world;
        this.playerId = playerId;
        this.entityId = entityId;
    }

    public Command initializeCommand(Command command) {
        return command.initialize(world, playerId, entityId, spatial);
    }

    public void addCommand(Command command) {
        command.setRunning(true);
        for (int i = 0; i < commands.size(); i++) {
            Command command1 = commands.get(i);
            if (command1.getPriority() < command.getPriority()) {
                commands.add(i, command);
                return;
            }
        }
        commands.add(command);
    }

    public void removeCommand(Command command) {
        command.setRunning(false);
        commands.remove(command);
    }

    public void clearCommands() {
        for (Iterator<Command> it = commands.iterator(); it.hasNext();) {
            Command command = it.next();
            command.setRunning(false);
        }
        commands.clear();
    }

    public void update(float tpf) {
        if (!enabled) {
            return;
        }
        for (Iterator<Command> it = commands.iterator(); it.hasNext();) {
            Command command = it.next();
            //do command and remove if returned true, else stop processing
            Command.State commandState = command.doCommand(tpf);
            switch (commandState) {
                case Finished:
                    command.setRunning(false);
                    it.remove();
                    break;
                case Blocking:
                    return;
                case Continuing:
                    break;
            }
        }
    }

    public void setSpatial(Spatial spatial) {
        if (spatial == null) {
            if (this.spatial != null) {
            }
            this.spatial = spatial;
            return;
        }
        this.spatial = spatial;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void render(RenderManager rm, ViewPort vp) {
    }

    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }
}
