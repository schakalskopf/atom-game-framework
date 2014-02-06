package sg.atom.gameplay;

import sg.atom.gameplay.score.ScoreInfo;

/**
 * LevelInfo (Common Implementation) is the description of a GameLevel.
 *
 * <p>The Reason to made up a lightwieght "Description" of a level is to
 * maximize reuse a GameLevel; custom data is packed into different
 * LevelInfo. </p>
 *
 * @author atomix
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
    public String levelModelPath;
    public String levelPassword;
    public ScoreInfo levelHighScore;
    public int status;

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public String getLevelModelPath() {
        return levelModelPath;
    }

    public void setLevelModelPath(String levelModelPath) {
        this.levelModelPath = levelModelPath;
    }

    public String getLevelPassword() {
        return levelPassword;
    }

    public void setLevelPassword(String levelPassword) {
        this.levelPassword = levelPassword;
    }

    public ScoreInfo getLevelHighScore() {
        return levelHighScore;
    }

    public void setLevelHighScore(ScoreInfo levelHighScore) {
        this.levelHighScore = levelHighScore;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
