package game.strategy;

import game.Cell;
import game.Command;
import game.State;
import game.StaticConfig;
import game.ai.Strategy;
import game.strategy.tom.Helpers;

import java.util.List;

public class ClosestManhattanLift extends Strategy {

  private final StaticConfig sconfig;
  
  public ClosestManhattanLift(StaticConfig sconfig) {
    this.sconfig = sconfig;
  }
  
  @Override
  public List<Command> apply(State s) {
    return Helpers.moveToSimple(s, sconfig.liftx, sconfig.lifty);
  }
  
  @Override
  public boolean wantsToApply(State s) {
    return s.lambdaPositions.isEmpty() && s.board.bitsets[Cell.HoRock.ordinal()].isEmpty();
  }
  
  @Override
  public String toString() {
    return "ClosestManhattanLift";
  }

}
