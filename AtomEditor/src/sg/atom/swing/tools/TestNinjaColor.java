package sg.atom.swing.tools;

import sg.atom.editor.ui.nifty.components.AnimationManager;
import sg.atom.corex.animation.AnimationView;
import sg.atom.corex.material.color.ColorUtils;
import sg.atom.corex.common.CommonTool;
import sg.atom.corex.scenegraph.shape.ArrowShape;
import sg.atom.corex.scenegraph.shape.GridHelperShape;
import sg.atom.corex.scenegraph.shape.LightShape;
import sg.atom.corex.scenegraph.shape.Shape;
import sg.atom.corex.scenegraph.shape.ShapeUtil;
import sg.atom.corex.scenegraph.shape.SkeletonDebugger;
import sg.atom.corex.scenegraph.shape.SphereShape;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Type;
import java.nio.FloatBuffer;

/** Sample 7 - how to load an OgreXML model and play an animation, 
 * using channels, a controller, and an AnimEventListener. */
public class TestNinjaColor extends SimpleApplication {

    Node player;
    private AnimChannel channel;
    private AnimControl control;
    private SkeletonDebugger skeletonDebug;
    AnimationView aniInfoTxt;
    int vertexCount;
    float[] colorArray;
    Geometry ninja;
    Mesh ninjaMesh;
    FloatBuffer colorBuffer;
    FloatBuffer vertices;
    String currentAniName = "";
    float currentTime = 0;
    float currentSpeed = 1;
    int playState = 0;
    private AnimationManager aniManager;

    public static void main(String[] args) {
        TestNinjaColor app = new TestNinjaColor();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        initCommonTool();
        initViewPort();
        initLight();
        initKeys();
        initPlayer();
        initText();
        initCam();
        initShape();
    }

    public void initViewPort() {
        viewPort.setBackgroundColor(ColorRGBA.LightGray);
    }
    CommonTool commonTool;

    void initCommonTool() {
        this.commonTool = CommonTool.getDefault(this);
    }

    public void initPlayer() {
        initPlayerModelPos();
        initPlayerAnimation();
        initPlayerSkeleton();
        initPlayerMat();
        initPlayerVextexCount();
        initColorBuffer();
    }

    public void initPlayerModelPos() {
        /** Load a model that contains animation */
        player = (Node) assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");

        player.setLocalScale(0.02f);

        float PI = FastMath.PI;
        float PI2 = FastMath.PI / 2;
        float[] angles = {0, PI, 0};

        player.setLocalRotation(new Quaternion(angles));
        rootNode.attachChild(player);

    }

    public void initPlayerAnimation() {

        aniManager = new AnimationManager();

        /** Create a controller and channels. */
        control = player.getControl(AnimControl.class);
        control.addListener(aniManager);
        channel = control.createChannel();
        channel.setAnim("Kick");

    }

    public void initPlayerSkeleton() {
        skeletonDebug = new SkeletonDebugger("Ninja_skeleton", control.getSkeleton());
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", ColorRGBA.Green);
        mat.getAdditionalRenderState().setDepthTest(false);
        skeletonDebug.setMaterial(mat);

        player.attachChild(skeletonDebug);
        //player.setCullHint(CullHint.Always);
        //skeletonDebug.setCullHint(CullHint.Never);
    }

    public void initPlayerMat() {
        // NINJA is Special !
        ninja = (Geometry) player.getChild(0);
        if ((ninja.getName().startsWith("Ninja") && (ninja.getClass() == Geometry.class))) {
            //Material mat2 = ninja.getMaterial();
            //mat2.setTransparent(true);
            //mat2.getAdditionalRenderState().setWireframe(true);
            //System.out.println("I'm a ninja !");
        }


        Material matVC = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //matVC.setTransparent(true);
        matVC.getAdditionalRenderState().setWireframe(true);
        matVC.setBoolean("VertexColor", true);
        ninja.setMaterial(matVC);
    }

    public void initPlayerVextexCount() {
        ninjaMesh = ninja.getMesh();
        //ninjaMesh.setMode(Mesh.Mode.Points);
        //ninjaMesh.setPointSize(4);
        vertexCount = ninjaMesh.getVertexCount();
        colorArray = new float[vertexCount * 4];

    }

