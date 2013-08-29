package atom.swing;

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
        scrollPane.getViewport().setView(panel2);
        //scrollPane.setWheelScrollingEnabled(true);
        frame1.getContentPane().add(scrollPane, BorderLayout.CENTER);
        scrollPane.setPreferredSize(new Dimension(512, 512));

        frame1.setBackground(Color.LIGHT_GRAY);
        frame1.setCursor(new Cursor(Cursor.HAND_CURSOR));

        frame1.setVisible(true);
    }

    public void getInfo() {
        String str = " Name :" + geo1.getName();
        str = str + "| Vertext Count :" + mesh.getVertexCount();
        str = str + "| Triangle Count :" + mesh.getTriangleCount();
        label1.setText(str);

    }

    @Override
    public void simpleUpdate(float tpf) {
        angle1 += tpf * 0.25f;
        angle1 %= FastMath.TWO_PI;

        angle2 += tpf * 0.50f;
        angle2 %= FastMath.TWO_PI;

        pl.setPosition(new Vector3f(FastMath.cos(angle1) * 4f, 0.5f, FastMath.sin(angle1) * 4f));
        p2.setPosition(new Vector3f(FastMath.cos(angle2) * 4f, 0.5f, FastMath.sin(angle2) * 4f));
        lightMdl.setLocalTranslation(pl.getPosition());
        lightMd2.setLocalTranslation(p2.getPosition());
    }

    @Override
    public void destroy() {
        frame1.dispose();
        super.destroy();
    }

    class DrawUVPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener, ItemListener, MouseWheelListener {

        Mesh mesh;
        Geometry geo;
        int width, height;
        List<Point> texcoordList = new LinkedList<Point>();
        List<Vector3i> indexList = new LinkedList<Vector3i>();
        BufferedImage bgImg = null;
        float closeEnough = 2.5f;
        HashMap<Integer, Point> texcoordListOld = new HashMap<Integer, Point>(20);
        private boolean showBrush;
        private int brushWidth;
        HashMap<String, BufferedImage> textureList;
        private float zoomAmount = 1;
        private int zoomPX;
        private int zoomPY;

        DrawUVPanel(Geometry geo, HashMap<String, BufferedImage> textureList) {
            this.geo = geo;
            this.mesh = geo.getMesh();
            setBackground(Color.LIGHT_GRAY);
            width = 512;
            height = 512;
            loadMeshIndex();
            loadMeshCoord();
            this.textureList = textureList;
            loadTextureImage();
            setPreferredSize(new Dimension(width, height));
        }

        public void initListener() {
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            this.addMouseWheelListener(this);
        }

        private void loadTextureImage() {
            if ((textureList != null) && (!textureList.isEmpty())) {
                this.bgImg = textureList.values().iterator().next();
            }
        }

        private void loadTextureImage(String textureName) {

            /*
            try {
            bgImg = ImageIO.read(new File("F:\\JGE\\Shader\\Elephant.jpg"));
            } catch (IOException ex) {
            Logger.getLogger(TestOgreLoading.class.getName()).log(Level.SEVERE, null, ex);
            }
             */
            this.bgImg = textureList.get(textureName);

        }

        private void loadMeshCoord() {
            FloatBuffer texcoordBuffer = mesh.getFloatBuffer(VertexBuffer.Type.TexCoord);
            int step = 0;
            int count = 0;
            int x = 0, y = 0;
            for (int i = 0; i < texcoordBuffer.limit(); i++) {
                float value = texcoordBuffer.get();

                switch (step) {
                    case 0:
                        x = Math.round(value * width);
                        step = 1;
                        break;
                    case 1:
                        y = Math.round(value * height);
                        count++;
                        //System.out.println(" > "+count+ "x :" + x + " y : " + y);
                        step = 0;

                        texcoordList.add(new Point(x, y));
                        break;
                }
            }
        }

        private void loadMeshIndex() {
            IndexShortBuffer indexBuffer = (IndexShortBuffer) mesh.getIndexBuffer();
            int step = 0;
            int count = 0;
            int x = 0, y = 0, z = 0;
            for (int i = 0; i < indexBuffer.size(); i++) {
                int value = indexBuffer.get(i);

                switch (step) {
                    case 0:
                        x = value;
                        step = 1;
                        break;
                    case 1:
                        y = value;
                        step = 2;
                        break;
                    case 2:
                        z = value;
                        count++;
                        //System.out.println(" > " + count + "x :" + x + " y : " + y + " z : " + z);
                        step = 0;

                        indexList.add(new Vector3i(x, y, z));
                        break;
                }
            }
        }
        int startX, startY, curX, curY, endX, endY;
        boolean triDrag = false, pDrag = false;
        List<Integer> pointSelectedIndex = new LinkedList<Integer>();
        int triSelectedIndex;

        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            startX = e.getX();
            startY = e.getY();

            System.out.println(">>> Clicked " + startX + " " + startY);

            pointSelectedIndex = checkClickPoint(startX, startY);
            if (!pointSelectedIndex.isEmpty()) {
                pDrag = true;
                triDrag = false;
                System.out.println(" Pressed Point : " + pointSelectedIndex);
                texcoordListOld.clear();
                for (int i = 0; i < pointSelectedIndex.size(); i++) {
                    int pi = pointSelectedIndex.get(i).intValue();

                    Point p1 = texcoordList.get(pi);
                    texcoordListOld.put(new Integer(pi), (Point) p1.clone());
                }
            } else {
                pDrag = false;
                triSelectedIndex = checkClickTri(startX, startY);
                if (triSelectedIndex != -1) {
                    System.out.println("Tri Drag : " + triSelectedIndex);
                    triDrag = true;
                    repaint();
                } else {
                    triDrag = false;
                }
            }
        }

        private List<Integer> checkClickPoint(int x, int y) {
            List<Integer> list = new LinkedList<Integer>();
            for (int i = 0; i < texcoordList.size(); i++) {
                Point p1 = texcoordList.get(i);
                //System.out.println("Distance : " + p1.distance(x, y));
                if (p1.distance(x, y) < closeEnough) {
                    System.out.println("Distance Pass : point " + i + " :" + p1.distance(x, y));
                    list.add(new Integer(i));
                }

            }
            return list;
        }

        private int checkClickTri(int x, int y) {
            Polygon p = new Polygon();
            int num = mesh.getTriangleCount();

            for (int i = 0; i < num; i++) {
                Vector3i tri = indexList.get(i);
                Point v1 = texcoordList.get(tri.x);
                Point v2 = texcoordList.get(tri.y);
                Point v3 = texcoordList.get(tri.z);

                p.addPoint(v1.x, v1.y);
                p.addPoint(v2.x, v2.y);
                p.addPoint(v3.x, v3.y);
                if (p.contains(x, y) == true) {
                    return i;
                }
                p.reset();
            }
            return -1;
        }

        public void mouseReleased(MouseEvent e) {
            pDrag = false;
            triDrag = false;
            endX = e.getX();
            endY = e.getY();
            repaint();
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
            curX = e.getX();
            curY = e.getY();
            //System.out.println(">>> Moved " + curX + " " + curY);
            // DRAW the brush
            if (showBrush) {
                repaint();
            }
            zoomPX = e.getX();
            zoomPY = e.getY();
        }

        public void drawVertex(int x, int y, Graphics g, Color c) {
            g.setColor(c);
            g.drawLine(x, y - 2, x, y + 2);
            g.drawLine(x - 2, y, x + 2, y);
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.setClip(0, 0, width, height);
            g2D.fillRect(0, 0, width, height);
            if (bgImg != null) {
                // DRAW the background image
                if (zoomAmount == 1) {
                    g2D.drawImage(bgImg, 0, 0, width, height, frame1);
                } else {

                    float imgW = bgImg.getWidth();
                    float imgH = bgImg.getHeight();
                    float imgZoomPX = zoomPX * zoomAmount;
                    float imgZoomPY = zoomPY * zoomAmount;
                    int sx1 = Math.round(imgZoomPX - zoomPX);
                    int sy1 = Math.round(imgZoomPY - zoomPY);

                    int sx2 = Math.round(sx1 + imgW / zoomAmount);
                    int sy2 = Math.round(sy1 + imgH / zoomAmount);
                    g2D.translate(-sx1, -sy1);
                    g2D.scale(zoomAmount, zoomAmount);
                    g2D.drawImage(bgImg, 0, 0, width, height, frame1);
                    //g2D.drawImage(bgImg, 0, 0, width, height, sx1, sy1, sx2, sy2, frame1);


                    //System.out.println(" XZ " + zoomPX + " YZ " + zoomPY + " X1 " + sx1 + " Y1 " + sy1);
                }
            }
            g2D.setColor(Color.LIGHT_GRAY);


            g2D.setColor(Color.BLACK);
            Polygon p = new Polygon();


            int num = mesh.getTriangleCount();

            for (int i = 0; i < num; i++) {
                Vector3i tri = indexList.get(i);

                if ((triDrag) && (i == triSelectedIndex)) {
                    g2D.setStroke(new BasicStroke(4f));
                    g2D.setColor(Color.YELLOW);

                    System.out.println("Tri DRAW : " + triSelectedIndex);
                }
                Point v1 = texcoordList.get(tri.x);
                Point v2 = texcoordList.get(tri.y);
                Point v3 = texcoordList.get(tri.z);

                p.addPoint(v1.x, v1.y);
                p.addPoint(v2.x, v2.y);
                p.addPoint(v3.x, v3.y);
                g2D.drawPolygon(p);
                if ((triDrag) && (i == triSelectedIndex)) {
                    g2D.setColor(Color.BLACK);
                    g2D.setStroke(new BasicStroke(1));
                }
                p.reset();
            }
            // DRAW the vextices
            for (int i = 0; i < texcoordList.size(); i++) {
                Point p1 = texcoordList.get(i);
                drawVertex(p1.x, p1.y, g2D, Color.WHITE);
            }

            if (pDrag) {
                //Point v1 = texcoordList.get(pointSelectedIndex);
                Point v1 = new Point(curX, curY);
                g2D.setColor(Color.YELLOW);
                g2D.fillOval(v1.x - 4, v1.y - 4, 8, 8);
            }
            //g.fillPolygon();
            // DRAW the brush
            showBrush = true;
            brushWidth = 70;
            if (showBrush) {
                g2D.setColor(Color.RED);
                g2D.drawOval(curX - brushWidth / 2, curY - brushWidth / 2, brushWidth, brushWidth);
            }
        }

        public Color disToColor(float dis) {
            return new Color(1, 1, 1);
        }

        public void mouseDragged(MouseEvent e) {
            curX = e.getX();
            curY = e.getY();

            if (pDrag) {
                System.out.println(">>> Draged point : " + pointSelectedIndex + " " + curX + " " + curY);
                for (int i = 0; i < pointSelectedIndex.size(); i++) {

                    int pi = pointSelectedIndex.get(i).intValue();

                    Point p1 = texcoordList.get(pi);
                    Point p2 = texcoordListOld.get(pi);


                    int transX = curX - startX;
                    int transY = curY - startY;
                    p1.x = p2.x + transX;
                    p1.y = p2.y + transY;
                    //texcoordList.set(p1, new Point(curX, curY));
                }
                repaint();
            }
            if (triDrag) {
                repaint();
            }
        }

        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox) e.getSource();
            String textureName = (String) cb.getSelectedItem();
            System.out.println("Select texture " + textureName);
            loadTextureImage(textureName);
            repaint();
        }

        public void itemStateChanged(ItemEvent e) {
            /*
            JComboBox cb = (JComboBox) e.getSource();
            String textureName = (String) cb.getSelectedItem();
            System.out.println("Select texture " + textureName);
            loadTextureImage(textureName);
             * 
             */
        }

        public void mouseWheelMoved(MouseWheelEvent e) {
            int rot = e.getWheelRotation();
            float speed = (float) rot / 20;
            if (zoomAmount - speed > 1) {
                zoomAmount = zoomAmount - speed;

            } else {
                zoomAmount = 1;
            }
            System.out.println("WheelRotation : " + e.getWheelRotation() + " Zoom amount :" + zoomAmount + " speed " + speed);
            repaint();
        }
    }

    public class Vector3i {

        public int x, y, z;

        Vector3i(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
