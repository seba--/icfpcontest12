package game.ai;

import game.State;

/**
 * A scorer that returns the average score of a set of other scorers.
 * 
 * @author Tillmann Rendel
 * @author Thomas Horstmeyer
 */
public class MultiScorer implements Scorer {

  public Scorer[] scorers;

  public MultiScorer(Scorer... scorers) {
    this.scorers = scorers;
  }

  @Override
  public int score(State state) {
    int sum = 0;
    for (Scorer scorer : scorers) {
      sum += scorer.score(state);
    }

    return sum / scorers.length;
  }
}
