package game.strategy;

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
public class ClosestManhattanLambda extends Strategy {

  public final StaticConfig sconfig;
  
  public ClosestManhattanLambda(StaticConfig sconfig) {
    this.sconfig = sconfig;
  }
  
  @Override
  public List<Command> apply(State s) {
    if (s.lambdaPositions.isEmpty()) return null;
    
    int lambda = sconfig.nextLambda[s.robotCol * s.board.height + s.robotRow];
    if (lambda < 0)
      return null;
    
    return Helpers.moveToSimple(s, lambda / s.board.height, lambda % s.board.height);
  }
  
  @Override
  public boolean wantsToApply(State s) {
    return !s.lambdaPositions.isEmpty();
  }
  
  @Override
  public String toString() {
    return "NextManhattanLambda";
  }

}
