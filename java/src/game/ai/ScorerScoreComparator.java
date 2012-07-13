package game.ai;

import game.State;

import java.util.Comparator;

/**
 * Compares two states according to their scorer evaluation.
 * 
 * @author Tillmann Rendel
 * @author Thomas Horstmeyer
 */

public class ScorerScoreComparator implements Comparator<State> {
  public int compare(State a, State b) {
    return Integer.compare(a.scorerScore, b.scorerScore);
  }
}
