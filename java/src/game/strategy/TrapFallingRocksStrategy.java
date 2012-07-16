package game.strategy;

import java.util.Arrays;
import java.util.List;

import game.Cell;
import game.Command;
import game.State;
import game.ai.Strategy;

public class TrapFallingRocksStrategy extends Strategy{
  @Override
  public boolean wantsToApply(State s) {
    // look for a falling rock above
    // ?|?
    // ?_?
    // ?R?
    
    if ((s.board.get(s.robotCol, s.robotRow+1) == Cell.Empty) &&
       (s.board.get(s.robotCol, s.robotRow+2) == Cell.FallingRock)) {
         return true;
       }
       
    return false;
  }
  
  
  @Override
  public List<Command> apply(State s) {
    // ?|?  l  ?_?  r  ?_?  r  ?_?  l  ?_?
    // ?_?  -> ?|?  -> ?*?  -> ?_?  -> ?_?
    // ?R?     R_?     _R?     _|R     *R_  rock trapped!    
    return Arrays.asList(Command.Left, Command.Right, Command.Left, Command.Right);
  }
  
}
