/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select.ui;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.ui.Picture;
import sg.atom.core.GameGUIManager;
import sg.atom.stage.select.SelectUI;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class SelectRectUI extends SelectUI{

    Picture p1, p2;
    public float x = 0, y = 0, width = 0, height = 0;
    private Material blackMat;
    private final Material greenMat;
    Geometry l1, l2, l3, l4;
    private Geometry c1, c2;
    float lineWidth = 0.5f;
    boolean visible = false;
    public boolean dragging = false;

    public SelectRectUI(GameGUIManager gameGUIManager) {
        super("selectRectUI");
        AssetManager assetManager = gameGUIManager.getAssetManager();

        blackMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        blackMat.setColor("Color", ColorRGBA.Black);


        Box box = new Box(1, 1, 1);
        l1 = new Geometry("l1", box);

        greenMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        greenMat.setColor("Color", ColorRGBA.Green);

        l1.setMaterial(greenMat);
        l1.setLocalTranslation(0, 0, 1);

        l2 = l1.clone(true);
        l3 = l1.clone(true);
        l4 = l1.clone(true);
        /*
        l2.getMaterial().setColor("Color", ColorRGBA.Blue);
        l3.getMaterial().setColor("Color", ColorRGBA.Brown);
        l4.getMaterial().setColor("Color", ColorRGBA.Cyan);
         */


        l2.getMaterial().setColor("Color", ColorRGBA.Green);
        l3.getMaterial().setColor("Color", ColorRGBA.Green);
        l4.getMaterial().setColor("Color", ColorRGBA.Green);
        attachChild(l1);
        attachChild(l2);
        attachChild(l3);
        attachChild(l4);
        //Cylinder cyl = new Cylinder(1, 16, 2, 1);
        Sphere sp = new Sphere(6, 6, 2);
        c1 = new Geometry("c1", sp);
        c1.setMaterial(greenMat);
        c2 = c1.clone(true);

        c1.getMaterial().setColor("Color", ColorRGBA.Yellow);
        c2.getMaterial().setColor("Color", ColorRGBA.Orange);

        attachChild(c1);
        attachChild(c2);

        setVisible(false);
    }

    public void update() {
        updateRect(x, y, width, height);
        //System.out.println(" Width " + width + " height " + height);
    }

    public void updateRect(float x, float y, float width, float height) {

        l1.setLocalScale(width / 2, 1 * lineWidth, 1);
        l2.setLocalScale(1 * lineWidth, height / 2, 1);
        l3.setLocalScale(width / 2, 1 * lineWidth, 1);
        l4.setLocalScale(1 * lineWidth, height / 2, 1);

        l1.setLocalTranslation(x + width / 2, y, 0);
        l2.setLocalTranslation(x, y + height / 2, 0);
        l3.setLocalTranslation(x + width / 2, y + height, 0);
        l4.setLocalTranslation(x + width, y + height / 2, 0);

        c1.setLocalTranslation(x, y, 0);
        c2.setLocalTranslation(x + width, y + height, 0);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setHoldPoint(float px, float py) {
        x = px;
        y = py;
        width = 0;
        height = 0;
    }

    public void setDynamicPoint(float px, float py) {
        width = px - x;
        height = py - y;

    }

    public void setVisible(boolean b) {
        visible = b;
        if (!visible) {
            this.setCullHint(cullHint.Always);
        } else {
            this.setCullHint(cullHint.Never);
        }
    }

    public boolean isVisible() {
        return visible;
    }
    
    
}
