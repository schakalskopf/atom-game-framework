/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import sg.atom.gameplay.actor.Actor;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.LinkedList;
import sg.atom.gameplay.action.MoveAction;

/**
 *
 * @author hungcuong
 */
public class GameCharacter extends Actor {
    // Game character properties

    private int id;
    private String name;
    private String characterModelPath;
    // =============================================
    // Action 
    // Movement Action
    public MoveAction moveAction;
    // Skill Action
    LinkedList<GameAction> requestedAction = new LinkedList<GameAction>();
    // ===============================================
    private Node characterModel;
    AtomCharacterControl characterControl;
    private Node modelNode;

    public AtomCharacterControl getCharacterControl() {
        return characterControl;
    }

    public void initCharacter(Node modelNode, AtomCharacterControl characterControl) {
        this.modelNode = modelNode;
        this.characterControl = characterControl;
        modelNode.addControl(characterControl);
    }

    public GameCharacter(String name) {
        this.name = name;
    }

    public GameCharacter(String name, String characterModelPath) {
        this.name = name;
        this.characterModelPath = characterModelPath;
    }

    public GameCharacter(String name, Node characterModel) {
        this.name = name;
        this.characterModel = characterModel;
    }
    /*
     void updateSkill() {
     for (GameAction sk : requestedAction) {
     if (sk instanceof SkillAction) {
     if (((SkillAction) sk).getSkillName().equals("weapon")) {
     if (((SkillAction) sk).getSkillProperty().equals("shoot")) {
     //weapon.fire(true);
     } else if (((SkillAction) sk).getSkillProperty().equals("zoom")) {
     //weapon.reload(true);
     } else if (((SkillAction) sk).getSkillProperty().equals("reload")) {
     //weapon.zoom(true);
     }
     }
     }
     }
     }
     * 
     */

    public Spatial getPlayerModel() {
        return modelNode;
    }

    public Node getModelNode() {
        return modelNode;
    }

    public void setModelNode(Node modelNode) {
        this.modelNode = modelNode;
    }
    // GETTER & SETTER

    public int getId() {
        return id;
    }

//    public LinkedList<Skill> getSkill(){
//        
//    }
    @Override
    public void doAction(GameAction a) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
