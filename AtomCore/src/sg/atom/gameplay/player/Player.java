package sg.atom.gameplay.player;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import sg.atom.gameplay.GameCharacter;
import sg.atom.gameplay.GamePlayManager;
import sg.atom.stage.StageManager;

/**
 * Player usually refered to human who play and control the game.
 *
 * @author cuong.nguyenmanh2
 */
public class Player {

    private StageManager stageManager;
    private GamePlayManager gamePlayManager;
    // Player properties
    private String name;
    private PlayerAvatar playerAvatar;
    private PlayerProfile playerProfile;
    private GameCharacter playerMainCharacter;
    ClassToInstanceMap datas = MutableClassToInstanceMap.create();

    public Player(StageManager stageManager, String name) {
        this.stageManager = stageManager;
        this.gamePlayManager = stageManager.getGamePlayManager();
        this.name = name;
    }

    /**
     * Need to be replaced in real-game implementation!
     */
    public void initPlayer() {
        this.playerAvatar = new PlayerAvatar();
        this.playerProfile = new PlayerProfile();
        this.playerMainCharacter = new GameCharacter("Character " + name);
    }

    public void simpleUpdate(float tpf) {
    }

    public PlayerAvatar getPlayerAvatar() {
        return playerAvatar;
    }

    public void setPlayerAvatar(PlayerAvatar playerAvatar) {
        this.playerAvatar = playerAvatar;
    }

    public GameCharacter getPlayerMainCharacter() {
        return playerMainCharacter;
    }

    public void setPlayerMainCharacter(GameCharacter playerMainCharacter) {
        this.playerMainCharacter = playerMainCharacter;
    }

    public PlayerProfile getPlayerProfile() {
        return playerProfile;
    }

    public void setPlayerProfile(PlayerProfile playerProfile) {
        this.playerProfile = playerProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //FIXME: Replace with Lookup.
    public <T> T getUserData(Class<T> aClass) {
        return (T) datas.getInstance(aClass);
    }

    public void addUserData(Object object) {
        datas.putInstance(object.getClass(), object);
    }
}
