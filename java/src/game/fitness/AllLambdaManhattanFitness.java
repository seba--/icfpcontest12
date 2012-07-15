package game.fitness;

import util.MathUtil;
import game.State;
import game.ai.Fitness;

/**
 * Tries to minize overall distance to all lambdas.
 * 
 * @author seba
 */
public class AllLambdaManhattanFitness implements Fitness {

  /** (non-Javadoc)
   * @see game.ai.Fitness#evaluate(game.State)
   */
  @Override
  public int evaluate(State state) {
    int sum = 0;
    
    for (int pos : state.lambdaPositions)
      sum += MathUtil.distanceToPos(state.robotCol, state.robotRow, pos, state.board.height);
    
    return sum;
  }

}
