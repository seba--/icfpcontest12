package game;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Board as specified in Section 2.1
 * EXCEPT: we start counting at (0,0).
 * 
 * @author seba
 */
public class Board {
  
  /**
   * FIRST coordinate is COLUMN;
   * SECOND coordinate is ROW.
   */
  public final Cell[][] grid;
  
  public final int width;
  public final int height;
  
  public Board(int width, int height) {
    this.width = width;
    this.height = height;
    grid = new Cell[width][height];
  }
  
  /**
   * FIRST coordinate is COLUMN;
   * SECOND coordinate is ROW.
   */
  public Cell get(int n, int m) {
    if (n < 0 || n >= width || m < 0 || m >= height)
      return Cell.Wall;
    return grid[n][m];
  }

  /**
   * FIRST coordinate is COLUMN;
   * SECOND coordinate is ROW.
   */
  public void set(int n, int m, Cell c) {
    if (n < 0 || n >= width || m < 0 || m >= height)
      return;
    grid[n][m] = c;
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int rrow = height - 1; rrow >= 0; rrow--) {
      for (int col = 0; col < width; col++)
        sb.append(grid[col][rrow].shortName());
      if (rrow > 0)
        sb.append('\n');
    }
    return sb.toString();
  }
  
  public static Board parse(String s) {
    List<List<Cell>> flippedBoard = new ArrayList<List<Cell>>();
    int colCount = -1;
    
    StringTokenizer tokenizer = new StringTokenizer(s, "\n");
    while (tokenizer.hasMoreTokens()) {
      String line = tokenizer.nextToken();
      List<Cell> row = new ArrayList<Cell>();
      flippedBoard.add(row);
      
      for (int i = 0; i < line.length(); i++)
        row.add(Cell.parse(line.charAt(i)));
      
      colCount = Math.max(colCount, line.length());
    }
    
    int rowCount = flippedBoard.size();
    
    Board board = new Board(colCount, rowCount);
    for (int row = 0; row < rowCount; row++)
      for (int col = 0; col < colCount; col++) {
        int rrow = rowCount - row - 1;
        List<Cell> rowList = flippedBoard.get(rrow);
        if (col < rowList.size())
          board.grid[col][row] = rowList.get(col);
        else 
          // Section 2.5: "Shorter lines are assumed to be padded out with spaces"
          board.grid[col][row] = Cell.Empty;
      }
    
    return board;
  }
}
