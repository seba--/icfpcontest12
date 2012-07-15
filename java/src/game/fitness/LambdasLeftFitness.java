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
    return (int) (((double) state.collectedLambdas / (state.collectedLambdas + state.lambdaPositions.size())) * 1000000);
  }

}
