package game.strategy;

import java.util.Collections;
import java.util.List;

import game.Command;
import game.State;
import game.ai.Strategy;

public class DownStrategy extends Strategy {
  private  final static List<Command> cmds = Collections.singletonList(Command.Down); 
  
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
    int pos = s.robotCol * s.board.height + s.robotRow - 1;
    return s.board.isPosition(pos) && (s.board.isEarth(pos) || s.board.isEmpty(pos) || s.board.isLambda(pos)) || s.board.isTrampoline(pos);
  }
  
  @Override
  public String toString() {
    return "Down";
  }
}
