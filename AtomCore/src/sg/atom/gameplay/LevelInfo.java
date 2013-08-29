package sg.atom.gameplay;

import sg.atom.gameplay.score.ScoreInfo;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class LevelInfo {

    public LevelInfo(String name, int dif, int max, String levelModelPath) {
        this.levelName = name;
        this.difficulty = dif;
        this.maxPlayer = max;
        this.levelModelPath = levelModelPath;
    }
    public String levelName;
    public int difficulty;
    public int maxPlayer;
    String levelModelPath;
    String levelPassword;
    ScoreInfo levelHighScore;
}
