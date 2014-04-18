/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.context;

import javolution.context.AbstractContext;
import sg.atom.core.execution.TaskManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public abstract class AtomContext extends AbstractContext {

    /**
     * This class should not be instantiated.
     */
    private AtomContext() {
    }

    /**
     * Returns the {@code ChannelManager} for use by the current application.
     * The object returned is not serializable, and should not be stored as part
     * of a managed object.
     *
     * @return	the {@code ChannelManager} for the current application
     * @throws	ManagerNotFoundException if the {@code ChannelManager} cannot be
     * located
     */
//    public static ChannelManager getChannelManager() {
////        try {
////            return InternalContext.getManagerLocator().getChannelManager();
////        } catch (IllegalStateException ise) {
////            throw new ManagerNotFoundException("ManagerLocator is " +
////                                               "unavailable", ise);
////        }
//        return null;
//    }
    /**
     * Returns the {@code DataManager} for use by the current application. The
     * object returned is not serializable, and should not be stored as part of
     * a managed object.
     *
     * @return	the {@code DataManager} for the current application
     * @throws	ManagerNotFoundException if the {@code DataManager} cannot be
     * located
     */
    public static DataManager getDataManager() {
//        try {
//            return InternalContext.getManagerLocator().getDataManager();
//        } catch (IllegalStateException ise) {
//            throw new ManagerNotFoundException("ManagerLocator is " +
//                                               "unavailable", ise);
//        }
        return null;
    }

    /**
     * Returns the {@code TaskManager} for use by the current application. The
     * object returned is not serializable, and should not be stored as part of
     * a managed object.
     *
     * @return	the {@code TaskManager} for the current application
     * @throws	ManagerNotFoundException if the {@code TaskManager} cannot be
     * located
     */
    public static TaskManager getTaskManager() {
//        try {
//            return InternalContext.getManagerLocator().getTaskManager();
//        } catch (IllegalStateException ise) {
//            throw new ManagerNotFoundException("ManagerLocator is " +
//                                               "unavailable", ise);
//        }
        return null;
    }

    /**
     * Returns a manager of the specified type for use by the current
     * application. The object returned is not serializable, and should not be
     * stored as part of a managed object.
     *
     * @param	<T> the type of the manager
     * @param	type a class representing the type of the manager
     * @return	the manager of the specified type for the current application
     * @throws	ManagerNotFoundException if no manager is found for the specified
     * type
     */
    public static <T> T getManager(Class<T> type) {
//        try {
//            return InternalContext.getManagerLocator().getManager(type);
//        } catch (IllegalStateException ise) {
//            throw new ManagerNotFoundException("ManagerLocator is " +
//                                               "unavailable", ise);
//        }
        return null;
    }
}
