package sg.atom.swing.tools;

import com.jme3.app.SimpleApplication;
import com.jme3.light.PointLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.MatParam;
import com.jme3.material.MatParamTexture;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Spatial;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.mesh.IndexShortBuffer;
import com.jme3.scene.shape.Sphere;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import jme3tools.converters.ImageToAwt;

public class UVMaker extends SimpleApplication {

    float angle1;
    float angle2;
    PointLight pl;
    PointLight p2;
    Spatial lightMdl;
    Spatial lightMd2;

    public static void main(String[] args) {
        UVMaker app = new UVMaker();
        app.start();
    }
    private JFrame frame1;
    private JLabel label1;
    private Mesh mesh;
    private Geometry geo1;
    private DrawUVPanel panel2;
    private HashMap<String, BufferedImage> textureList = new HashMap<String, BufferedImage>(4);
    private JComboBox textureJList;

    public void simpleInitApp() {
//        PointLight pl = new PointLight();
//        pl.setPosition(new Vector3f(10, 10, -10));
//        rootNode.addLight(pl);
        flyCam.setMoveSpeed(10f);

        // sunset light
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.1f, -0.7f, 1).normalizeLocal());
        dl.setColor(new ColorRGBA(1f, 1f, 1f, 1.0f));
        rootNode.addLight(dl);


        lightMdl = new Geometry("Light", new Sphere(10, 10, 0.1f));
        lightMdl.setMaterial((Material) assetManager.loadAsset("Common/Materials/RedColor.j3m"));
        rootNode.attachChild(lightMdl);

        lightMd2 = new Geometry("Light", new Sphere(10, 10, 0.1f));
        lightMd2.setMaterial((Material) assetManager.loadAsset("Common/Materials/WhiteColor.j3m"));
        rootNode.attachChild(lightMd2);


        pl = new PointLight();
        pl.setColor(new ColorRGBA(1, 0.9f, 0.9f, 0));
        pl.setPosition(new Vector3f(0f, 0f, 4f));
        rootNode.addLight(pl);

        p2 = new PointLight();
        p2.setColor(new ColorRGBA(0.9f, 1, 0.9f, 0));
        p2.setPosition(new Vector3f(0f, 0f, 3f));
        rootNode.addLight(p2);


        // create the geometry and attach it
        Spatial elephant = (Spatial) assetManager.loadModel("Models/Elephant/Elephant.mesh.xml");
        geo1 = (Geometry) ((Node) elephant).getChild(0);
        mesh = geo1.getMesh();
        float scale = 0.05f;
        elephant.scale(scale, scale, scale);
        rootNode.attachChild(elephant);

        createFrame();

    }

    void createFrame() {
        frame1 = new JFrame("Texture");
        //frame1.setLayout(new MigLayout());

        JMenuBar menuBar = new JMenuBar();
        frame1.setJMenuBar(menuBar);

        JMenu menuFile = new JMenu("File");
        menuBar.add(menuFile);

        JToolBar toolBar = new JToolBar("Who is me");
        frame1.getContentPane().setLayout(new BorderLayout());
        frame1.add(toolBar, BorderLayout.NORTH);


        Material mat = geo1.getMaterial();
        LinkedList<MatParam> matList = new LinkedList<MatParam>(mat.getParams());
        for (MatParam matParam : matList) {
            if (matParam.getVarType().isTextureType()) {
                com.jme3.texture.Image image = ((MatParamTexture) matParam).getTextureValue().getImage();
                String textureType = ((MatParamTexture) matParam).getName();
                String textureName = ((MatParamTexture) matParam).getName();
                //bgImg=ImageToAwt.convert(image, false, false, 0);
                System.out.println("Texture_" + textureName);
                textureList.put("Texture_" + textureName, ImageToAwt.convert(image, false, false, 0));

            }
        }
        textureJList = new JComboBox(textureList.keySet().toArray());
        toolBar.add(new JLabel(" Select Texture "));
        toolBar.add(textureJList);



        JPanel panel1 = new JPanel();
        panel2 = new DrawUVPanel(geo1, textureList);
        panel2.initListener();
        textureJList.addActionListener(panel2);
        textureJList.addItemListener(panel2);

        label1 = new JLabel("It's very funny !");
        panel1.add(label1);
        JButton btn1024 = new JButton("1024");
        JButton btn512 = new JButton("512");
        JButton btn256 = new JButton("256");
        panel1.add(btn1024);
        panel1.add(btn512);
        panel1.add(btn256);
        getInfo();

        frame1.getContentPane().add(panel1, BorderLayout.SOUTH);
        frame1.setSize(512 + 30, 512 + 100);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setWheelScrollingEnabled(true);
        //panel2.setPreferredSize(new Dimension(512, 512));
        scrollPane.getVi