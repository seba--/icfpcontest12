package game.strategy.tom;

import game.Command;

public class WayCoordinateSimple implements Comparable<WayCoordinateSimple>{

  public int col;
  public int row;
  public int steps;
  public Command steppedHereWith;
  
  public WayCoordinateSimple(int col, int row, int steps, Command lastStep) {
    this.col = col;
    this.row = row;
    this.steps = steps;
    this.steppedHereWith = lastStep;
  }

  @Override
  public int compareTo(WayCoordinateSimple other) {
    return Integer.compare(steps, other.steps);
  }

}
