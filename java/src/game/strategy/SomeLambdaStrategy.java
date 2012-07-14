package game.strategy;

import java.util.List;

import game.Command;
import game.State;
import game.ai.Strategy;
import game.strategy.tom.Helpers;

/**
 * Chooses a next lambda and finds a simple path to it.
 * NOTE: Can be applied several times to the same state and 
 * chooses different lambdas each time!
 * 
 * @author horstmey
 *
 */
public class SomeLambdaStrategy extends Strategy {
    
  @Override
  public boolean wantsToApply(State s) {
    return !s.lambdaPositions.isEmpty() && s.nextLambdaStrategyIndex < s.lambdaPositions.size();
  }
  
  public boolean isUseOnce() {
    return false;
  }
  
  @Override
  public List<Command> apply(State s) {
    int i = -1;
    List<Command> cmds = null;
    for (i = s.nextLambdaStrategyIndex; i < s.lambdaPositions.size(); i++) {
      int p = s.lambdaPositions.get(i);
      int col = p / s.board.height;
      int row = p % s.board.height;
      cmds = Helpers.moveToSimple(s, col, row);
      if (cmds != null) break;
    }
    
    s.nextLambdaStrategyIndex = i + 1;
    return cmds;
  }
  
  public String toString() {
    return "SomeLambdaStrategy";
  }
}
