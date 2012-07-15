package game.strategy;

import game.Cell;
import game.Command;
import game.State;
import game.StaticConfig;
import game.ai.Strategy;
import game.strategy.tom.Helpers;

import java.util.List;
/**
 * Goes to nearest lambda or exit if no  lambdas left
 * 
 * @author horstmey
 *
 */
public class ClosestWalkLambda extends Strategy {

  public final StaticConfig sconfig;
  
  public ClosestWalkLambda(StaticConfig sconfig) {
    this.sconfig = sconfig;
  }
  
  @Override
  public List<Command> apply(State s) {
    if (s.lambdaPositions.isEmpty()) return null;
    
    return Helpers.moveToCell(s, Cell.Lambda, 1);
  }
  
  @Override
  public boolean wantsToApply(State s) {
    return !s.lambdaPositions.isEmpty();
  }
  
  @Override
  public String toString() {
    return "ClosestWalkLambda";
  }

}
