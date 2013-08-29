/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage;

import sg.atom.core.StageManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.scene.Node;

import java.util.logging.Logger;
import sg.atom.core.GameGUIManager;
import sg.atom.gameplay.GameLevel;
import sg.atom.gameplay.player.Player;
import sg.atom.core.AtomMain;

/**
 *
 * @author hungcuong
 * @param GamePlayManager is
 */
public class GamePlayManager {
    // the Global var

    protected AtomMain app;
    protected AssetManager assetManager;
    protected InputManager inputManager;
    protected GameGUIManager gameGUIManager;
    protected Node rootNode;
    protected Node guiNode;
    protected FlyByCamera flyCam;
    protected StageManager stageManager;
    //        
    // Level --------------------------------------------------

    protected GameLevel currentLevel;
    protected Player mainPlayer;
    private static final Logger logger = Logger.getLogger(GamePlayManager.class.getName());

    // GAME PLAY MANAGER
    // It's the manager that make a game follow a genre
    // which make input, point of view, action, enviroment and mood ... 
    // right for the game-genre and plot
    // It know every about the player and items,triggers , NPC, skill
    // The things that affect the game play
    // The main job of GamePlayManager is taking care of:
    // + Main character of the player position
    // + Spawning of enemies
    // Sub-manager
    // Item Manager: Place and manage items and adward
    public GamePlayManager(AtomMain app) {
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.inputManager = app.getInputManager();
        this.stageManager = app.getStageManager();
        this.gameGUIManager = app.getGameGUIManager();
    }

    public void initGamePlay(GameLevel level) {
        this.rootNode = app.getRootNode();
        this.guiNode = app.getGuiNode();
        this.flyCam = app.getFlyByCamera();
        this.currentLevel = level;
    }

    public void loadGamePlay() {
        // load all GameCharacter models
        // WorldManager will help but
        // for simple case , this 's enough
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Node getGuiNode() {
        return guiNode;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public GameGUIManager getGameGUIManager() {
        return gameGUIManager;
    }

    // CONFIG =====================
    public void configGamePlay() {
    }

    public void startLevel(GameLevel level) {
    }
    // UPDATE =====================

    public void update(float tpf) {
    }
// GETTER & SETTER ==============

    public WorldManager getWorldManager() {
        return stageManager.getWorldManager();
    }
    public StageManager getStageManager() {
        return stageManager;
    }
    GameLevel getCurrentLevel() {
        return currentLevel;
    }

    Player getMainPlayer() {
        return mainPlayer;
    }

    public Logger getLogger() {
        return logger;
    }

    public Player getCurrentPlayer() {
        return mainPlayer;
    }
}
