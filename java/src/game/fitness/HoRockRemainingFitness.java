package game.fitness;

import game.Cell;
import game.State;
import game.ai.Fitness;
public class HoRockRemainingFitness implements Fitness {

  public final int initialHoRocks;
  public HoRockRemainingFitness(State initialState) {
    initialHoRocks = initialState.board.bitsets[Cell.HoRock.ordinal()].size();
  }
  
  @Override
  public int evaluate(State state) {
    if (initialHoRocks == 0) 
      return 1000000;
    
    int hoRockCount = state.board.bitsets[Cell.HoRock.ordinal()].size();
    return (int) ((1-(hoRockCount / ((double) initialHoRocks)))*1000000);
  }

}
