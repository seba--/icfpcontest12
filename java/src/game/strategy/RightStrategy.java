package game.strategy;

import java.util.Collections;
import java.util.List;

import game.Command;
import game.State;
import game.ai.Strategy;

public class RightStrategy extends Strategy {
  private  final static List<Command> cmds = Collections.singletonList(Command.Right); 
  
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
    int pos1 = (s.robotCol + 1) * s.board.height + s.robotRow;
    int pos2 = (s.robotCol + 2) * s.board.height + s.robotRow;
    return s.board.isPosition(pos1) && (s.board.isEarth(pos1) || s.board.isEmpty(pos1) || s.board.isLambda(pos1) || (s.board.isPosition(pos2) && s.board.isEmpty(pos2) && (s.board.isRock(pos1) || s.board.isFallingRock(pos1))));
  }
  
  @Override
  public String toString() {
    return "Right";
  }
}
