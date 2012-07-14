package game.fitness;

import game.State;

/**
 * @author seba
 */
public class Scoring {
  public static int totalScore(int steps, int collectedLambdas, boolean abort, boolean win) {
    return -steps + (25 * collectedLambdas) + (abort ? 25 * collectedLambdas : 0) + (win ? 50 * collectedLambdas : 0);
  }
  
  public static int maximalScore(State state) {
    return Scoring.totalScore(0, state.lambdaPositions.size() + state.collectedLambdas, false, true);
  }
}
