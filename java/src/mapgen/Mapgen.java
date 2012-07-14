package mapgen;

import game.Board;
import game.Cell;
import game.log.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * 
 * @author Felix Rieger
 *
 */

public class Mapgen {

  
  public static void main(String[] args) throws IOException {
    Log.println(cellTypes.length);
                                // Earth, Empty, Lambda, Lift, Rock, Wall.
    int[] defaultProbabilites = {500,     130,   70,     10,   139,  150};    // just made these up
    
    
    int[] mapSizes = {3, 4, 5, 6, 7, 8, 9, 10, 20, 50, 100, 200};
    
    int mapCtr = 0;
    for (int mapSize : mapSizes) {
      for (int i = 0; i < 100; i++) {
        Log.println("generating map " + (mapCtr*100 + i) + " of " + (mapSizes.length*100) + " -- " + mapSize);
        Board theMap = getMap(mapSize, mapSize, 1, defaultProbabilites);
        writeMap(theMap, (mapSize + "x" + mapSize), zeropad(3,i));
      }
      mapCtr++;
    }
  }

  public static String zeropad(int length, int num) {
    String tmp = ""+num;
    while(tmp.length() < length) {
      tmp = "0" + tmp;
    }
    return tmp;
  }
    
  public static void writeMap(Board map, String sizePath, String name) throws IOException {
    Log.println("\twrite map");
    File f = new File("../maps/created/" + sizePath + "/map" + sizePath + "-" + name + ".map");
    f.createNewFile();
    FileWriter fw = new FileWriter(f);
    fw.write(map.toString());
    fw.close();
  }
  
  
  public static Board getMap(int sizex, int sizey, int lifts, int[] probabilities) {
    Log.println("\tcreate walls");
    Board tempBoard = createWalls(sizex, sizey, lifts);
    
    Log.println("\tfill map");
    Cell[] grid = tempBoard.grid;
    
    for (int i = 1; i < sizex-1; i++) {
      for (int j = 1; j < sizey-1; j++) {
        grid[i * sizey + j] = getNextRandomCell(probabilities);
      }
    }
    
    Log.println("\tplace robot");
    // place robot
    int robotXPos = rnd.nextInt(sizex-2)+1;
    int robotYPos = rnd.nextInt(sizey-2)+1;
    grid[robotXPos * sizey + robotYPos] = Cell.Robot;
    
    return tempBoard;
  }
  
  static Random rnd = new Random();

  /**
   * 
   * @param probabilities array of probabilities 0..999, 
   *      order: Earth, Empty, Lambda, Lift, Rock, Wall.
   *      Probabilities should add up to 999
   * @return
   */
  public static Cell getNextRandomCell(int[] probabilities) {
    int val = rnd.nextInt(1000);
    
    for (int c = 0; c < cellTypes.length; c++) {
      if (val >= probabilities[c]) { // not selected
        val -= probabilities[c];
      } else {  // selected
        return cellTypes[c];
      }
    }
    
    return Cell.Earth;  // if something goes wrong, just return earth
  }
  
  /**
   * Creates walls and the lift
   * @param sizex   width
   * @param sizey   height
   * @param lifts   maximum number of lifts
   * @return
   */
  public static Board createWalls(int sizex, int sizey, int lifts) {
    Board tempBoard = new Board(sizex, sizey);

    Cell[] grid = tempBoard.grid;
    
    // build walls
    
    for (int i = 0; i < sizex; i++) {
      grid[i * sizey + 0] = Cell.Wall;
      grid[i * sizey + (sizey-1)] = Cell.Wall;      
    }
    for (int i = 1; i < sizey-1; i++) {
      grid[0 * sizey + i] = Cell.Wall;
      grid[(sizex-1) * sizey + i] = Cell.Wall;
    }
    
    for (int i = 0; i < lifts; i++) {
      // determine lift position
      Random rnd = new Random();
      int liftWall = rnd.nextInt(4);
      int liftPos = 0;
      if ((liftWall & 1) == 0) {
        // on top/bottom walls
        liftPos = rnd.nextInt(sizex-2) + 1; // no lift in edges
      } else {
        liftPos = rnd.nextInt(sizey-2) + 1; // no lift in edges
      }
      
      // build lift
      if (liftWall == 0) {
        grid[liftPos * sizey + 0] = Cell.Lift;
      } else if (liftWall == 1) {
        grid[0 * sizey + liftPos] = Cell.Lift;
      } else if (liftWall == 2) {
        grid[liftPos * sizey + (sizey-1)] = Cell.Lift;
      } else if (liftWall == 3) {
        grid[(sizex-1) * sizey + liftPos] = Cell.Lift;
      }
    }
    
    return tempBoard;
  }
  
  private static Cell[] cellTypes = {Cell.Earth, Cell.Empty, Cell.Lambda, Cell.Lift, Cell.Rock, Cell.Wall};

  
  
}
