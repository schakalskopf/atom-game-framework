/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import com.jme3.scene.Node;
import java.util.LinkedList;
import java.util.Properties;
import sg.atom.entity.Entity;
import sg.atom.stage.actor.AtomActor;
import sg.atom.gameplay.controls.AtomCharacterControl;

/**
 * GameCharacter is a "Common" game pattern which a Player represent his/her
 * self as a Virtual representor, so call "Character" as in RPG.
 *
 * <p>GameCharacter can be built up from serveral infos and resources. It also
 * get methods to manipulate (get/set) custom data. A GameCharacter also refered
 * (link) to its associated GameCharacterControl</p>
 *
 * @author atomix
 */
public class GameCharacter extends AtomActor {
    // Game character properties

    private int id;
    private String name;
    private String characterModelPath;
    protected Properties userData;
    // Common associated data =====================================
    protected Entity characterEntity;
    protected LinkedList<GameAction> actionList = new LinkedList<GameAction>();
    protected LinkedList<Skill> skills = new LinkedList<Skill>();
    protected LinkedList<GameItem> items = new LinkedList<GameItem>();
    // Character Visualization=====================================
    private Node characterModel;
    private Node modelNode;
    private AtomCharacterControl characterControl;

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

    public void initCharacter(Node modelNode, AtomCharacterControl characterControl) {
        this.modelNode = modelNode;
        this.characterControl = characterControl;
        modelNode.addControl(characterControl);
    }

    @Override
    public void doAction(GameAction a) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    // GETTER & SETTER

    public LinkedList<GameItem> getItems() {
        return items;
    }

    public Node getModelNode() {
        return modelNode;
    }

    public void setModelNode(Node modelNode) {
        this.modelNode = modelNode;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCharacterModelPath() {
        return characterModelPath;
    }

    public AtomCharacterControl getCharacterControl() {
        return characterControl;
    }

    public Properties getUserData() {
        return userData;
    }

    public Entity getCharacterEntity() {
        return characterEntity;
    }

    public Node getCharacterModel() {
        return characterModel;
    }

    public LinkedList<GameAction> getActionList() {
        return actionList;
    }

    public LinkedList<Skill> getSkills() {
        return skills;
    }
}
