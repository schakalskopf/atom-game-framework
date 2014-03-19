/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import com.jme3.asset.AssetManager;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.scene.Node;
import java.util.Properties;
import java.util.logging.Logger;
import sg.atom.core.AbstractManager;
import sg.atom.core.AtomMain;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.gameplay.player.Player;
import sg.atom.stage.StageManager;
import sg.atom.stage.WorldManager;
import sg.atom.ui.GameGUIManager;

/**
 * GamePlayManager is the manager to manage all gameplay aspects.
 *
 * <p>It's the manager that make a game follow a genre, which make input, point
 * of view, action, enviroment and mood ..., right for the game-genre and plot.
 * It has some overlap functions with StageManager but it care more about the
 * score, the winning, the mood... and StageManager don't even know about them.
 *
 * It also knows everything about the players and items,triggers , NPC, skill and
 * everythings affect the game play.</p>
 *
 * This implementation has some assumptions: <ul>
 *
 * <li>At least one main player in the local system. Wrong with a stand-alone
 * server.</li>
 *
 * <li>The game is Level base.</li>
 *
 * <li></li> </ul>
 *
 * <p>The main job of GamePlayManager is taking care of: <ul>
 *
 * <li>Main character of the player position</li>
 *
 * <li>Spawning of enemies</li> </ul> 
 * 
 * Sub-managers:
 *
 * Item Manager: Place and manage items and adward
 *
 * @author atomix
 */
public class GamePlayManager extends AbstractManager implements IGameCycle {
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

    protected GamePlayManager() {
        //For singleton!
    }

    public GamePlayManager(AtomMain app) {
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.inputManager = app.getInputManager();
        this.stageManager = app.getStageManager();
        this.gameGUIManager = app.getGameGUIManager();
    }

    public void lazyInit(AtomMain app) {
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.inputManager = app.getInputManager();
        this.stageManager = app.getStageManager();
        this.gameGUIManager = app.getGameGUIManager();
    }

    public void initGamePlay() {
        initGamePlay(null);
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

    public GameLevel getCurrentLevel() {
        return currentLevel;
    }

    public Player getMainPlayer() {
        return mainPlayer;
    }

    public Logger getLogger() {
        return logger;
    }

    public Player getCurrentPlayer() {
        return mainPlayer;
    }

    @Override
    public void init() {
        initGamePlay();
    }

    @Override
    public void load() {
        loadGamePlay();
    }

    @Override
    public void config(Properties props) {
        configGamePlay();
    }

    @Override
    public void finish() {
    }

    @Override
    public LifeCyclePhase getCurrentPhase() {
        return null;
    }

    @Override
    public float getProgressPercent(LifeCyclePhase aPhrase) {
        return 0;
    }
}
