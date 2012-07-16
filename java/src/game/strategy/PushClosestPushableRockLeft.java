package game.strategy;

import java.util.List;

import game.Cell;
import game.Command;
import game.State;
import game.ai.Strategy;
import game.strategy.tom.Helpers;
import game.strategy.tom.Scout;

public class PushClosestPushableRockLeft extends Strategy {
  final Cell[][] area = new Cell[][]{{Cell.Empty}, {Cell.Rock}, {Cell.AnyPassable}};
  final Scout scout = new Scout(area, 2, 0);
  
  List<Command> cmds;
  private final int searchDistance;
  
  public PushClosestPushableRockLeft(int searchDistance) {
    this.searchDistance = searchDistance;
  }
  
  @Override
  public List<Command> apply(State state) {
    cmds.add(Command.Left);
    return cmds;
  }
  
  @Override
  public boolean wantsToApply(State s) {
    cmds = Helpers.moveToAreaCell(s, scout, 1, searchDistance);
    return (cmds != null);
    
  }
  
  @Override
  public String toString() {
    return "PushClosestPushableRockLeft";
  }
  
}
