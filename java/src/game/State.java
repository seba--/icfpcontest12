package game;

import game.ai.Strategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * @author seba
 */
public class State {
  public Board board;

  /**
   * How much score we got so far.
   */
  public int score;

  /**
   * Whether our scorer thinks we should explore this state.
   */
  public int scorerScore;

  /**
   * steps taken since start of game.
   */
  public int steps;

  /**
   * not yet applied strategies. gets set when state is created in driver.
   */
  public Set<Strategy> pendingStrategies = new HashSet<Strategy>();

  /**
   * The parent state. The parent state can be transformed into this state by
   * {@link #fromParent}. This field is {@code null} if this is the initial
   * state.
   * 
   * <p>
   * Needed for building the final command list.
   */
  public State parent;

  /**
   * The commands to reach this state from its parent state.
   */
  public List<Command> fromParent;

  public State(Board board, int steps) {
    this.board = board;
    this.score = 0;
    this.steps = steps;
  }

  /**
   * Return the commands to reach this state from the initial state.
   */
  public List<Command> fromInitial() {
    // collect all states from here to the initial state
    List<State> states = new ArrayList<State>();
    State state = this;
    while (state != null) {
      states.add(state);
    }
    
    // collect the commands to move from each state to the next
    List<Command> result = new ArrayList<Command>();
    ListIterator<State> iterator = states.listIterator(states.size());
    while (iterator.hasPrevious()) {
      result.addAll(iterator.previous().fromParent);
    }
    
    return result;
  }

  /**
   * A state is final if (1) we are dead (2) we won (3) we aborted.
   */
  public boolean isFinal() {
    // TODO Implement this
    return false;
  }
}
