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
  public int robotCol;
  public int robotRow;
  public int lambdasLeft;
  public int collectedLambdas;
  public Ending ending;

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

  
  public State(Board board, int score, int robotCol, int robotRow, int lambdasLeft, int collectedLambdas, int steps) {
    this.board = board;
    this.score = score;
    ending = Ending.None;
    
    this.robotCol = robotCol;
    this.robotRow = robotRow;
    
    this.collectedLambdas = collectedLambdas;
    this.lambdasLeft = lambdasLeft;
    
    this.steps = steps;
  }
  
  /**
   * Auto-initialize state from initial board.
   */
  public State(Board board) {
    this.board = board;
    this.score = 0;
    this.collectedLambdas = 0;
    this.ending = Ending.None;
    this.steps = 0;
    
    int rcol = -1;
    int rrow = -1;
    int lambas = 0;
    
    for (int col = 0; col < board.width; ++col)
      for (int row = 0; row < board.height; ++row)
        switch (board.grid[col][row]) {
        case Robot:
          rcol = col;
          rrow = row;
          break;
        case Lambda:
          lambas++;
          break;
        default:
          ;
        }
    
    this.robotCol = rcol;
    this.robotRow = rrow;
    this.lambdasLeft = lambas;
  }
  
  public State makeFinal() {
    /*
     * From now on, you should not change any of the fields stored in this object.
     * We don't enforce that, though.
     */
    return this;
  }
}
