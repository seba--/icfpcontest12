package game;

import java.util.Map;


/**
 * @author seba
 */
public class StaticConfig {
  
  /**
   * lift position: there is only one lift in each map.
   */
  public final int liftx;
  public final int lifty;
  
  /*
   * for flooding
   */
  public final int floodingRate;
  public final int waterResistance;
  public final boolean boardHasTrampolines;
  
  /*
   * for beards
   */
  public final int beardgrowth;
  
  public StaticConfig(State initialState) {
    this(initialState, 0, 10, 25);
  }
  
  public StaticConfig(State initialState, int floodingRate, int waterResistance, int beardgrowth) {
    this.floodingRate = floodingRate;
    this.waterResistance = waterResistance;
    this.beardgrowth = beardgrowth;
    
    Board board = initialState.board;
    this.boardHasTrampolines = !board.bitsets[Cell.Trampoline.ordinal()].isEmpty();
        
    int x = -1;
    int y = -1;
    for (int col = 0; col < board.width; ++col) {
      for (int row = 0; row < board.height; ++row) {
        if (board.get(col, row) == Cell.Lift || board.get(col, row) == Cell.RobotAndLift) {
          x = col;
          y = row;
        }
      }
    }

    liftx = x;
    lifty = y;
  }
}
