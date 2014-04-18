/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic.command;

import sg.atom.logic.annotations.Command;
import sg.atom.utils.datastructure.collection.primitives.LongArray;

/**
 *
 * @author CuongNguyen
 */
public class BoundCommand {

    protected Command command;
    protected Object instance;
    protected LongArray shortcuts;

    public BoundCommand(Command command, Object instance, LongArray shortcuts) {
        this.command = command;
        this.instance = instance;
        this.shortcuts = shortcuts;
    }

    public BoundCommand(CommandController instance) {
        this.instance = instance;
        shortcuts = new LongArray();
        //bind
    }

    protected void bind(Object target) {
    }

    protected void unbind() {
    }
    //    public Array<String> formatShortcuts(KeyFormatter formatter) {
    //        Array<String> keys = new Array<String>();
    //        for (int i = 0; shortcuts.size > i; i++) {
    //            keys.add(formatter.parse(shortcuts.get(i)));
    //        }
    //        return keys;
    //    }

    public boolean acceptsShortcut(long packedKeys) {
        return shortcuts.contains(packedKeys);
    }

    public Object getCommandInstance() {
        return instance;
    }

    public String getDescription() {
        return command.description();
    }

    public String getName() {
        return command.name();
    }

    public LongArray getShortcuts() {
        return shortcuts;
    }
}
