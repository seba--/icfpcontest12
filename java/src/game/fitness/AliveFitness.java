package game.fitness;

import game.State;
import game.ai.Fitness;

/**
 * Dying is bad, aborting is not so good, winning and continuing is good.
 * 
 * @author Sebastian Erdweg
 */
public class AliveFitness implements Fitness {

  /**
   * @see game.ai.Fitness#evaluate(game.State)
   */
  @Override
  public int evaluate(State state) {
    switch (state.ending) {
    case LoseRock:
    case LoseWater:
      return 0;
    case Abort:
      return 500000;
    case None:
    case Win:
    default:
      return 1000000;
    }
  }

}
