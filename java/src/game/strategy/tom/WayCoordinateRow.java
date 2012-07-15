package game.strategy.tom;

import game.Command;

public class WayCoordinateRow implements Comparable<WayCoordinateRow> {

  public int steps;
  public int col;
  public int row;
  public Command steppedHereWith;

  private int cmp;

  public WayCoordinateRow(int col, int row, int destRow, int steps, Command lastStep) {
    this.col = col;
    this.row = row;
    this.steps = steps;
    this.steppedHereWith = lastStep;

    cmp = steps + Math.abs(destRow - row);
  }

  @Override
  public int compareTo(WayCoordinateRow other) {
    return Integer.compare(cmp, other.cmp);
  }

}
