package game;

import game.strategy.tom.WayCoordinateSimple;

import java.util.PriorityQueue;

import util.MathUtil;


/**
 * @author seba
 */
public class StaticConfig {
  
  /**
   * lift position: there is only one lift in each map.
   */
  public final int liftx;
  public final int lifty;
  
  public final int maxStepsAprox;

  /*
   * for flooding
   */
  public final int floodingRate;
  public final int waterResistance;
  public final boolean boardHasTrampolines;
  public final boolean boardHasBeard;
  public final boolean boardHasWater;
  
  
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
    this.boardHasBeard       = !board.bitsets[Cell.Beard.ordinal()].isEmpty();
    this.boardHasWater       = (initialState.waterLevel > 0 || floodingRate > 0);
    
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

    /*
     * Approximate needed number of steps:
     * From the initial position go to each lambda and back to the initial position
     */
    State s = initialState;
    WayCoordinateSimple[][] m = new WayCoordinateSimple[s.board.width][s.board.height];
    PriorityQueue<WayCoordinateSimple> p = new PriorityQueue<WayCoordinateSimple>();
    WayCoordinateSimple wc = new WayCoordinateSimple(s.robotCol, s.robotRow, 0, null);
     
    while (wc != null) {
      if (m[wc.col][wc.row] == null || m[wc.col][wc.row].steps > wc.steps) { // not already better way

        Cell c = s.board.get(wc.col, wc.row);
        if (c == Cell.Earth || c == Cell.Empty || c == Cell.Lambda || c == Cell.Robot || c == Cell.Razor) { // may move there
          
          m[wc.col][wc.row] = wc;

          // generate follow up moves
          p.add(new WayCoordinateSimple(wc.col - 1, wc.row, wc.steps + 1, Command.Left));
          p.add(new WayCoordinateSimple(wc.col + 1, wc.row, wc.steps + 1, Command.Right));
          p.add(new WayCoordinateSimple(wc.col, wc.row + 1, wc.steps + 1, Command.Up));
          if (s.board.get(wc.col, wc.row + 1) != Cell.Rock)
            p.add(new WayCoordinateSimple(wc.col, wc.row - 1, wc.steps + 1, Command.Down));

        }
      } 
      wc = p.poll();
    }
    
    int steps = 0;
    for (int lambda : initialState.lambdaPositions) {
      wc = m[lambda / board.height][lambda % board.height];
      if (wc == null)
        steps += 2 * MathUtil.distanceToPos(s.robotCol, s.robotRow, lambda, board.height);
      else
        steps += 2 * wc.steps;
    }
    
    wc = m[liftx][lifty];
    if (wc == null)
      steps += 2 * MathUtil.distanceToPos(s.robotCol, s.robotRow, liftx, lifty);
    else
      steps += 2 * wc.steps;

    maxStepsAprox = steps;
  }
}
