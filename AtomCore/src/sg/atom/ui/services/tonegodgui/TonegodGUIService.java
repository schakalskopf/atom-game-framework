/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.services.tonegodgui;

import com.google.common.collect.TreeTraverser;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import java.util.Properties;
import sg.atom.ui.GameGUIManager;
import sg.atom.ui.systems.GUIElement;
import sg.atom.ui.systems.GUIInteraction;
import sg.atom.ui.systems.GUIScreenInfo;
import sg.atom.ui.systems.GUISystemService;
import tonegod.gui.core.Screen;
import tonegod.gui.core.Element;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class TonegodGUIService extends AbstractExecutionThreadService implements GUISystemService<Screen, Element> {

    @Override
    protected void run() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TypeToken getTypeToken() {
        return TypeToken.of(Screen.class);
    }

    @Override
    public Screen getGUISystemInstance(GameGUIManager manager) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void reportInteraction(GUIInteraction interaction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void ignoreInteraction(GUIInteraction interaction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void crossInteraction(GUIInteraction interaction, GUIInteraction interaction2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void muted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void refreshChanges() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void load() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void config(Properties props) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void finish() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LifeCyclePhase getCurrentPhase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getProgressPercent(LifeCyclePhase aPhrase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TreeTraverser<Screen> getElementTraverser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Element getRootElement(GUIScreenInfo screen) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void activeScreen(GUIScreenInfo<? extends GUISystemService, ?> screen) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deactiveScreen(GUIScreenInfo<? extends GUISystemService, ?> screen) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadScreen(GUIScreenInfo<? extends GUISystemService, ?> screen) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void refreshScreen(GUIScreenInfo<? extends GUISystemService, ?> screen) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Element getElementByPath(String path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void doInteraction(GUIInteraction interaction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void interceptInteraction(GUIInteraction interaction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void reportInteraction(GUIInteraction interaction, GUIElement<? extends GUISystemService, ?> element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadPath(String filePath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
