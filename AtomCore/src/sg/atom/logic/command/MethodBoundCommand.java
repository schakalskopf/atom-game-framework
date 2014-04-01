/*
 * Copyright 2012 Adrian Papari
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sg.atom.logic.command;

import java.lang.reflect.Method;
import sg.atom.logic.annotations.Command;
import sg.atom.logic.annotations.Shortcut;
import sg.atom.logic.input.KeyPacker;

/**
 * Object representation of the {@link Command} annotation.
 */
public class MethodBoundCommand extends BoundCommand {

    private final Method method;

    MethodBoundCommand(Method method, CommandController instance) {
        super(instance);
        this.method = method;
    }

    protected void bind(Object target) {
        if (!method.isAnnotationPresent(Command.class)) {
            throw new RuntimeException("No @Command on " + method.getName());
        }

        command = method.getAnnotation(Command.class);

        for (Shortcut shortcut : command.bindings()) {
            shortcuts.add(KeyPacker.pack(shortcut.value()));
        }
    }

    public Method getMethod() {
        return method;
    }
}
