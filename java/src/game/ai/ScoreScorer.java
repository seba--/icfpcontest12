package game.ai;

import game.State;

/**
 * Evaluates how many points we already secured in a state.
 * 
 * @author Tillmann Rendel
 */

public class ScoreScorer implements Scorer {
  @Override
  public int score(State state) {
    // TODO need to scale to 0 .. 1,000,000
    return state.score;
  }
}
