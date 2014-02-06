package sg.atom.gameplay.player;

import sg.atom.gameplay.GameCharacter;
import sg.atom.gameplay.GamePlayManager;
import sg.atom.stage.StageManager;

 public class Player {

    private StageManager stageManager;
    private GamePlayManager gamePlayManager;
    // Player properties
    private String name;
    private PlayerAvatar playerAvatar;
    private PlayerProfile playerProfile;
    private GameCharacter playerMainCharacter;

    public Player(StageManager stageManager, String name) {
        this.stageManager = stageManager;
        this.gamePlayManager = stageManager.getGamePlayManager();
        this.name = name;
    }

    public void initPlayer() {
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
}
