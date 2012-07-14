package game.ai;

import game.Command;
import game.Ending;
import game.State;
import game.StaticConfig;
import game.fitness.AverageFitness;
import game.fitness.ManhattanDirectedFitness;
import game.fitness.Scoring;
import game.fitness.StepCountFitness;
import game.log.Log;
import game.selector.SimpleSelector;
import game.stepper.MultiStepper;
import game.stepper.SingleStepper;
import interrupt.ExitHandler;

import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import util.FileCommands;
import util.Pair;

/**
 * The main driver of the artifical intelligence.
 * 
 * @author Tillmann Rendel
 * @author Thomas Horstmeyer
 */

public class Driver {

  public static final int PRIORITY_QUEUE_CAPACITY = 5000;

  // TODO is contains on PriorityQueue fast enough?
  
  public final StaticConfig sconfig;
  public final State initialState;
  public final MultiStepper stepper;
  public Comparator<State> comparator = new FitnessComparator();
  public PriorityQueue<State> liveStates = new PriorityQueue<State>(PRIORITY_QUEUE_CAPACITY, comparator);
  public Set<State> deadStates = new HashSet<State>();
  public Fitness fitness;
  public Selector strategySelector;

  public State bestState;

  public Driver(StaticConfig sconfig, State initialState, Selector strategySelector, Fitness fitness) {
    this.sconfig = sconfig;
    this.initialState = initialState;
    this.strategySelector = strategySelector;
    this.fitness = fitness;
    this.stepper = new MultiStepper(sconfig);
  }

  public void solve(State initial) {
    strategySelector.prepareState(initial);
    liveStates.add(initial);
    initial.fitness = fitness.evaluate(initial);

    bestState = initial;

    // TODO when to stop?

    int iterations = 0;
    
    // choose k, M, G more cleverly
    // choose 5000k more cleverly
    // explain final solution (what strategies)

    Log.printf(" iter  |  score  |  live   |  dead   \n");
    Log.printf(" ------+---------+---------+---------\n");

    while (!liveStates.isEmpty()) {
      iterations++;
      
      State state = liveStates.peek();

      if (iterations % 5000 == 0) {
        Log.printf("%4dk  |  %5d  |  %4dk  |  %4dk  \n",
            iterations / 1000,
            bestState.score,
            liveStates.size() / 1000,
            deadStates.size() / 1000);
      }

      Strategy strategy = strategySelector.selectStrategy(state);

      if (strategy == null) {
        killState(state);
      } else {
        // apply strategy to create new state
        List<Command> commands = strategy.apply(state);
        strategy.applicationCount++;

        if (commands != null) {
          assert (!commands.isEmpty());
          State newState = computeNextState(state, commands, strategy);
          if (!deadStates.contains(newState) && !liveStates.contains(newState)) {
            if (newState.score > bestState.score) {
              bestState = newState;
            }

            if (newState.ending == Ending.None &&
                newState.steps < newState.board.width * newState.board.height &&
                Scoring.maximalReachableScore(newState) > bestState.score ) {
              liveStates.add(newState);
              newState.fitness = fitness.evaluate(newState);
              strategySelector.prepareState(newState);
            }
          }
        }
      }
    }

  }

  /**
   * Print known best solution to System.out
   */
  public void printSolution() {
    // TODO: make threadsafe, might be called from exit handler
    // while already outputting... (use StringBuilder perhaps?!)

    // print statistics
    Log.printf("\nStrategy applications:\n");
    for (Strategy strategy : strategySelector.getUsedStrategies()) {
      Log.printf("  %3dk %s\n", strategy.applicationCount / 1000, strategy);
    }
    Log.println();

    // print solution
    if (bestState.solution != null) {
      Command[] commands = bestState.solution.allCommands();
      for (Command command : commands) {
        System.out.append(command.shortName());
      }
    }
    System.out.println("A");
    System.out.flush();
  }
  
  public void simulateSolution() {
    if (bestState.solution != null) {
      State st = initialState;
      SingleStepper stepper = new SingleStepper(sconfig);
      Command[] commands = bestState.solution.allCommands();

      Log.println(st);
      Log.println();
      for (Command command : commands) {
        st = stepper.step(st, command);
        Log.println(st);
        Log.println();
      }
    }
  }

  // kill states that have no strategies left
  private void killState(State state) {
    liveStates.remove(state);
    deadStates.add(state);
  }

  private State computeNextState(State state, List<Command> commands, Strategy strategy) {
    State result = stepper.multistep(state, commands);
    result.solution = new Solution(state.solution, commands.toArray(new Command[commands.size()]), strategy);
    return result;
  }
  
  public void finished() {
    printSolution();
    simulateSolution();
  }

  // TODO add exception handling?
  public static void main(String[] args) throws Exception {
    String text;
    if (args.length > 0) {
      text = FileCommands.readFileAsString(args[0]);
    } else {
      text = FileCommands.readAsString(new InputStreamReader(System.in));
    }
    text = text.replace("\r", "");
    Pair<StaticConfig, State> p = State.parse(text);
    StaticConfig sconfig = p.a;
    State state = p.b;

    Selector selector = new SimpleSelector(sconfig);
    Fitness scorer = new AverageFitness(new StepCountFitness(), new ManhattanDirectedFitness(sconfig));
    Driver driver = new Driver(sconfig, state, selector, scorer);
    
    ExitHandler.register(driver);
    driver.solve(state);
    ExitHandler.unregister(driver);
    driver.finished();
  }
}
