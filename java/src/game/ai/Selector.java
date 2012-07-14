package game.ai;

import game.State;

import java.util.List;

/**
 * A strategy for selecting which strategy to use.
 * 
 * @author Tillmann Rendel
 * @author Thomas Horstmeyer
 */
public interface Selector {

  /**
   * Selects a strategy for the given state.
   * 
   * @param state
   * @return {@code null} if there's no strategy to apply, a strategy otherwise.
   */
  public abstract Strategy selectStrategy(State state);

  /**
   * Prepares a newly created state for strategy selection.
   * 
   * @param state
   */
  public abstract void prepareState(State state);

  /**
   * Returns a list that contains (at least) all strategies this selector ever
   * selected.
   */
  public List<Strategy> getUsedStrategies();
}