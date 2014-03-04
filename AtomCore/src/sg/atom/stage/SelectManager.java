package sg.atom.stage;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;

import sg.atom.ui.GameGUIManager;
import sg.atom.entity.EntityManager;
import sg.atom.entity.SpatialEntity;
import sg.atom.entity.SpatialEntityControl;
import sg.atom.stage.select.EntitySelectCondition;
import sg.atom.stage.select.HoverFunction;
import sg.atom.stage.select.SelectFunction;
import sg.atom.stage.select.SelectListener;
import sg.atom.stage.select.SpatialSelectControl;
import sg.atom.stage.select.condition.EmptyEntitySelectCondition;
import sg.atom.ui.input.MouseRawListener;
import sg.atom.world.SceneGraphHelper;

/**
@author atomix
 */
/**
 * This class
 * <code>SelectManager</code> manages the select actions of the game<br> In fact
 * is pretty generic for custom mechanic to hook in!<br> <br> <p>
 * <b>SelectListener</b> via Obsever pattern: like Event Listener in Swing<br>
 * You should register your listener by registerSelectListener.<br> The
 * listeners will be called when an Object (
 * <code>Spatial</code> or
 * <code>Entity</code>) is Selected/Deselected.<br>
 *
 * This class also hold a list of selected objects for you to check.<br> </p>
 * <p><br> <b>Function</b> You can also drive the SelectManager by set the
 * function for it by
 * <code>setFunction(String functionName)</code> <ul><li>SingleSelect</li></ul>
 * </p>
 *
 * <b>SelectFunction</b> has a SelectCondition and determine a SelectShape,
 * which will be represent by a SelectUI. By default , RectangleSelectShape is
 * made of 2 points, top-left and bottom-down. In other case PolySelectShape
 * points will be added to the shape each time the user click in SelectMode.<br>
 *
 * <b>SelectCondition</b> The select method is defined by an interface
 * SelectCondition <br>
 *
 * If the current selectCondition of the SelectManager is meet, selectEvent will
 * be trigger and spreaded<br>
 *
 * <b>SelectUI</b> is used to represent to select area or shape in the screen,
 * like rectangle or circle.<br>
 *
 * <b>SelectManager</b> will automaticly work when Mouse moved and click
 * arcording to the current Function:<br> <ul><li>SingleSelect</li></ul>
 */
public class SelectManager {

    public static enum SelectOperationType {

        Normal, Add, Substract, Union
    }
    SelectOperationType selectOperationType = SelectOperationType.Normal;
    AssetManager assetManager;
    protected StageManager stageManager;
    protected InputManager inputManager;
    protected WorldManager worldManager;
    protected GameGUIManager gameGUIManager;
    protected EntityManager entityManager;
    /**
     * the current selection of the game
     */
    ArrayList< SpatialEntity> currentSelection;
    ArrayList<SelectListener> listeners = new ArrayList<SelectListener>();
    protected boolean trackHover = false;
    private EntitySelectCondition entitySelectCondition;
    private SelectFunction currentSelectFunction;
    private HoverFunction hoverFunction;
    private CollisionResults currentResults;
    private CollisionResults currentHoverResults;
    private boolean enable = false;

    public SelectManager(GameGUIManager gameGUIManager, StageManager stageManager, WorldManager worldManager) {

        this.gameGUIManager = gameGUIManager;
        this.stageManager = stageManager;
        this.inputManager = stageManager.getApp().getInputManager();
        currentSelection = new ArrayList< SpatialEntity>(100);
        this.hoverFunction = new HoverFunction(this);
    }

    /**
     * the first initilazation of the Manager
     */
    public void init() {
        this.entityManager = this.stageManager.getEntityManager();
        this.worldManager = this.stageManager.getWorldManager();
        this.assetManager = this.worldManager.getAssetManager();
        entitySelectCondition = new EmptyEntitySelectCondition();
    }

    public void setRootNode() {
    }

    public void processSelectFunction() {
        Node shootables = worldManager.getWorldNode();
        currentResults = this.doShoot(stageManager.getCamera(), shootables);
    }

    public void processHoverFunction() {
        Node shootables = worldManager.getWorldNode();
        currentHoverResults = this.doShoot(stageManager.getCamera(), shootables);
    }

    public void setupInputListener() {
        System.out.println("Select Input setup!");
        inputManager.addRawInputListener(new MouseRawListener() {
            @Override
            public void onMouseMotionEvent(MouseMotionEvent evt) {
                mouseMove(evt);
            }

            @Override
            public void onMouseButtonEvent(MouseButtonEvent evt) {
                mouseButton(evt);
            }
        });
    }

