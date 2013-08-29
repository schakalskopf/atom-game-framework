package sg.atom.gameplay;

import sg.atom.gameplay.score.ScoreInfo;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.control.Control;
import sg.atom.stage.GamePlayManager;
import sg.atom.stage.WorldManager;

public class GameLevel {

    protected GamePlayManager gamePlayManager;
    protected WorldManager worldManager;
    protected Node levelNode;
    protected RigidBodyControl control;
    protected LevelInfo info;
    protected GameScene currentScene;
    protected Vector3f startPos;

    public GameLevel(GamePlayManager gamePlay, WorldManager worldManager, String name, String path) {
        this.gamePlayManager = gamePlay;
        this.worldManager = worldManager;
        this.info = new LevelInfo(name, 1, 10, path);
        //this.currentScene = new GameScene(this, gamePlay.getCamera());

    }

    public void loadLevel() {
        if (info.levelModelPath != null && !info.levelModelPath.isEmpty()) {
            levelNode = (Node) worldManager.getAssetManager().loadModel(info.levelModelPath);
            if (levelNode.getChild("startNode") != null) {
                startPos = levelNode.getChild("startNode").getWorldTranslation();

            } else {
                startPos = new Vector3f(0, 4f, 0);
            }
        } else {
            levelNode = new Node("emptyLevelNode");
            //levelNode = worldManager.createSimpleGridLevel();
        }
    }

    public void simpleUpdate(float tpf) {
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
        return control;
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
}
