package sg.atom.logic.input;

import java.lang.reflect.Method;
import sg.atom.logic.annotations.Command;
import sg.atom.logic.annotations.Shortcut;
import sg.atom.logic.command.CommandController;
import sg.atom.logic.command.CommandManager;
import sg.atom.utils.datastructure.collection.Array;
import sg.atom.utils.datastructure.collection.primitives.LongMap;

final class ShortcutConfigurator {

    private ShortcutConfigurator() {
    }

    static LongMap<Method> create(CommandController controller) {
        LongMap<Method> shortcutToMethodMap = new LongMap<Method>();

        Array<Method> methods = new Array<Method>();
        for (Method method : controller.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                methods.add(method);
            }
        }

        if (controller.commandComparator() != null) {
            methods.sort(controller.commandComparator());
        }

        for (Method method : methods) {
            assignKeyToMethod(shortcutToMethodMap, method);
            CommandManager.instance.addCommand(method, controller);
        }

        return shortcutToMethodMap;
    }

    private static void assignKeyToMethod(LongMap<Method> shortcutToMethodMap, Method method) {
        //KeyFormatter formatter = new KeyFormatter();

        Command command = method.getAnnotation(Command.class);
        for (Shortcut shortcut : command.bindings()) {
//            if (CommandManager.instance.debug) {
//                System.out.printf("Adding shortcut for command '%s': '%s'\n", command.name(), formatter.parse(shortcut.value()));
//            }

            shortcutToMethodMap.put(KeyPacker.pack(shortcut.value()), method);
        }
    }
}
