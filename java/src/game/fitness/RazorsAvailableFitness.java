package game.fitness;

import game.Cell;
import game.State;
import game.ai.Fitness;

/**
 * Prefers having enough razors available
 * @author Felix Rieger
 *
 */
public class RazorsAvailableFitness implements Fitness {

  @Override
  public int evaluate(State s) {
    int beardCount = s.board.bitsets[Cell.Beard.ordinal()].size();
    
    if (beardCount == 0) {
      return 1000000;
    }
    int minRazorsNeeded = (beardCount / 8)+1; // minimal amount of razors needed to remove all beards
    
    int razors = s.board.razors;
    
    if (razors > minRazorsNeeded) {
      return 1000000;   // always good to have enough razors
    }
    
    return (int) (((double) razors / minRazorsNeeded) * 1000000);
  }

}
