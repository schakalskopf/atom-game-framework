package sg.atom2d.test.tile;

import sg.atom2d.geo.tile.tiled.TiledLoader3D;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import java.awt.Canvas;

/**
 * test
 *
 * @author normenhansen
 */
public class TestTileLoader3D extends SimpleApplication {

    public static void main(String[] args) {
        TestTileLoader3D app = new TestTileLoader3D();
        app.setDefaultSettings();
        app.start();
    }

    public void setDefaultSettings() {
        AppSettings cfg = new AppSettings(true);

        cfg.setResolution(800, 600);
        cfg.setTitle("Stendhal 3D"); // branding: window name
        setShowSettings(false);
        setSettings(cfg);
    }

    public Canvas getAppCanvas() {
        createCanvas();
        JmeCanvasContext ctx = (JmeCanvasContext) getContext();
        startCanvas();
        return ctx.getCanvas();
    }

    @Override
    public void simpleInitApp() {
        //viewPort.setBackgroundColor(ColorRGBA.White);
        flyCam.setMoveSpeed(40f);
        TiledLoader3D tileLoader = new TiledLoader3D(assetManager, rootNode);
        try {
            tileLoader.loadAll("tiled/Level 0/semos/village_w.tmx");
            tileLoader.alignCam(cam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