    public void initLight() {
        /** Add a light source so we can see the model */
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.1f, -1f, -1).normalizeLocal());
        rootNode.addLight(dl);
    }

    public void initShape() {

        ShapeUtil shapeUtil = new ShapeUtil();

//        ((MyBoxShape.Builder) shapeUtil.createShape(MyShape.BOX, "Box1", rootNode))
//                .setAllSize(1f, 1f, 1f)
//                .setPos(player.getWorldTranslation())
//                .setColor(ColorRGBA.Blue)
//                .build();

        ((SphereShape.Builder) shapeUtil.createShape(Shape.SPHERE, "SPHERE1", rootNode)).setRadius(0.2f).setOpacity(0.5f).setPos(0, 0, 0).setColor(ColorRGBA.Yellow).build();

//         ((MyCylinderShape.Builder) shapeUtil.createShape(MyShape.CYLINDER, "CYLINDER1", rootNode))
//                .setRadius(2f)
//                .setRadius2(2f)
//                .setHeight(4f)
//                .setOpacity(0.5f)
//                .setPos(player.getWorldTranslation())
//                .setColor(ColorRGBA.Red)
//                 .build();    

        ((ArrowShape.Builder) shapeUtil.createShape(Shape.ARROW, "ARROW", rootNode)).setLineWidth(4).setPos(0, 0, 0).setColor(ColorRGBA.Red).build();

//        ((MyLightShape.Builder) shapeUtil.createShape(MyShape.LIGHT, "LIGHT", rootNode))
//                .setRadialSamples(16)
//                .setLightColor(ColorRGBA.Blue)
//                .setPos(0,0,0)
//                .build();     
//        
//        ((LightShape.Builder) shapeUtil.createShape(Shape.LIGHT, "LIGHT", rootNode))
//                .setLightColor(ColorRGBA.Blue)
//                .setWired(true)
//                .setPos(0,0,0)
//                .build();

        ((GridHelperShape.Builder) shapeUtil.createShape(Shape.GRIDHELPER, "GRID", rootNode)).setxSamples(55).setySamples(55).setGridWidth(0.4f).setColor(ColorRGBA.DarkGray).setPos(0, 0, 0).build();
    }

    private void initCam() {
        flyCam.setMoveSpeed(10f);
        flyCam.setRotationSpeed(2);
    }

    private void initText() {
        aniInfoTxt = new AnimationView("Animation", assetManager, channel);
        aniInfoTxt.setTextSize(12f);
        aniInfoTxt.setTextColor(ColorRGBA.Blue);

        aniInfoTxt.setLocalTranslation(cam.getWidth() - 200, cam.getHeight() - 350, 0);
        guiNode.attachChild(aniInfoTxt);
    }

    /** Custom Keybindings: Mapping a named action to a key input. */
    private void initKeys() {
        inputManager.addMapping("Walk", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "Walk");

        for (int i = 0; i < 9; i++) {
            inputManager.addMapping("Do" + i, new KeyTrigger(KeyInput.KEY_1 + i));
            inputManager.addListener(actionListener, "Do" + i);
        }
        inputManager.addMapping("Speed+", new KeyTrigger(KeyInput.KEY_ADD));
        inputManager.addListener(actionListener, "Speed+");
        inputManager.addMapping("Speed-", new KeyTrigger(KeyInput.KEY_SUBTRACT));
        inputManager.addListener(actionListener, "Speed-");


        inputManager.addMapping("PlayPause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addListener(actionListener, "PlayPause");
        inputManager.addMapping("Stop", new KeyTrigger(KeyInput.KEY_O));
        inputManager.addListener(actionListener, "Stop");

        inputManager.addMapping("Backward", new KeyTrigger(KeyInput.KEY_LBRACKET));
        inputManager.addListener(actionListener, "Backward");
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_RBRACKET));
        inputManager.addListener(actionListener, "Forward");

        inputManager.addMapping("Skeleton", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addListener(actionListener, "Skeleton");
    }

    @Override
    public void simpleUpdate(float tpf) {

        opps();
    }

    void initColorBuffer() {

        vertexCount = ninjaMesh.getVertexCount();
        colorArray = new float[vertexCount * 4];
        int colorIndex = 0;

        System.out.println("vertexCount :" + vertexCount);
        //System.out.println("colorArray.length :" + colorArray.length);
        for (int i = 0; i < vertexCount; i++) {
            // Red value (is increased by .2 on each next vertex here)
            colorArray[colorIndex++] = 0.1f;
            // Green value (is reduced by .2 on each next vertex)
            colorArray[colorIndex++] = 0.9f;
            // Blue value (remains the same in our case)
            colorArray[colorIndex++] = 0.5f;
            // Alpha value (no transparency set here)
            colorArray[colorIndex++] = 1.0f;

        }

        ninjaMesh.setBuffer(Type.Color, 4, colorArray);
        ninjaMesh.getBuffer(Type.Color).setUpdateNeeded();
        //System.out.println("colorBuffer.remaining :" + colorBuffer.remaining());
        System.out.println("Count :" + ninjaMesh.getBuffer(Type.Color).getNumComponents());

    }

    void opps() {
        float maxdis = 15;
        vertexCount = ninjaMesh.getVertexCount();
        //colorArray = new float[vertexCount * 4];
        Vector3f camPos = cam.getLocation();
        //Vector3f geoPos = ninja.getWorldTranslation();
        //Vector3f scaleValue = ninja.getLocalScale();
        colorBuffer = ninjaMesh.getFloatBuffer(VertexBuffer.Type.Color);


        vertices = ninjaMesh.getFloatBuffer(VertexBuffer.Type.Position);
        vertices.rewind();

        float value = 0, valueR = 0, valueG = 0, valueB = 0, valueA = 0;

        int colorIndex = 0;
        for (int i = 0; i < vertexCount; i++) {
            float posx = vertices.get();
            float posy = vertices.get();
            float posz = vertices.get();

            Vector3f vertexPos2 = new Vector3f(posx, posy, posz);
            Vector3f vertexPos = new Vector3f(posx, posy, posz);
            ninja.getWorldTransform().transformVector(vertexPos2, vertexPos);

            float dis = vertexPos.distance(camPos);
            if (dis > maxdis) {
                value = 0;

            } else {
                value = (maxdis - dis) / maxdis;
                //System.out.println(" value :"+value);
                Vector3f color1 = ColorUtils.HSVtoRGB((1 - value) * 360, 1, 1);

                valueR = color1.x;
                valueG = color1.y;
                valueB = color1.z;
                valueA = 1f;
                //System.out.println("value :"+ value );
                //System.out.println("colorIndex :"+ colorIndex );
                colorArray[colorIndex++] = valueR;
                colorArray[colorIndex++] = valueG;
                colorArray[colorIndex++] = valueB;
                colorArray[colorIndex++] = 1f;


            }
        }

        colorBuffer.clear();
        colorBuffer.put(colorArray);


        ninjaMesh.getBuffer(Type.Color).setUpdateNeeded();

    }
    /** Definining the named action that can be triggered by key inputs. */
    private ActionListener actionListener = new ActionListener() {

        private boolean skeletonShow = true;

        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Walk") && !keyPressed) {
                if (!channel.getAnimationName().equals("Walk")) {
                    /** Play the "walk" animation! */
                    channel.setAnim("Walk", 0.50f);
                    channel.setLoopMode(LoopMode.Loop);
                }
            } else {
                if (keyPressed) {
                    if (name.startsWith("Do")) {
                        int what = Integer.parseInt(name.substring(2));
                        String[] names = control.getAnimationNames().toArray(new String[0]);
                        int length = names.length;
                        if (what < length) {
                            currentSpeed = channel.getSpeed();
                            channel.setAnim(names[what]);
                            channel.setSpeed(speed);
                        }

                    } else if (name.equals("Speed-")) {
                        channel.setSpeed(channel.getSpeed() / 2);
                    } else if (name.equals("Speed+")) {
                        channel.setSpeed(channel.getSpeed() * 2);
                    } else if (name.equals("Stop")) {
                        currentSpeed = channel.getSpeed();
                        channel.setTime(0);
                        channel.setSpeed(0);
                    } else if (name.equals("PlayPause")) {
                        if (playState == 0) {
                            //currentTime = channel.getTime();
                            currentSpeed = channel.getSpeed();
                            //channel.setTime(currentTime);
                            channel.setSpeed(0);
                            playState = 1;
                        } else {
                            channel.setSpeed(currentSpeed);
                            playState = 0;
                        }
                    } else if (name.equals("Backward")) {
                        float spf = (float) 1 / 24;
                        currentTime = channel.getTime();
                        float maxTime = channel.getAnimMaxTime();
                        if (currentTime - spf >= 0) {
                            currentTime = currentTime - spf;
                        } else {
                            currentTime = maxTime;
                        }
                        //System.out.println("Time : " + currentTime + "  _ " + spf);
                        channel.setTime(currentTime);
                        channel.setSpeed(0);

                        playState = 1;
                    } else if (name.equals("Forward")) {
                        float spf = (float) 1 / 24;
                        float maxTime = channel.getAnimMaxTime();

                        currentTime = channel.getTime();
                        if (currentTime + spf <= maxTime) {
                            currentTime = currentTime + spf;
                        } else {
                            currentTime = 0;
                        }
                        //System.out.println("Time : " + currentTime + "  _ " + spf);
                        channel.setTime(currentTime);
                        channel.setSpeed(0);

                        playState = 1;
                    } else if (name.equals("Skeleton")) {
                        skeletonShow = !skeletonShow;
                        if (skeletonShow) {
                            skeletonDebug.removeFromParent();
                        } else {
                            player.attachChild(skeletonDebug);
                        }
                    }

                    //System.out.println("Do it babe !");
                }
            }
        }
    };
}
