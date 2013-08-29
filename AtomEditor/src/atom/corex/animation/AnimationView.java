/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.corex.animation;

import com.jme3.animation.AnimChannel;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author hungcuong
 */
public class AnimationView extends Node implements Control {

    private BitmapText[] labels;
    private String[] statLabels;
    private String[] statData;
    private final StringBuilder stringBuilder = new StringBuilder();
    private AnimChannel channel;
    float textSize;
    ColorRGBA textColor;
    private String currentAniName;
    private int currentAniIndex;

    public ColorRGBA getTextColor() {
        return textColor;
    }

    public void setTextColor(ColorRGBA textColor) {
        this.textColor = textColor;
        changeTextFont();
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        changeTextFont();
    }

    public void setChannel(AnimChannel channel) {
        this.channel = channel;
    }

    public AnimationView(String name, AssetManager manager, AnimChannel channel) {
        super(name);

        setQueueBucket(Bucket.Gui);
        setCullHint(CullHint.Never);

        this.channel = channel;
        statLabels = aniGetLabels();

        statData = new String[statLabels.length];
        labels = new BitmapText[statLabels.length];
        textColor = ColorRGBA.Red;
        textSize = 10;

        BitmapFont font = manager.loadFont("Interface/Fonts/Console.fnt");
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new BitmapText(font);
            labels[i].setColor(textColor);
            labels[i].setSize(textSize);
            labels[i].setLocalTranslation(0, labels[i].getLineHeight() * (i + 1), 0);
            attachChild(labels[i]);
        }

        addControl(this);
    }

    private void changeTextFont() {
        for (int i = 0; i < labels.length; i++) {
            labels[i].setColor(textColor);
            labels[i].setSize(textSize);
        }
    }

    public void update(float tpf) {
        aniGetData();
        for (int i = 0; i < labels.length; i++) {
            stringBuilder.setLength(0);
            stringBuilder.append(statLabels[i]).append(" = ").append(statData[i]);
            if (i==currentAniIndex){
                labels[labels.length-1-i].setColor(ColorRGBA.Red);
            } else {
                labels[labels.length-1-i].setColor(textColor);
            }
            labels[labels.length-1-i].setText(stringBuilder);
            //System.out.println(stringBuilder);
        }
    }

    public Control cloneForSpatial(Spatial spatial) {
        return (Control) spatial;
    }

    public void setSpatial(Spatial spatial) {
    }

    public void setEnabled(boolean enabled) {
    }

    public boolean isEnabled() {
        return true;
    }

    public void render(RenderManager rm, ViewPort vp) {
    }

    private void aniGetData() {
        String name = channel.getAnimationName();
        float timeLength = channel.getAnimMaxTime();
        float speed=channel.getSpeed();
        float time= channel.getTime();
        float percent=FastMath.ceil(time/timeLength * 100);
        int boneNum=channel.getControl().getSkeleton().getBoneCount();

        String[] list1 = new String[5];
        StringBuilder sb = new StringBuilder();

        list1[0] = name;
        list1[1] = ""+timeLength;
        list1[2] = ""+speed;
        list1[3] = ""+time;
        list1[4] = ""+percent;

        String[] list3=channel.getControl().getAnimationNames().toArray(new String[0]);
        currentAniName=channel.getAnimationName();
        int num = list3.length;
         String[] list2 = new String[num+1];
         list2[0]="("+num+") Bone: "+boneNum;
         

         for (int i=0;i<num;i++){
             list2[i+1]=list3[i];
             if (currentAniName.equals(list3[i])) {
                 currentAniIndex=i+6;
             }
         }
        statData = concat (list1, list2);

        

    }

    private String[] aniGetLabels() {

        String[] list1 = new String[]{"Ani name",
            "Length",
            "Speed",
            "Now",
            "Frame"};
        
        int num = channel.getControl().getAnimationNames().size();
         String[] list2 = new String[num+1];
         System.out.println("Num "+num);
         list2[0]="Channel";
         for (int i=1;i<num+1;i++){
             list2[i]=""+i;
         }
         
        String[] listAll = concat (list1, list2);

        return listAll;
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
