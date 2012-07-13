package game.ai;

import game.Board;
import game.Command;
import game.Ending;
import game.State;
import game.selector.SimpleSelector;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

/**
 * The main driver of the artifical intelligence.
 * 
 * @author Tillmann Rendel
 * @author Thomas Horstmeyer
 */

public class Driver {
  
  public static final int PRIORITY_QUEUE_CAPACITY = 5000;
  
  // state choosing: compute one integer score, store in state, use priority
  // queue.

  // TODO is contains on PriorityQueue fast enough?
  public Comparator<State> comparator = new ScorerScoreComparator();
  public PriorityQueue<State> liveStates = new PriorityQueue<State>(PRIORITY_QUEUE_CAPACITY, comparator);
  public Set<State> deadStates = new HashSet<State>();
  public Scorer scorer;
  public Selector strategySelector;

  public State bestState;

  public Driver(Selector strategySelector, Scorer scorer) {
    this.strategySelector = strategySelector;
  }

  public List<Command> solve(State initial) {
    strategySelector.prepareState(initial);
    liveStates.add(initial);

    bestState = initial;

    // TODO when to stop?

    while (!liveStates.isEmpty()) {
      State state = liveStates.poll();
      Strategy strategy = strategySelector.selectStrategy(state);

      if (strategy == null) {
        killState(state);
      } else {
        // apply strategy to create new state
        List<Command> commands = strategy.apply(state);

        if (commands != null) {
          assert (!commands.isEmpty());
          State newState = computeNextState(state, commands);
          if (!deadStates.contains(newState) && !liveStates.contains(newState))
            if (newState.score > bestState.score) {
              bestState = newState;
            }

          if (newState.ending == Ending.None) {
            liveStates.add(newState);
            strategySelector.prepareState(newState);
          }
        }
      }
    }

    return bestState.fromInitial();
  }

  // kill states that have no strategies left
  private void killState(State state) {
    liveStates.remove(state);
    deadStates.add(state);
  }

  private State computeNextState(State state, List<Command> commands) {
    // TODO Hook up to sebastian's code
    State result = null;
    return result;
  }

  public static void main(String[] args) {
    Selector selector = new SimpleSelector();
    Scorer scorer = new ScoreScorer();
    Driver driver = new Driver(selector, scorer);

    // from http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
    String text = new Scanner(System.in).useDelimiter("\\A").next();
    Board board = Board.parse(text);

    List<Command> solution = driver.solve(new State(board));
    for (Command command : solution) {
      System.out.append(command.shortName());
    }

    System.out.println("A");
  }
}
