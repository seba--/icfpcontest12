package game.fitness;

import game.State;
import game.ai.Fitness;

/**
 * Rewards eating lambdas.
 * 
 * @author seba
 *
 */
public class LambdasLeftFitness implements Fitness {

  /* (non-Javadoc)
   * @see game.ai.Fitness#evaluate(game.State)
   */
  @Override
  public int evaluate(State state) {
    if (state.previousState != null && 
        state.previousState.lambdaPositions.size() < state.lambdaPositions.size())
      return 1000000;
    return 0;
  }

}
