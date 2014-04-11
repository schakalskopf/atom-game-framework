/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.common;

import sg.atom.corex.camera.CamUtils;
import sg.atom.corex.scenegraph.shape.ShapeUtil;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import sg.atom.core.AbstractManager;

/**
 * CommonTool is the singleton reference beside of Global in an editing
 * enviroment.
 *
 * <p>It's the bootstrap module to run the whole application.
 *
 * @author hungcuong
 */
public class CommonTool {

    private static CommonTool defaultInstance;
    /**
     * A private Constructor prevents any other class from instantiating.
     */
    // Engine Level
    private SimpleApplication app;
    private AssetManager assetManager;
    private Node rootNode;
    private Node guiNode;
    private InputManager inputManager;
    private RenderManager renderManager;
    // Tool Level
    // ___ Common
    private CamUtils camUtil;
    private Camera currentCam;
    private ShapeUtil shapeUtil;
    // ___

//    private EditorSelectionManager selectionManager;
//    private HelperManager helperManager;
    private CommonTool(SimpleApplication app) {

        // Low Level
        // Engine Level
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.inputManager = app.getInputManager();
        this.rootNode = app.getRootNode();
        this.guiNode = app.getGuiNode();
        this.renderManager = app.getRenderManager();
        // App Level
        // Use common discovery to search for manager and plugins.

        // Tool Level
        this.shapeUtil = ShapeUtil.getDefault();
        this.camUtil = new CamUtils(getCurrentCam(), app.getFlyByCamera(), getInputManager());

    }

    public static synchronized CommonTool getDefault(SimpleApplication app) {
        if (defaultInstance == null) {
            if (app == null) {
                throw new NullPointerException("You don't init the SimpleApplication that Use the tool !");
            } else {
                defaultInstance = new CommonTool(app);
            }
        }
        return defaultInstance;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * @return the app
     */
    public SimpleApplication getApp() {
        return app;
    }

    /**
     * @param app the app to set
     */
    public void setApp(SimpleApplication app) {
        this.app = app;
    }

    /**
     * @return the assetManager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * @param assetManager the assetManager to set
     */
    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    /**
     * @return the rootNode
     */
    public Node getRootNode() {
        return rootNode;
    }

    /**
     * @param rootNode the rootNode to set
     */
    public void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * @return the guiNode
     */
    public Node getGuiNode() {
        return guiNode;
    }

    /**
     * @param guiNode the guiNode to set
     */
    public void setGuiNode(Node guiNode) {
        this.guiNode = guiNode;
    }

    /**
     * @return the inputManager
     */
    public InputManager getInputManager() {
        return inputManager;
    }

    /**
     * @param inputManager the inputManager to set
     */
    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    /**
     * @return the renderManager
     */
    public RenderManager getRenderManager() {
        return renderManager;
    }

    /**
     * @param renderManager the renderManager to set
     */
    public void setRenderManager(RenderManager renderManager) {
        this.renderManager = renderManager;
    }

    /**
     * @return the camUtil
     */
    public CamUtils getCamUtil() {
        return camUtil;
    }

    /**
     * @param camUtil the camUtil to set
     */
    public void setCamUtil(CamUtils camUtil) {
        this.camUtil = camUtil;
    }

    /**
     * @return the currentCam
     */
    public Camera getCurrentCam() {
        return currentCam;
    }

    /**
     * @param currentCam the currentCam to set
     */
    public void setCurrentCam(Camera currentCam) {
        this.currentCam = currentCam;
    }

    /**
     * @return the shapeUtil
     */
    public ShapeUtil getShapeUtil() {
        return shapeUtil;
    }

    /**
     * @param shapeUtil the shapeUtil to set
     */
    public void setShapeUtil(ShapeUtil shapeUtil) {
        this.shapeUtil = shapeUtil;
    }

    public <T extends AbstractManager> T getManager(Class<T> aClass) {
        return null;
    }

    public <T> T getRegistered(Class<T> aClass) {
        return null;
    }
}
