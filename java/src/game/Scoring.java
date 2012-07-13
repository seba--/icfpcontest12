package game;

/**
 * @author seba
 */
public class Scoring {
  public static int totalScore(int steps, int collectedLambdas, boolean abort, boolean win) {
    return -steps + (25 * collectedLambdas) + (abort ? 25 * collectedLambdas : 0) + (win ? 50 * collectedLambdas : 0);
  }
}
