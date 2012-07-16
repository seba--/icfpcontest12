package game.strategy;

import java.util.List;

import game.Cell;
import game.Command;
import game.State;
import game.ai.Strategy;
import game.strategy.tom.Helpers;

public class ClosestWalkHoRock extends Strategy {

	public ClosestWalkHoRock() {
		
	}
	
	@Override
	public List<Command> apply(State s) {
	  return Helpers.moveToCell(s, Cell.HoRock, 1);
	}
	  
	@Override
	public boolean wantsToApply(State s) {
	  return !s.board.bitsets[Cell.HoRock.ordinal()].isEmpty();
    }

	public String toString() {
		return "ClosestWalkHoRock";
	}

}
