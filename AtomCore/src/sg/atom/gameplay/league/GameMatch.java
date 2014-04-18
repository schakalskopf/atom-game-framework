package sg.atom.gameplay.league;

import java.util.ArrayList;
import sg.atom.core.AtomMain;
import sg.atom.gameplay.GameLevel;
import sg.atom.gameplay.player.Player;
import sg.atom.logic.Condition;

/**
 * A GameMatch between Player of type T in a Game.
 *
 * <h4>Features:</h4><ul>
 *
 * <li>Common n-n bidi mapping between GameMatches and {@link GameLevel} are
 * supported. You can also extends this by provide your map method.
 *
 *  <li>
 * @author atomix
 */
public abstract class GameMatch<GAME extends AtomMain, PLAYER extends Player> {

    public static class WinPredicate extends Condition<GameMatch> {

        @Override
        public boolean apply(GameMatch input) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public static enum CommonGameGoal {

        WIN, DEFEAT, TERMINATE, EARN, SURVIVE, OVERCOME, PASS;
//        Predicate goalPredicate;
//
//        CommonGameGoal(Predicate goalPredicate) {
//            this.goalPredicate = goalPredicate;
//        }
//
//        public Predicate getGoalPredicate() {
//            return goalPredicate;
//        }
    };

    public static class CommonGameMatchResult {
        //WON, LOST, PASSED, NOTPASSED
    }

    public static enum CommonGameMatchRoutine {
    }
    public Integer id;
    public String name;
    public PLAYER host;
    public ArrayList<PLAYER> players;
    public GamePolicy<GAME, PLAYER> policy;

    public abstract Object getResult(PLAYER p);
    
    public GameLevel mapToLevel(){
        return null;
    }
    
    public void multiMapToLevel(){
        
    }
}
