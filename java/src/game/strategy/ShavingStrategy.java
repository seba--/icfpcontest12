package game.strategy;

import java.util.Arrays;
import java.util.List;

import game.Cell;
import game.Command;
import game.State;
import game.ai.Strategy;

public class ShavingStrategy extends Strategy {

  @Override
  public List<Command> apply(State state) {
    return Arrays.asList(Command.Shave);
  }
  
  @Override
  public boolean wantsToApply(State s) {
    if (s.board.razors == 0 || s.board.bitsets[Cell.Beard.ordinal()].isEmpty()) {
      return false;
    }

    int rCol = s.robotCol;
    int rRow = s.robotRow;

    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        if (s.board.get(rCol+i, rRow+j) == Cell.Beard) {
          return true;
        }
      }
    }
    
    return false;
    
  }

  @Override
  public String toString() {
    return "Shaving";
  }

}
