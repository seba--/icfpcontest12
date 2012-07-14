package game.strategy;

import game.Board;
import game.Cell;
import game.Command;
import game.State;
import game.ai.Strategy;
import game.util.MapUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Try to dig into earth for as many consecutive steps as possible
 * Digging preference: Left, Right, Down, Up
 * @author Felix Rieger
 *
 */
public class DiggingStrategy extends Strategy {

  @Override
  public List<Command> apply(State state) {
    //Cell[] surroundings = MapUtil.get4neigh(state.robotCol, state.robotRow, state.board);
    
    int[] digDistance = new int[]{0,0,0,0};
    
    for (int i = 0; i < 4; i++) {
      if ((i == 2) && ((state.board.get(state.robotCol, state.robotRow+1) == Cell.Rock) || (state.board.get(state.robotCol, state.robotRow+1) == Cell.FallingRock)))
        continue; // rock check on going down
      digDistance[i] = getMaxDiggingDistance(i, state.board, state.robotCol, state.robotRow);
    }
    
    List<Command> cl = new ArrayList<Command>(8);
    
    
    if (digDistance[1] >= 2 && digDistance[1] >= digDistance[0] && digDistance[1] >= digDistance[2] && digDistance[1] >= digDistance[3]) {
      // go LEFT
      for (int i = 0; i < digDistance[1]; i++) {
        cl.add(Command.Left);
      }
    } else if (digDistance[3] >= 2 && digDistance[3] >= digDistance[0] && digDistance[3] >= digDistance[2]) {
      // go RIGHT
      for (int i = 0; i < digDistance[3]; i++) {
        cl.add(Command.Right);
      }
    } else if (digDistance[0] >= 2 && digDistance[0] >= digDistance[2]) {
      // go DOWN
      for (int i = 0; i < digDistance[0]; i++) {
        cl.add(Command.Down);
      }
    } else if (digDistance[2] >= 2){
      // go UP
      for (int i = 0; i < digDistance[2]; i++) {
        cl.add(Command.Up);
      }
    } else { // if we can not dig more than 2 consecutive steps, don't bother
      return null;
    }
    
    return cl;
    
    /*
    if (surroundings[1] == Cell.Earth) {
      Cell[] s2 = MapUtil.get4neigh(state.robotCol-1, state.robotRow, state.board);
      if (s2[1] == Cell.Earth) {
        // select this state
        return Arrays.asList(new Command[]{Command.Left, Command.Left});
      }
    }
    
    if (surroundings[3] == Cell.Earth) {
      Cell[] s2 = MapUtil.get4neigh(state.robotCol+1, state.robotRow, state.board);
      if (s2[3] == Cell.Earth) {
        // select this state
        return Arrays.asList(new Command[]{Command.Right, Command.Right});
      }
    } 
    
    if (surroundings[0] == Cell.Earth) { // earth DOWN
      Cell[] s2 = MapUtil.get4neigh(state.robotCol, state.robotRow-1, state.board);
      if (s2[0] == Cell.Earth) {
        // select this state
        return Arrays.asList(new Command[]{Command.Down, Command.Down});
      }
    } 
    
    if (surroundings[2] == Cell.Earth) {
      Cell[] s2 = MapUtil.get4neigh(state.robotCol, state.robotRow+1, state.board);
      if (s2[2] == Cell.Earth) {
        // select this state
        return Arrays.asList(new Command[]{Command.Up, Command.Up});
      }
    } */    
  }
  
  public static int getMaxDiggingDistance(int direction, Board b, int x, int y) {
    Cell[] neigh = MapUtil.get4neigh(x, y, b);
    int xoffset = 0;
    int yoffset = 0;
    switch (direction) {
    case 0:
      yoffset = -1;
      break;
    case 1:
      xoffset = -1;
      break;
    case 2:
      yoffset = 1;
      break;
    case 3:
      xoffset = 1;
    }

    if (neigh[direction] == Cell.Earth) {
      return 1 + getMaxDiggingDistance(direction, b, x+xoffset, y+yoffset);
    }
    return 0;
  }
  
  
  @Override
  public boolean wantsToApply(State state) {
    Cell[] surroundings = MapUtil.get4neigh(state.robotCol, state.robotRow, state.board);

    if (surroundings[0] != Cell.Earth)
      if (surroundings[1] != Cell.Earth)
        if (surroundings[2] != Cell.Earth)
          if (surroundings[3] != Cell.Earth)
            return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "DiggingStrategy";
  }

}
