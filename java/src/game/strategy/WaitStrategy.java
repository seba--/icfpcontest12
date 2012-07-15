package game.strategy;

import java.util.Collections;
import java.util.List;

import game.Cell;
import game.Command;
import game.State;
import game.ai.Strategy;

public class WaitStrategy extends Strategy {
  private  final static List<Command> cmds = Collections.singletonList(Command.Wait); 
  
  @Override
  public List<Command> apply(State state) {
    return cmds;
  }
  
  @Override
  public boolean isUseOnce() {
    return true;
  }
  
  @Override
  public boolean wantsToApply(State s) {
    //only wait if something happens/moves
    //TODO: does one of these returns actually work
    return !s.board.bitsets[Cell.FallingRock.ordinal()].isEmpty();
    //return !s.activePositions.isEmpty();
  }
  
  @Override
  public String toString() {
    return "Wait";
  }
}
