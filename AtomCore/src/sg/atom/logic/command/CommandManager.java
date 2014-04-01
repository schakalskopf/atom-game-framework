/*
 * Copyright 2012 Adrian Papari
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sg.atom.logic.command;

import java.lang.reflect.Method;
import java.util.Iterator;
import sg.atom.logic.input.KeyData;
import sg.atom.utils.datastructure.collection.Array;

/**
 * Will at a later point facilitate overriding default shortcuts.
 *
 * //FIXME: This singleton should be replace with AtomSingleton!
 */
public enum CommandManager {

    instance;
    private final Array<MethodBoundCommand> commands;
    public boolean debug = false;

    private CommandManager() {
        commands = new Array<MethodBoundCommand>();
    }

    @SuppressWarnings("static-method")
    public void setSingleModifierKeys(boolean lrModifierEquality) {
        KeyData.modfierKeyEquality = lrModifierEquality;
    }

    @SuppressWarnings("static-method")
    public boolean isSingleModifierKeys() {
        return KeyData.modfierKeyEquality;
    }

    public void addCommand(Method method, CommandController instance) {
        commands.add(new MethodBoundCommand(method, instance));
    }

    public void remove(CommandController instance) {
        Iterator<MethodBoundCommand> iterator = commands.iterator();
        while (iterator.hasNext()) {
            MethodBoundCommand command = iterator.next();
            if (command.getCommandInstance() == instance) {
                iterator.remove();
            }
        }
    }

    public void remove(Class<? extends CommandController> klazz) {
        Iterator<MethodBoundCommand> iterator = commands.iterator();
        while (iterator.hasNext()) {
            MethodBoundCommand command = iterator.next();
            if (command.getCommandInstance().getClass() == klazz) {
                iterator.remove();
            }
        }
    }

    public void removeAll() {
        commands.clear();
    }

    public Array<MethodBoundCommand> getCommands() {
        return commands;
    }
}
