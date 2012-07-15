package game.strategy;

import game.Cell;
import game.Command;
import game.State;
import game.StaticConfig;
import game.ai.Strategy;
import game.strategy.tom.Helpers;

import java.util.List;
/**
 * Goes to n nearest lambdas or exit if no lambdas left
 * 
 * @author horstmey
 *
 */
public class NClosestWalksLambda extends Strategy {

  public final int N;
  
  public NClosestWalksLambda(int n) {
    N = n;
  }
  
  @Override
  public boolean isUseOnce() {
    return false;
  }
  
  @Override
  public List<Command> apply(State s) {
    s.nClosestWalksStrategyIndex++;
    return Helpers.moveToCell(s, Cell.Lambda, s.nClosestWalksStrategyIndex);
  }
  
  @Override
  public boolean wantsToApply(State s) {
    return s.nClosestWalksStrategyIndex < N && s.nClosestWalksStrategyIndex < s.lambdaPositions.size();
  }
  
  @Override
  public String toString() {
    return "NClosestWalksLambda";
  }

}