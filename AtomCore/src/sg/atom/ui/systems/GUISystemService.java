/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.systems;

import com.google.common.annotations.Beta;
import com.google.common.collect.TreeTraverser;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.ui.GameGUIManager;

/**
 * GUISystem imply a hierachical GUI system. Which is:
 *
 * <ul><li> Can represented by a single top-most class <li>Element based with
 * index <li>Screen base <li>Interaction based</ul>
 *
 * <p>This level of abstraction is higher than one existed in JME3 for Nifty
 * with: Input/Sound/Display/Renderer. And note that all the class is Generic!
 *
 * <p>Support multi GUI system is highly overhead. That's why this
 * implementation is marked as Beta till now. Moved from version 1.0! In v1.1,
 * GameGUISystem is completely support multi service and styles.
 *
 * @author cuong.nguyenmanh2
 */
@Beta
public interface GUISystemService<T, E> extends IGameCycle {

    public TypeToken<T> getTypeToken();

    @Inject
    public T getGUISystemInstance(GameGUIManager manager);

    public E getElementByPath(String path);

    public E getRootElement(GUIScreenInfo<? extends GUISystemService, ?> screen);

    public void activeScreen(GUIScreenInfo<? extends GUISystemService, ?> screen);

    public void deactiveScreen(GUIScreenInfo<? extends GUISystemService, ?> screen);

    public void loadScreen(GUIScreenInfo<? extends GUISystemService, ?> screen);

    public void refreshScreen(GUIScreenInfo<? extends GUISystemService, ?> screen);

    /**
     * Report a specific Interaction or Topic. This can be no op!
     *
     * @param interaction
     */
    public void reportInteraction(GUIInteraction interaction);

    public void doInteraction(GUIInteraction interaction);

    public void interceptInteraction(GUIInteraction interaction);

    public void reportInteraction(GUIInteraction interaction, GUIElement<? extends GUISystemService, ?> element);

    /**
     * Ignore a specific Interaction or Topic. This can be no op!
     *
     * @param interaction
     */
    public void ignoreInteraction(GUIInteraction interaction);

    /**
     * Interaction happen between two system. This can be a no op because JME3
     * input already take care of almost every things.
     *
     * @param interaction
     * @param interaction2
     */
    public void crossInteraction(GUIInteraction interaction, GUIInteraction interaction2);

    public void muted();

    public void refreshChanges();

    public TreeTraverser<T> getElementTraverser();

    public void loadPath(String filePath);

}
