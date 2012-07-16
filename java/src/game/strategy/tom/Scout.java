package game.strategy.tom;

import game.Board;
import game.Cell;

/**
 * A Scout is used to identify a target destination in Helpers.moveToScout
 * 
 * @author horstmey
 *
 */
public class Scout {
  public Cell[][] area;
  public final int colOffset;
  public final int rowOffset;
 
  //need: Cell.AnyPassable, Cell.AnySolid, Cell.Any
  public Scout(Cell[][] area, int colOffset, int rowOffset) {
    this.area = area;
    this.colOffset = colOffset;
    this.rowOffset = rowOffset;
  }
  
  public boolean areaFits(Board board, int col, int row) {
    for (int c = area.length - 1; c >= 0; c--) {
      for (int r = area[c].length -1; r >= 0; r--) {
       //TODO bounds check
        int boardCol = c + colOffset;
        int boardRow = r + rowOffset;
        
        //only Any may lie out of bounds
        if (area[c][r] != Cell.Any && !board.isPosition(board.position(boardCol, boardRow))) return false;
        
        //compare
        if (!Cell.similar(area[c][r], board.get(boardCol, boardRow))) return false;
      }
    }
    return true;
  }
}
