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
   for (int i = 0; i < board.width; i++) {
     for (int j = 0; j < board.height; j++) {
       currVal = (board.grid[i+j*board.width] == cellType ? 1 : 0);

       if (i==0 && j==0) {
         integralBoard[0] = currVal;
       } else if (j == 0) { // y = 0
         integralBoard[i + j*board.width] = currVal + integralBoard[(i-1)+(j*board.width)];
       } else if (i == 0) { // x = 0
         integralBoard[i + j*board.width] = currVal + integralBoard[i + ((j-1)*board.width)];
       } else {
         integralBoard[i + j*board.width] = currVal + integralBoard[i + (j-1)*board.width] + integralBoard[(i-1)+j*board.width] - integralBoard[(i-1) + (j-1)*board.width];
       }
     }
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
  
  
  
  /**
   * get 8-neighborhood
   * @param x should be > 0
   * @param y should be > 0
   * @param b board
   * @return
   */
  public static Cell[] get8neigh(int x, int y, Board b) {
    Cell[] robot8neigh = new Cell[3*3];
    for (int j = 0; j < 3; j++) {
      for (int i = 0; i < 3; i++) {
        robot8neigh[i+3*j] = b.get(x-1+i, y-1+j);
      }
    }
    
    return robot8neigh;
  }
  
  /**
   * get 4-neighborhood (0,0) is top left:
   *<pre>   0
   * 1 R 3
   *   2</pre>
   * @param x > 0
   * @param y > 0
   * @param b board
   * @return
   */
  public static Cell[] get4neigh(int x, int y, Board b) {
    Cell[] robot4neigh = new Cell[4];
    robot4neigh[0] = b.get(x,y-1);
    robot4neigh[1] = b.get(x-1,y);
    robot4neigh[2] = b.get(x,y+1);
    robot4neigh[3] = b.get(x+1,y);
    
    return robot4neigh;
  }

}
