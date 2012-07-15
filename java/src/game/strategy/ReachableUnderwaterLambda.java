package game.strategy;

import java.util.List;

import game.Command;
import game.State;
import game.StaticConfig;
import game.ai.Strategy;
import game.strategy.tom.Helpers;

/**
 * Tries to get lambda in water with enough remaining moves to dive up again
 * @author Felix Rieger
 *
 */
public class ReachableUnderwaterLambda extends Strategy {

  final int waterResistance;
  final int floodingRate;
  
  public ReachableUnderwaterLambda(StaticConfig sc) {
    waterResistance = sc.waterResistance;
    floodingRate = sc.floodingRate;
  }
  
  @Override
  public List<Command> apply(State s) {
    int closestCoord = -1;
    int closestDist = Integer.MAX_VALUE;
    
    for(Integer n : s.lambdaPositions) {
      if (s.board.row(n) <= s.waterLevel) { // only interested in lambdas below water
        int dist = Helpers.manhattan(s.robotCol, s.robotRow, s.board.col(n), s.board.row(n));
        if (dist < closestDist) {
          closestDist = dist;
          closestCoord = n;
        }
      }
    }

    if (closestCoord == -1)   // found no lambda below water
      return null;
    
    if (closestDist > (waterResistance/2))   // no way to reach lambda without drowning
      return null;
    
    return Helpers.moveToSimple(s, s.board.col(closestCoord), s.board.row(closestCoord));    
  }

  @Override
  public boolean wantsToApply(State s) {
    if (s.waterLevel == 0)
      return false;
    
    return true;
  }

  @Override
  public String toString() {
    return "ReachableWaterLambda";
  }
  
  

}