    public void mouseMove(MouseMotionEvent evt) {
        if (enable) {
            if (currentSelectFunction != null) {
                currentSelectFunction.mouseMove(evt);
            }
            if (trackHover) {
                if (this.hoverFunction != null) {
                    processHoverFunction();
                    this.hoverFunction.funcHover();
                }
            }
        }
    }

    public void mouseButton(MouseButtonEvent evt) {
        if (enable) {
            if (currentSelectFunction != null) {
                currentSelectFunction.mouseButton(evt);
            }
        }
    }

    public SpatialEntityControl findSpatialEntityControl(CollisionResults results) {
        if (results.size() > 0) {
            CollisionResult closest = results.getClosestCollision();
            Geometry geo = closest.getGeometry();

            // check SpatialEntityControl
            Spatial selectableSpatial = SceneGraphHelper.travelUpFindControl(geo, SpatialEntityControl.class);
            if (selectableSpatial == null) {
                funcDeselectAll();
                return null;
            } else {

                SpatialEntityControl spatialEntityControl = selectableSpatial.getControl(SpatialEntityControl.class);
                return spatialEntityControl;
            }
        }
        return null;
    }

    public CollisionResults doShoot(Camera cam, Node shootables) {

        //System.out.println(" DO SHOOT !");
        Vector3f origin = cam.getWorldCoordinates(inputManager.getCursorPosition(), 0.0f);
        Vector3f direction = cam.getWorldCoordinates(inputManager.getCursorPosition(), 0.3f);
        direction.subtractLocal(origin).normalizeLocal();


        // shoot and check the shoot ray collision
        Ray ray = new Ray(origin, direction);
        CollisionResults results = new CollisionResults();
        shootables.collideWith(ray, results);
        return results;
    }

    /**
     * This function drive the default deselect All mechanic <ol> <li>Set
     * selected to False in all
     * <code>SpatialSelectControl</code></li> <li>Call the select listeners</li>
     * </ol>
     */
    protected void funcDeselectAll() {
        if (!currentSelection.isEmpty()) {
            for (SpatialEntity entity : currentSelection) {
                Spatial sp = entity.getSpatial();
                SpatialSelectControl entityControl = sp.getControl(SpatialSelectControl.class);
                entityControl.setSelected(false);
                for (SelectListener sl : listeners) {
                    sl.deselected(entity);
                }
            }
            for (SelectListener sl : listeners) {
                sl.deselectMulti(currentSelection);
            }
            currentSelection.clear();
        }
    }

    public ArrayList<SpatialEntity> getCurrentSelection() {
        return currentSelection;
    }

    public <T extends SpatialEntity> ArrayList<T> getCurrentSelection(Class<T> clazz) {
        ArrayList<T> result = new ArrayList<T>();
        for (SpatialEntity se : currentSelection) {
            result.add((T) se);
        }
        return result;
    }

    public void registerSelectListener(SelectListener listener1) {
        listeners.add(listener1);

    }

    public void deselectAll() {
        funcDeselectAll();
    }

    public CollisionResults getCurrentResults() {
        return currentResults;
    }

    public CollisionResults getCurrentHoverResults() {
        return currentHoverResults;
    }

    public void setSelectFunction(SelectFunction selectFunc) {
        this.currentSelectFunction = selectFunc;

    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public EntitySelectCondition getEntitySelectCondition() {
        return entitySelectCondition;
    }

    public SelectFunction getCurrentSelectFunction() {
        return currentSelectFunction;
    }

    public ArrayList<SelectListener> getListeners() {
        return listeners;
    }

    public GameGUIManager getGameGUIManager() {
        return gameGUIManager;
    }

    public void setEntitySelectCondition(EntitySelectCondition entitySelectCondition) {
        this.entitySelectCondition = entitySelectCondition;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isTrackHover() {
        return trackHover;
    }

    public void setTrackHover(boolean trackHover) {
        this.trackHover = trackHover;
    }

    public void setHoverFunction(HoverFunction hoverFunction) {
        this.hoverFunction = hoverFunction;
    }

    public HoverFunction getHoverFunction() {
        return hoverFunction;
    }

    public SelectOperationType getSelectOperationType() {
        return selectOperationType;
    }

    public void setSelectOperationType(SelectOperationType selectOperationType) {
        this.selectOperationType = selectOperationType;
    }
}
