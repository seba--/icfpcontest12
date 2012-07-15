package game.strategy;

import java.util.List;

import game.Cell;
import game.Command;
import game.State;
import game.ai.Strategy;
import game.strategy.tom.Helpers;

public class ClosestWalkTrampoline extends Strategy {
 
  public ClosestWalkTrampoline() {
  }
  
  @Override
  public List<Command> apply(State s) {
   
    return Helpers.moveToCell(s, Cell.Trampoline, 1);
  }
  
  @Override
  public boolean wantsToApply(State s) {
    return !s.board.bitsets[Cell.Trampoline.ordinal()].isEmpty();
  }
  
  @Override
  public String toString() {
    return "ClosestWalkTrampoline";
  }
}
