package game.strategy;

import game.Board;
import game.Cell;
import game.Command;
import game.State;
import game.ai.Strategy;

import java.util.ArrayList;
import java.util.List;

public class WallFollowingStrategy implements Strategy {

  @Override
  public List<Command> apply(State state) {
    int robotx = state.robotCol;
    int roboty = state.robotRow;
    if (robotx == 0 || roboty == 0 || robotx == state.board.width-1 || robotx == state.board.height-1) {
      // robot is on outside wall, nothing to do here
      return null;
    }
    
    // get the 8-neighborhood
    
    Cell[] robot8neigh = get8neigh(robotx, roboty, state.board);
    
    // compute the number of wall elements top, bottom, left, right
    int[] wallSum = computeWallSum(robot8neigh);
    
    if (wallSum[0] + wallSum[1] + wallSum[2] + wallSum[3] == 0) {
      // no walls in sight, abort
      return null;
    }
    
    // idea: do not go where no walls are, but go where the least number of walls are (so you will not get stuck in a corner)

    
    // find out which direction to go. Keep the two best directions
    int[] smallestIdxs = new int[]{-1,-1};
    int[] smallestVals = new int[]{255, 255};
    
    for (int i = 0; i < 4; i++) {
      if ((wallSum[i] != 0) && (wallSum[i] < smallestVals[0])) {
        smallestVals[1] = smallestVals[0];
        smallestIdxs[1] = smallestIdxs[0];
        
        smallestVals[0] = wallSum[i];
        smallestIdxs[0] = i;
      }
    }
    
    // 8-neighborhood:
    // 0 1 2
    // 3[4]5
    // 6 7 8
    
    // check the two directions and find out which leads to less walls around overall
    Cell[] new8neigh;
    int[] futureWallCounts = new int[]{255,255};
    for (int i = 0; i < 2; i++) {
      if (smallestIdxs[i] != -1) { 
        
        if (smallestIdxs[i] == 0) {// go up
          if (robot8neigh[1] != Cell.Wall) { // robot can actually move up
            new8neigh = get8neigh(robotx, roboty+1, state.board);
          } else {
            continue;
          }
        } else if (smallestIdxs[i] == 1) { // go down
          if (robot8neigh[7] != Cell.Wall) { // robot can actually move down
            new8neigh = get8neigh(robotx, roboty-1, state.board);
          } else {
            continue;
          }
        } else if (smallestIdxs[i] == 2) { // go left
          if (robot8neigh[3] != Cell.Wall) {
            new8neigh = get8neigh(robotx-1, roboty, state.board);
          } else {
            continue;
          }
        } else { // go right
          if (robot8neigh[5] != Cell.Wall) {
            new8neigh = get8neigh(robotx+1, roboty, state.board);
          } else {
            continue;
          }
        }
        
        futureWallCounts[i] = 0;
        // count the number of walls
        for (int j = 0; j < 9; j++) {
          if (new8neigh[j] == Cell.Wall) {
            futureWallCounts[i]++;
          }
        }
        
      }
      
    }
    
    
    List<Command> nextStep = new ArrayList<Command>();
    // look at the future wall counts to select the best direction, i.e the direction that leads to a less-walled area
    if (futureWallCounts[0] != 255 & (futureWallCounts[0] <= futureWallCounts[1])) {
      nextStep.add(getDirection(smallestIdxs[0]));
    } else if (futureWallCounts[1] != 255 & (futureWallCounts[1] < futureWallCounts[0])) {
      nextStep.add(getDirection(smallestIdxs[1]));
    } else {
      // don't know what to do here
      return null;
    }
    return nextStep;

    
  }
  
  /**
   * get 8-neighborhood
   * @param x should be > 0
   * @param y should be > 0
   * @param b board
   * @return
   */
  public Cell[] get8neigh(int x, int y, Board b) {
    Cell[] robot8neigh = new Cell[3*3];
    for (int j = 0; j < 3; j++) {
      for (int i = 0; i < 3; i++) {
        robot8neigh[i+3*j] = b.get(x-1+i, y-1+j);
      }
    }
    
    return robot8neigh;
  }

  public int[] computeWallSum(Cell[] robot8neigh) {
    int wallSum[] = new int[4];
    for (int i = 0; i < 9; i++) {
      if (robot8neigh[i] == Cell.Wall) {
        if (i<3) { // wall on top
          wallSum[0]++;
        } else if (i>5) { // wall on bottom
          wallSum[1]++;
        }
        
        if (i%3 == 0) { // wall left
          wallSum[2]++;
        } else if (i%3 == 2) { // wall right
          wallSum[3]++;
        }
      }
    }
    
    return wallSum;
  }
  
  public Command getDirection(int dir) {
    switch (dir) {
    case 0:
      return Command.Up;
    case 1:
      return Command.Down;
    case 2:
      return Command.Left;
    case 3:
      return Command.Right;
    }
    return null;
  }
}
