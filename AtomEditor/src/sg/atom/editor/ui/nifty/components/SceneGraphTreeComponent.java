package sg.atom.editor.ui.nifty.components;

import sg.atom.editor.managers.EditorGUIManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.TreeBox;
import de.lessvoid.nifty.controls.TreeItem;
import de.lessvoid.nifty.controls.treebox.builder.TreeBoxBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import java.util.HashMap;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class SceneGraphTreeComponent extends ExNiftyComponent {

    HashMap<String, NiftyImage> treeIcons = new HashMap<String, NiftyImage>();

    public SceneGraphTreeComponent(EditorGUIManager guiManager) {
        super("sceneGraph", guiManager);
    }

    public PanelBuilder createTree() {
        this.nScreen = getNiftyScreen();
        return new PanelBuilder("tree") {
            {
                childLayoutCenter();
                height(percentage(100));
                width(percentage(100));
                control(new TreeBoxBuilder("tree-box") {
                    {
                        valignBottom();
                    }
                });
            }
        };
    }

    public void setupTreeBox() {
        NiftyImage folder = nifty.createImage("Interface/icons/jme3/node.gif", true);
        NiftyImage folderOpen = nifty.createImage("Interface/icons/jme3/node.gif", true);
        NiftyImage item = nifty.createImage("Interface/icons/jme3/mesh.gif", true);
        treeIcons.put("folder", folder);
        treeIcons.put("item", item);
        treeIcons.put("folderOpen", folderOpen);

        TreeItem<String> treeRoot = new TreeItem<String>();
        /*
         TreeItem<String> branch1 = new TreeItem<String>(treeRoot, "branch 1", "branche 1", folder, folderOpen, true);
         TreeItem<String> branch11 = new TreeItem<String>(treeRoot, "branch 1 1", "branche 1 1", item);
         TreeItem<String> branch12 = new TreeItem<String>(treeRoot, "branch 1 2", "branche 1 2", item);
         branch1.addTreeItem(branch11);
         branch1.addTreeItem(branch12);
         TreeItem<String> branch2 = new TreeItem<String>(treeRoot, "branch 2", "branche 2", folder, folderOpen, true);
         TreeItem<String> branch21 = new TreeItem<String>(treeRoot, "branch 2 1", "branche 2 1", folder, folderOpen, true);
         TreeItem<String> branch211 = new TreeItem<String>(treeRoot, "branch 2 1 1", "branche 2 1 1", item);
         branch2.addTreeItem(branch21);
         branch21.addTreeItem(branch211);
         treeRoot.addTreeItem(branch1);
         treeRoot.addTreeItem(branch2);
         */

        treefromSceneGraph(treeRoot, guiManager.getApp().getRootNode());
        nScreen.findNiftyControl("tree-box", TreeBox.class).setTree(treeRoot);
    }

    private void treefromSceneGraph(TreeItem<String> treeRoot, Node rootNode) {
        TreeItem<String> treeItem;
        for (Spatial sp : rootNode.getChildren()) {
            if (sp instanceof Node) {
                treeItem = new TreeItem<String>(treeRoot, sp.getName(), sp.getName(), treeIcons.get("folder"), treeIcons.get("folder"), true);
                treeRoot.addTreeItem(treeItem);
                treefromSceneGraph(treeItem, (Node) sp);
            } else {
                treeItem = new TreeItem<String>(treeRoot, sp.getName(), sp.getName(), treeIcons.get("item"), treeIcons.get("item"), true);
                treeRoot.addTreeItem(treeItem);
            }
            if (treeItem != null) {
                //treeRoot.addTreeItem(treeItem);
            }
        }


    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        super.bind(nifty, screen);
    }

    @Override
    public Element build(Nifty nifty, Screen screen, Element parent) {
        return createTree().build(nifty, screen, parent);
    }
}
