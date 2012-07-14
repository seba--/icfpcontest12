package game;

import java.util.Set;
import java.util.TreeSet;

import util.MathUtil;

/**
 * @author seba
 */
public class StaticConfig {
  
  /**
   * List of lift positions: liftPositions = {3, 8, 19} => there are three lifts: 3, 8, and 19.
   */
  public final int[] liftPositions;
  
  /**
   * Position of next lambda at index position: nextLambda[robot] == position of next lambda.
   */
  public final int[] nextLambda;
  
  /*
   * for flooding
   */
  public final int floodingRate;
  public final int waterResistance;
  
  public StaticConfig(State initialState) {
    this(initialState, 0, 10);
  }
  
  public StaticConfig(State initialState, int floodingRate, int waterResistance) {
    this.floodingRate = floodingRate;
    this.waterResistance = waterResistance;
    
    Board board = initialState.board;
    
    nextLambda = new int[board.grid.length];
    Set<Integer> liftPositionsSet = new TreeSet<Integer>();
    for (int col = 0; col < board.width; ++col)
      for (int row = 0; row < board.height; ++row) {
        if (board.get(col, row) == Cell.Lift || board.get(col, row) == Cell.RobotAndLift)
          liftPositionsSet.add(col * board.height + row);
        
        // TODO: probably to inefficient for large boards 
        int closestLambda = -1;
        int closestDistance = Integer.MAX_VALUE;
        for (int lambda : initialState.lambdaPositions) {
          int lcol = lambda / board.height;
          int lrow = lambda % board.height;
         
          if (closestLambda == -1) {
            closestLambda = lambda;
            closestDistance = MathUtil.distance(col, row, lcol, lrow);
          }
          else {
            int distance = MathUtil.distance(col, row, lcol, lrow);
            if (distance < closestDistance) {
              closestLambda = lambda;
              closestDistance = distance;
            }
          }
        }
        
        nextLambda[col * board.height + row] = closestLambda;
      }

    this.liftPositions = new int[liftPositionsSet.size()];
    int i = 0;
    for (Integer pos : liftPositionsSet) {
      this.liftPositions[i] = pos;
      i++;
    }
    
  }
}
