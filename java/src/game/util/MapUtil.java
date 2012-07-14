package game.util;

import game.Board;
import game.Cell;

/**
 * Map utilities
 * @author Felix Rieger
 *
 */
public class MapUtil {

  /**
   * Build an integral board
   * @param board
   * @param cellType  cell to look for
   * @return
   */
  public static int[] computeIntegralBoard(Board board, Cell cellType) { 
   int[] integralBoard = new int[board.grid.length];
   
   int currVal = 0;
   for (int i = 0; i < board.grid.length; i++) {
     if (board.grid[i] == cellType) {
       currVal++;
     }
     integralBoard[i] = currVal;
   }
    
    return integralBoard;
  }
  
  /**
   * Get the count in an area.
   * @param x1  upper left corner (assuming (0,0) is up left)
   * @param y1  upper left corner
   * @param x2  lower right corner
   * @param y2  lower right corner
   * @param boardWidth
   * @param integralBoard integral board
   * @return
   */
  public static int getCountInArea(int x1, int y1, int x2, int y2, int boardWidth, int[] integralBoard) {
    int a = x1 + boardWidth*y1;
    int c = x2 + boardWidth*y2;
    
    int b = x2 + boardWidth*y1; // x2, y1
    int d = x1 + boardWidth*y2; // x1, y2
    
    // sum = C + A - B - D
    int sum = integralBoard[c] + integralBoard[a] - integralBoard[b] - integralBoard[d];
    return sum;
  }
  
  
  /**
   * get version of the map showing the density.
   * @param filterSize Size of the box filter kernel
   * @param boardWidth 
   * @param integralBoard integral board
   * @return float array; values from 0..1 --- 1 = maximum density (i.e. all cells in filter kernel contain element)
   */
  public static float[] getDensityMap(int filterSize, int boardWidth, int[] integralBoard) {
    //int tempWidth = (boardWidth+1) / filterSize;
    //int tempHeight = ((integralBoard.length/boardWidth) + 1) / filterSize;
    int filterOffset = filterSize/2;
    int tx1, tx2, ty1, ty2;
    
    //int[] temp = new int[tempWidth*tempHeight];
    float[] temp = new float[integralBoard.length];
    
    for (int xcoord = 0; xcoord < boardWidth; xcoord++) {
      tx1 = xcoord-filterOffset;
      tx2 = xcoord+filterOffset;
      if (tx1 < 0)
        tx1 = 0;
      if (tx2 < 0)
        tx2 = 0;
      if (tx1 > boardWidth-1) 
        tx1 = boardWidth-1;
      if (tx2 > boardWidth-1)
        tx2 = boardWidth-1;
      for (int ycoord = 0; ycoord < integralBoard.length/boardWidth; ycoord++) {
        ty1 = ycoord-filterOffset;
        ty2 = ycoord+filterOffset;
        if (ty1 < 0)
          ty1 = 0;
        if (ty2 < 0)
          ty2 = 0;
        if (ty1 > integralBoard.length/boardWidth)
          ty1 = integralBoard.length/boardWidth;
        if (ty2 > integralBoard.length/boardWidth)
          ty2 = integralBoard.length/boardWidth;
        
        //temp[xcoord + tempWidth*ycoord] = getCountInArea(tx1, ty1, tx2, ty2,boardWidth, integralBoard);
        temp[xcoord + boardWidth*ycoord] = getCountInArea(tx1, ty1, tx2, ty2, boardWidth, integralBoard) / ((float) (filterSize*filterSize));
      }
    }
    
    return temp;
  }
  
}
