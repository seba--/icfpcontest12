package game.ai;

import game.Command;
import game.Ending;
import game.State;
import game.StaticConfig;
import game.config.IDriverConfig;
import game.fitness.AverageFitness;
import game.fitness.ManhattanDirectedFitness;
import game.fitness.ScoreFitness;
import game.fitness.Scoring;
import game.fitness.StepCountFitness;
import game.log.Log;
import game.selector.SimpleSelector;
import game.stepper.MultiStepper;
import game.stepper.SingleStepper;
import game.ui.SimulateWindow;
import interrupt.ExitHandler;

import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

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

  public final StaticConfig sconfig;
  public final State initialState;
  public final MultiStepper stepper;
  public Comparator<State> comparator = new FitnessComparator();
  public PriorityQueue<State> liveStates = new PriorityQueue<State>(PRIORITY_QUEUE_CAPACITY, comparator);
  // public Set<State> seenStates = new HashSet<State>();
  public HashMap<Integer, State> seenStates = new HashMap<Integer, State>();
  public Fitness fitness;
  public Selector strategySelector;

  public State bestState;

  public boolean finished = false;
  public int iterations;
  public boolean timed = false;
  public long endTime = 0;
  public boolean simulate;

  public long lastPrintInfoTime;
  public int lastPrintInfoIter;

  public Driver(StaticConfig sconfig, State initialState, Selector strategySelector, Fitness fitness, boolean simulate) {
    this.sconfig = sconfig;
    this.initialState = initialState;
    this.strategySelector = strategySelector;
    this.fitness = fitness;
    this.stepper = new MultiStepper(sconfig);
    this.simulate = simulate;
  }

  /**
   * @param sconfig
   * @param initialState
   * @param strategySelector
   * @param fitness
   * @param lifetime
   *          max runtime in seconds
   */
  public Driver(StaticConfig sconfig, State initialState, Selector strategySelector, Fitness fitness, boolean simulate, int lifetime) {
    this.sconfig = sconfig;
    this.initialState = initialState;
    this.strategySelector = strategySelector;
    this.fitness = fitness;
    this.stepper = new MultiStepper(sconfig);
    this.endTime = System.currentTimeMillis() + (lifetime * 1000);
    this.timed = true;
    this.simulate = simulate;
  }

  public void solve(State initial) {
    strategySelector.prepareState(initial);
    liveStates.add(initial);
    initial.fitness = fitness.evaluate(initial);

    bestState = initial;

    // TODO when to stop?

    iterations = 0;

    // choose k, M, G more cleverly
    // choose 5000k more cleverly
    // explain final solution (what strategies)

    printDataHeader();
    while (!liveStates.isEmpty()) {
      // TODO why is this needed?
      if (timed && System.currentTimeMillis() >= endTime) {
        printDataRow();
        break;
      }

      iterations++;

      State state = liveStates.peek();

      if (iterations % 5000 == 0) {
        printDataRow();
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
          if (stateShouldBeConsidered(newState)) {
            seenStates.put(newState.board.hashCode(), newState);
            // Log.printf("%s => %8d old fitness, %8d new fitness, %12d\n",
            // commands.get(0), state.fitness, fitness.evaluate(newState),
            // state.hashCode());

            if (newState.score > bestState.score) {
              bestState = newState;
            }

            if (newState.ending == Ending.None && newState.steps < newState.board.width * newState.board.height && Scoring.maximalReachableScore(newState) > bestState.score) {
              liveStates.add(newState);
              newState.fitness = fitness.evaluate(newState);
              strategySelector.prepareState(newState);
            }
          }
        }
      }
    }
  }

  private void printDataHeader() {
    Log.printf(" iter  |  score  |  live   |  dead   |  iter/sec  \n");
    Log.printf(" ------+---------+---------+---------+------------\n");

    lastPrintInfoIter = iterations;
    lastPrintInfoTime = System.currentTimeMillis();
  }

  private void printDataRow() {
    long now = System.currentTimeMillis();
    int iterPerSec = 1000 * (iterations - lastPrintInfoIter) / (int) (now - lastPrintInfoTime);

    Log.printf("%4dk  |  %5d  |  %4dk  |  %4dk  |  %7d  \n",
        iterations / 1000,
        bestState.score,
        liveStates.size() / 1000,
        (seenStates.size() - liveStates.size()) / 1000,
        iterPerSec);

    lastPrintInfoIter = iterations;
    lastPrintInfoTime = now;

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
      SingleStepper stepper = new SingleStepper(sconfig);
      SimulateWindow win = null;
      if (this.simulate) {
        win = new SimulateWindow(fitness, stepper);
      }
      State st = initialState;
      st.fitness = fitness.evaluate(st);
      Command[] commands = bestState.solution.allCommands();

      Log.println(st);
      Log.println();
      if (this.simulate) {
        win.addState(st);
      }
      for (Command command : commands) {
        st = stepper.step(st, command);
        st.fitness = fitness.evaluate(st);
        if (this.simulate) {
          win.addState(st);
        }
        // Log.println(st);
      }
      if (this.simulate) {
        win.setVisible(true);
      }
      Log.println(st);
      Log.println();
    }
  }

  // kill states that have no strategies left
  private void killState(State state) {
    liveStates.remove(state);
  }

  private State computeNextState(State state, List<Command> commands, Strategy strategy) {
    State result = stepper.multistep(state, commands);
    result.solution = new Solution(state.solution, commands.toArray(new Command[commands.size()]), strategy);
    return result;
  }

  public void finished() {
    this.finished = true;
    printSolution();
    simulateSolution();
  }

  public static Driver create(IDriverConfig dconfig, StaticConfig sconfig, State state) {
    Selector selector = dconfig.strategySelector(sconfig, state);
    Fitness fitness = dconfig.fitnessFunction(sconfig, state);
    boolean simulate = dconfig.simulateWindow();
    return new Driver(sconfig, state, selector, fitness, simulate);
  }

  public static Driver create(IDriverConfig dconfig, StaticConfig sconfig, State state, int lifetime) {
    Selector selector = dconfig.strategySelector(sconfig, state);
    Fitness fitness = dconfig.fitnessFunction(sconfig, state);
    boolean simulate = dconfig.simulateWindow();
    return new Driver(sconfig, state, selector, fitness, simulate, lifetime);
  }

  public void run() {
    ExitHandler.register(this);
    solve(initialState);
    ExitHandler.unregister(this);

    finished();
  }

  private boolean stateShouldBeConsidered(State s) {
    State oldState = seenStates.get(s.board.hashCode());

    if (oldState == null)
      return true;

    // anderes board -> behalten
    if (!s.board.equals(oldState.board))
      return true;

    // TODO: theoretisch koennen noch mehr verworfen werden, wenn wir mehr
    // aufheben... vllt nicht den aufwand wert.

    // gleiches board, weniger steps -> behalten
    if (s.steps < oldState.steps)
      return true;

    // gleiches board, weniger stepsUnderwater -> behalten
    if (s.stepsUnderwater < oldState.stepsUnderwater)
      return true;

    return false;
  }

  // TODO add exception handling?
  public static void main(String[] args) throws Exception {
    String text;
    if (args.length > 0) {
      text = FileCommands.readFileAsString(args[0]);
    } else {
      text = FileCommands.readAsString(new InputStreamReader(System.in));
    }
    Pair<StaticConfig, State> p = State.parse(text);
    StaticConfig sconfig = p.a;
    State state = p.b;

    IDriverConfig stdConfig = new IDriverConfig() {
      @Override
      public Selector strategySelector(StaticConfig sconfig, State initialState) {
        return new SimpleSelector(sconfig);
      }

      @Override
      public Fitness fitnessFunction(StaticConfig sconfig, State initialState) {
        return new AverageFitness(new ScoreFitness(), new StepCountFitness(), new ManhattanDirectedFitness(sconfig));
      }

      @Override
      public boolean simulateWindow() {
        return true;
      }

    };

    Driver.create(stdConfig, sconfig, state, 15).run();
  }
}
