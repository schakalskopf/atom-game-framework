package sg.atom.gameplay;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import sg.atom.stage.GameScene;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.control.Control;
import java.util.Properties;
import sg.atom.core.AbstractManager;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.core.lifecycle.ManagableObject;
import sg.atom.stage.WorldManager;

/**
 * GameLevel (Common Implementation) is a Common pattern seen in Game.
 *
 * <p>Each level represent as a challenge/quest associated with a
 * scenery/enviroment for player to going through. Some game may have only one
 * level (eg:Chess) so basiclly this implementation support simplest but
 * expandable novel gameplay</p>
 *
 * <p> Level data can be embed in the levelNode or expose directly via internal
 * datas. This implementation assume that level have some kind of Data to expose
 * and some of them are private. So apply a Map interface over GameLevel is over
 * enginering. Instead, GameLevel support a method to get data out of it but not
 * generic.
 *
 * <p>Is the central of references for Stage, World, Sound, Select, Spawning and
 * their sub managers, but mostly recomended to access via Stage,World at the
 * moment.
 *
 * @author cuong.nguyenmanh2
 */
public class GameLevel implements IGameCycle, ManagableObject {

    protected GamePlayManager gamePlayManager;
    protected WorldManager worldManager;
    protected Node levelNode;
    protected RigidBodyControl physicControl;
    protected LevelInfo info;
    protected GameScene currentScene;
    // Positonal, decorations and additional datas for the GameLevel
    protected Vector3f startPos;

    public GameLevel(GamePlayManager gamePlay, WorldManager worldManager, String name, String path) {
        this.gamePlayManager = gamePlay;
        this.worldManager = worldManager;
        this.info = new LevelInfo(name, 1, 10, path);
        //this.currentScene = new GameScene(this, gamePlay.getCamera());
    }

    public void loadLevel() {
        preLoadLevel();
        if (info.levelModelPath != null && !info.levelModelPath.isEmpty()) {
            levelNode = (Node) worldManager.getAssetManager().loadModel(info.levelModelPath);
            if (levelNode.getChild("startNode") != null) {
                startPos = levelNode.getChild("startNode").getWorldTranslation();

            } else {
                startPos = new Vector3f(0, 4f, 0);
            }
        } else {
            levelNode = new Node("emptyLevelNode");
        }
        postLoadLevel();
    }

    public void simpleUpdate(float tpf) {
    }

    public void preLoadLevel() {
    }

    public void postLoadLevel() {
    }

    public Node getLevelNode() {
        return levelNode;
    }

    public Node getModelNode() {
        return levelNode;
    }

    public Control getCollisionControl() {
        return physicControl;
    }

    public GameScene getCurrentScene() {
        return currentScene;
    }

    public Vector3f getStartPos() {
        return startPos;
    }

    public void setStartPos(Vector3f startPos) {
        this.startPos = startPos;
    }

    public LevelInfo getInfo() {
        return info;
    }

    public Object getData(Object key) {
        return null;
    }

    @Override
    public void init() {
    }

    @Override
    public void load() {
        loadLevel();
    }

    @Override
    public void config(Properties props) {
    }

    @Override
    public void update(float tpf) {
        simpleUpdate(tpf);
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

    @Override
    public void init(Application app, AbstractManager... managers) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void load(AssetManager assetManager) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void config(Object... params) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
