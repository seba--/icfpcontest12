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
    
    Set<Integer> liftPositionsSet = new TreeSet<Integer>();
    for (int col = 0; col < board.width; ++col) {
      for (int row = 0; row < board.height; ++row) {
        if (board.get(col, row) == Cell.Lift || board.get(col, row) == Cell.RobotAndLift) {
          liftPositionsSet.add(col * board.height + row);
        }
      }
    }

    this.liftPositions = new int[liftPositionsSet.size()];
    int i = 0;
    for (Integer pos : liftPositionsSet) {
      this.liftPositions[i] = pos;
      i++;
    }
    
  }
}
