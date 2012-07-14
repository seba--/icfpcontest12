package game.strategy;

import game.Cell;
import game.Command;
import game.State;
import game.ai.Strategy;
import game.util.MapUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Try to dig into earth for 2 consecutive steps
 * Digging preference: Left, Right, Down, Up
 * @author Felix Rieger
 *
 */
public class DiggingStrategy extends Strategy {

  @Override
  public List<Command> apply(State state) {
    Cell[] surroundings = MapUtil.get4neigh(state.robotCol, state.robotRow, state.board);
    
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
    } 
    
    return null;
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

}
