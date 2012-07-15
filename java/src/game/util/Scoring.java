package game.util;

import game.State;

/**
 * @author seba
 */
public class Scoring {
  public static int totalScore(int steps, int collectedLambdas, boolean abort, boolean win) {
    return -steps + (25 * collectedLambdas) + (abort ? 25 * collectedLambdas : 0) + (win ? 50 * collectedLambdas : 0);
  }

  /**
   * An upper bound on the score we can get for an optimal strategy, starting
   * from the initial state.
   * 
   * <p>
   * The {@code state} parameter is only used to learn how many lambdas there
   * are.
   */
  public static int maximalScore(State state) {
    return Scoring.totalScore(0, state.lambdaPositions.size() + state.collectedLambdas, false, true);
  }

  /**
   * An upper bound on the score we can get starting from the given state.
   */
  public static int maximalReachableScore(State state) {
    return Scoring.totalScore(state.steps, state.lambdaPositions.size() + state.collectedLambdas, false, true);
  }
}
