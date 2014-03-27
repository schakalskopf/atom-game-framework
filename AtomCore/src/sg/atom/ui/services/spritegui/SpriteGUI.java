/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.services.spritegui;

import com.google.common.collect.TreeTraverser;
import com.google.common.reflect.TypeToken;
import com.jme3.scene.Node;
import java.util.Properties;
import sg.atom.fx.sprite.Sprite;
import sg.atom.ui.GameGUIManager;
import sg.atom.ui.systems.GUIElement;
import sg.atom.ui.systems.GUIInteraction;
import sg.atom.ui.systems.GUIScreenInfo;
import sg.atom.ui.systems.GUISystemService;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class SpriteGUI implements GUISystemService<SpriteGUI, Sprite> {

    Node guiRootNode;
    Node spriteGUIRootNode;
    
    public SpriteGUI(GameGUIManager guiManager) {
        
    }

    @Override
    public TypeToken<SpriteGUI> getTypeToken() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SpriteGUI getGUISystemInstance(GameGUIManager manager) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Sprite getElementByPath(String path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Sprite getRootElement(GUIScreenInfo<? extends GUISystemService, ?> screen) {
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
    public void reportInteraction(GUIInteraction interaction) {
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
    public TreeTraverser<SpriteGUI> getElementTraverser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadPath(String filePath) {
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
}
