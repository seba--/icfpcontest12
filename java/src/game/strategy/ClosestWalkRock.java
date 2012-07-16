package game.strategy;

import game.Cell;
import game.Command;
import game.State;
import game.ai.Strategy;
import game.strategy.tom.Helpers;

import java.util.ArrayList;
import java.util.List;

public class ClosestWalkRock extends Strategy {

  @Override
  public List<Command> apply(State state) {
    List<Command> cmds = Helpers.moveToCell(state, Cell.Rock, 1);
    if (cmds == null)
      return null;
    cmds = new ArrayList<Command>(cmds);
    cmds.remove(cmds.size() - 1); // do not actually go on top of the
                                  // higher-order rock, just go to the field
                                  // next to it.
                                  // there are better strategies for dealing
                                  // with higher-order rocks
    return cmds;
  }

  @Override
  public boolean wantsToApply(State s) {
    return !s.board.bitsets[Cell.Rock.ordinal()].isEmpty();
  }

  @Override
  public String toString() {
    return "ClosestWalkHoRock";
  }
}
