package game.ai;

import game.State;

/**
 * Evaluates a state. The driver will explore states with higher score earlier.
 * 
 * @author Tillmann Rendel
 * @author Thomas Horstmeyer
 */
public interface Fitness {
  /**
   * Returns the score for a state.
   * 
   * The score is a number between 0 and 1,000,000. 0 means: should be ignored.
   * 1,000,000 means: should be expanded.
   */
  public int evaluate(State state);
}
