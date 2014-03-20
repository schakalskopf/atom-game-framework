/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.common;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;

/**
 * Better stats.
 *
 * @author hungcuong
 */
public class StatusDisplay {

    public BitmapText labels;
    public BitmapText infoText1;
    public BitmapText infoText2;
    public BitmapText infoText3;
    public BitmapText infoText4;
    private AssetManager assetManager;
    private Node guiNode;
    private Camera cam;

    public StatusDisplay(Node guiNode, AssetManager assetManager, Camera cam) {
        this.guiNode = guiNode;
        this.assetManager = assetManager;
        this.cam = cam;
    }

    public void initText() {
        BitmapFont font = assetManager.loadFont("Interface/Fonts/Console.fnt");
        Node textNode = new Node();

        labels = new BitmapText(font);
        labels.setColor(ColorRGBA.Brown);
        labels.setSize(11);

        infoText1 = new BitmapText(font);
        infoText1.setColor(ColorRGBA.Red);
        infoText1.setSize(11);
        infoText2 = new BitmapText(font);
        infoText2.setColor(ColorRGBA.Green);
        infoText2.setSize(11);
        infoText3 = new BitmapText(font);
        infoText3.setColor(ColorRGBA.Blue);
        infoText3.setSize(11);
        infoText4 = new BitmapText(font);
        infoText4.setColor(ColorRGBA.Yellow);
        infoText4.setSize(11);

        textNode.setCullHint(CullHint.Never);
        textNode.setQueueBucket(Bucket.Gui);
        textNode.attachChild(labels);
        textNode.attachChild(infoText1);
        textNode.attachChild(infoText2);
        textNode.attachChild(infoText3);
        textNode.attachChild(infoText4);

        infoText1.setLocalTranslation(10, -30, 0);
        infoText2.setLocalTranslation(10, -50, 0);
        infoText3.setLocalTranslation(10, -70, 0);
        infoText4.setLocalTranslation(10, -90, 0);

        //textNode.setLocalTranslation(cam.getWidth() - 200, cam.getHeight() - 30, 0);
        textNode.setLocalTranslation(10, cam.getHeight() - 30, 0);

        guiNode.attachChild(textNode);
        labels.setText("A Status!");



    }
}
