package game.strategy;

import java.util.Collections;
import java.util.List;

import game.Command;
import game.State;
import game.ai.Strategy;

public class UpStrategy extends Strategy {
  private  final static List<Command> cmds = Collections.singletonList(Command.Up); 
  
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
    int pos = (s.robotCol + 1) * s.board.height + s.robotRow;
    return s.board.isEarth(pos) || s.board.isEmpty(pos) || s.board.isLambda(pos);
  }

}
