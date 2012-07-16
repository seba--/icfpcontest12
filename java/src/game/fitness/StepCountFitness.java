package game.fitness;

import game.State;
import game.ai.Fitness;

/**
 * Tries to minimizes numbers of steps.
 * 
 * @author seba
 *
 */
public class StepCountFitness implements Fitness {

  /* (non-Javadoc)
   * @see game.ai.Fitness#evaluate(game.State)
   */
  @Override
  public int evaluate(State state) {
    int max = state.board.width * state.board.height;
    return (int) ((1 - ((double) state.steps / max)) * 100000);
  }

}
