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
public class NextLambdaStrategy extends Strategy {
  {
    isUseOnce = false;
  }
  
  @Override
  public boolean wantsToApply(State s) {
    return s.nextLambdaStrategyIndex >= s.lambdaPositions.size();
  }
  
  @Override
  public List<Command> apply(State s) {
    if (s.lambdaPositions.isEmpty()) return null;
    int idx = s.nextLambdaStrategyIndex;
    if (idx > s.lambdaPositions.size()) return null; 
    
    
    int i = 0;
    List<Command> cmds = null;
    for(int p : s.lambdaPositions) {
      if (i >= idx) {
         int col = p / s.board.height;
         int row = p % s.board.height;
         cmds = Helpers.moveToSimple(s, col, row);
         if (cmds != null) break;
      }
      i++;
    }
    
    s.nextLambdaStrategyIndex = i + 1;
    return cmds;
  }
  
  public String toString() {
    return "NextLambdaStrategy";
  }
}
