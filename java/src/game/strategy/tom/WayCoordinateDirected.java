package game.strategy.tom;

import game.Command;

public class WayCoordinateDirected implements Comparable<WayCoordinateDirected>{

  public int steps;
  public int col;
  public int row;
  public Command steppedHereWith;
  
  private int cmp;

  public WayCoordinateDirected(int col, int row, int destCol, int destRow, int steps, Command lastStep) {
    this.col = col;
    this.row = row;
    this.steps = steps;
    this.steppedHereWith = lastStep;
    
    cmp = steps + Math.abs(destCol - col) + Math.abs(destRow - row);
  }

  @Override
  public int compareTo(WayCoordinateDirected other) {
    return Integer.compare(cmp, other.cmp);
  }

}
