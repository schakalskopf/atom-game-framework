package sg.atom.logic.input;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import sg.atom.logic.command.CommandController;
import sg.atom.utils.datastructure.collection.primitives.IntArray;
import sg.atom.utils.datastructure.collection.primitives.LongMap;

public final class KeyData {

    private final IntArray pressedKeys;
    private final LongMap<Method> shortcuts;
    private final CommandController controller;
    public static boolean modfierKeyEquality;

    KeyData(CommandController controller) {
        pressedKeys = new IntArray(false, 7);
        shortcuts = ShortcutConfigurator.create(controller);
        this.controller = controller;
    }

    boolean keyDown(int keycode) {
        if (modfierKeyEquality) {
            keycode = rightToLeftModifier(keycode);
        }

        boolean consumed = false;
        try {
            pressedKeys.add(keycode);
            long keyCombination = KeyPacker.pack(pressedKeys.toArray());
            consumed = shortcuts.containsKey(keyCombination);

            if (consumed) {
                shortcuts.get(keyCombination).invoke(controller);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Clearing keys - might help");
            pressedKeys.clear();

            e.printStackTrace();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(
                    "Is the CommandController's visibility too restrictive?", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return consumed;
    }

    boolean keyUp(int keycode) {
        if (modfierKeyEquality) {
            keycode = rightToLeftModifier(keycode);
        }

        pressedKeys.removeValue(keycode);
        return false;
    }

    private static int rightToLeftModifier(int keycode) {
        switch (keycode) {
//            case KeyInput.SHIFT_RIGHT:
//                return KeyInput.SHIFT_LEFT;
//
//            case KeyInput.ALT_RIGHT:
//                return KeyInput.ALT_LEFT;
//
//            case KeyInput.CONTROL_RIGHT:
//                return KeyInput.CONTROL_LEFT;

            default:
                return keycode;
        }
    }
}