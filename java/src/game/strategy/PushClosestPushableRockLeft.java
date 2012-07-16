package game.strategy;

import java.util.List;

import game.Cell;
import game.Command;
import game.State;
import game.ai.Strategy;
import game.strategy.tom.Helpers;
import game.strategy.tom.Scout;

public class PushClosestPushableRockLeft extends Strategy {

  @Override
  public List<Command> apply(State state) {
    //Cell[][] area = new Cell[][] {{Cell.AnyPassable, Cell.Rock, Cell.Empty}};
    //Scout s1 = new Scout(area, 0, 0);
    
    Cell[][] area = new Cell[][]{{Cell.Empty}, {Cell.Rock}, {Cell.AnyPassable}};
    Scout scout = new Scout(area, 2, 0);
    
    List<Command> cmds = Helpers.moveToAreaCell(state, scout, 1);
    if (cmds == null) return null;
    
    cmds.add(Command.Left);
    
    return cmds;
  }

  @Override
  public String toString() {
    return "PushClosestPushableRockLeft";
  }
}
