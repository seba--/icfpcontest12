package game.strategy;

import java.util.List;

import game.Cell;
import game.Command;
import game.State;
import game.ai.Strategy;
import game.strategy.tom.Helpers;
/**
 * Goes to nearest lambda or exit if no  lambdas left
 * 
 * @author horstmey
 *
 */
public class NextManhattanLambda implements Strategy {

  
  @Override
  public List<Command> apply(State s) {
    if (s.lambdaPositions.isEmpty()) return null;
    
    //todo find targets
    int bestCol = -1;
    int bestRow = -1;
    int bestDist = Integer.MAX_VALUE;
    
    for(int p : s.lambdaPositions) {
      int col = p / s.board.height;
      int row = p % s.board.height;
      int d = Helpers.manhattan(s.robotCol, s.robotRow, col, row); 
      if (d < bestDist) {
        bestCol = col;
        bestRow = row;
        bestDist = d;
      }
    }
    
    //todo moveTo target 
    return Helpers.moveToSimple(s, bestCol, bestRow);
  }

}
