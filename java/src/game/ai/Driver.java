package game.ai;

import game.Command;
import game.Ending;
import game.State;
import game.StaticConfig;
import game.config.IDriverConfig;
import game.config.SimpleSelectorConfig;
import game.log.Log;
import game.stepper.MultiStepper;
import game.stepper.SingleStepper;
import game.ui.SimulateWindow;
import game.util.Scoring;
import interrupt.ExitHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import util.FileCommands;
import util.MathUtil;
import util.Pair;

/**
 * The main driver of the artifical intelligence.
 * 
 * @author Tillmann Rendel
 * @author Thomas Horstmeyer
 */
public class Driver {
  public static final int PRIORITY_QUEUE_CAPACITY = 5000;

  public final String name;
  
  public final StaticConfig sconfig;
  public final State initialState;
  public final MultiStepper stepper;
  public Comparator<State> comparator = new FitnessComparator();
  
  /*
   * Elements are ordered in ascending order: poll() retrieves the smallest state.
   */
//  public PriorityQueue<State> liveStates = new PriorityQueue<State>(PRIORITY_QUEUE_CAPACITY, comparator);
  
  /*
   * Elements collected in list.
   */
  public ArrayList<State> liveStates = new ArrayList<State>(PRIORITY_QUEUE_CAPACITY);
  public double liveStatesAverageFitness;
  public double liveStatesAverageSteps;
  
  // public Set<State> seenStates = new HashSet<State>();
  public HashMap<Integer, State> seenStates = new HashMap<Integer, State>();
  public Fitness fitness;
  public Selector strategySelector;

  public State bestState;

  public boolean finished = false;
  public int iterations;
  public boolean timed = false;
  public long endTime = 0;

  public long lastPrintInfoTime;
  public int lastPrintInfoIter;

  public Driver(String name, StaticConfig sconfig, State initialState, Selector strategySelector, Fitness fitness) {
    this.name = name;
    this.sconfig = sconfig;
    this.initialState = initialState;
    this.strategySelector = strategySelector;
    this.fitness = fitness;
    this.stepper = new MultiStepper(sconfig);
  }

  /**
   * @param sconfig
   * @param initialState
   * @param strategySelector
   * @param fitness
   * @param lifetime
   *          max runtime in seconds
   */
  public Driver(String name, StaticConfig sconfig, State initialState, Selector strategySelector, Fitness fitness, int lifetime) {
    this.name = name;
    this.sconfig = sconfig;
    this.initialState = initialState;
    this.strategySelector = strategySelector;
    this.fitness = fitness;
    this.stepper = new MultiStepper(sconfig);
    this.endTime = System.currentTimeMillis() + (lifetime * 1000);
    this.timed = true;
  }

  public void solve(State initial) {
    strategySelector.prepareState(initial);
    initial.fitness = fitness.evaluate(initial);
    liveStates.add(initial);
    liveStatesAverageFitness = initial.fitness;
    liveStatesAverageSteps = initial.steps;

    bestState = initial;

    // TODO when to stop?

    iterations = 0;

    // choose k, M, G more cleverly
    // choose 5000k more cleverly

    printDataHeader();
    
    while (!liveStates.isEmpty()) {
      if (timed && System.currentTimeMillis() >= endTime) {
        // timeout for testing
        printDataRow();
        break;
      }

      iterations++;
      printDataRow();

      ArrayList<State> newStates = new ArrayList<State>(PRIORITY_QUEUE_CAPACITY);
      double newStatesFitnessNormalized = 0;
      double newStatesStepsNormalized = 0;
      double newStatesNormalizer = liveStates.size();
      
      for (int i = 0; i < liveStates.size(); ) {
        State state = liveStates.get(i);

        Strategy strategy = strategySelector.selectStrategy(state);
        
        if (strategy == null) {
          liveStates.remove(i);
          // do not increase 'i'
        } else {
          i++;
          
          // apply strategy to create new state
          List<Command> commands = strategy.apply(state);
          strategy.applicationCount++;
          
          if (commands != null) {
            assert (!commands.isEmpty());

            State newState = computeNextState(state, commands, strategy);
            
//            if (stateShouldBeConsidered(newState)) {
//              seenStates.put(newState.board.hashCode(), newState);
              
              if (newState.score > bestState.score) {
                bestState = newState;
              }
              
              if (newState.ending == Ending.None &&
                  newState.steps < newState.board.width * newState.board.height && 
                  Scoring.maximalReachableScore(newState) > bestState.score) {
                newState.fitness = fitness.evaluate(newState);
                
                if (true) {
                  // the kid is better than the average of all parents
                  newStates.add(newState);
                  newStatesFitnessNormalized += newState.fitness / newStatesNormalizer;
                  newStatesStepsNormalized += newState.steps / newStatesNormalizer;
                  strategySelector.prepareState(newState);
                }
              }
//            }
          }
        }
      }
      
      double newStatesAverageFitness = newStatesFitnessNormalized * newStatesNormalizer / newStates.size();
      double newStatesAverageSteps = newStatesStepsNormalized * newStatesNormalizer / newStates.size();
      

      int MAX_NUM_STATES = 10000;
      int newStatesCount = newStates.size();
      double size = liveStates.size() + newStatesCount;
      double averageFitness = (liveStatesAverageFitness * (liveStates.size() / size) + newStatesAverageFitness * (newStatesCount / size));
      
      double spaceFactor = (double) MAX_NUM_STATES / size;
      double fitnessFactor = s.fitness / averageFitness;
      
      boolean keep = spaceFactor * fitnessFactor > MathUtil.RANDOM.nextDouble();


      if (newStates.size() < 1000) {
        newStates.addAll(liveStates);
        if (newStatesCount != 0) {
          liveStatesAverageFitness = (newStatesAverageFitness + liveStatesAverageFitness) / 2;
          liveStatesAverageSteps = (newStatesAverageSteps + liveStatesAverageSteps) / 2;
        }
      }
      else {
        double oldFitness = liveStatesAverageFitness;
        double oldSteps = liveStatesAverageSteps;
        liveStatesAverageFitness = 0;
        liveStatesAverageSteps = 0;
        for (int i = 0; i < liveStates.size(); i++) {
          State s = liveStates.get(i);
          if ((s.fitness > oldFitness ||
               s.steps < oldSteps*0.0)) {
            newStates.add(s);
            liveStatesAverageFitness += s.fitness / (liveStates.size() / 2);
            liveStatesAverageSteps += s.steps / (liveStates.size() / 2);
          }
        }
        
        if (newStates.size() > newStatesCount) {
          liveStatesAverageFitness = liveStatesAverageFitness * (liveStates.size() / 2) / (newStates.size() - newStatesCount);
          liveStatesAverageFitness = (newStatesAverageFitness + liveStatesAverageFitness) / 2;
          
          liveStatesAverageSteps = liveStatesAverageSteps * (liveStates.size() / 2) / (newStates.size() - newStatesCount);
          liveStatesAverageSteps = (newStatesAverageSteps + liveStatesAverageSteps) / 2;
        }
        else {
          liveStatesAverageFitness = newStatesAverageFitness;
          liveStatesAverageSteps = newStatesAverageSteps;
        }
      }
        
      int keptOldStatesCount = newStates.size() - newStatesCount;

      Log.printf("was: %6d, keep: %6d, new: %6d, fitness: %f, steps: %3f\n",
          liveStates.size(), keptOldStatesCount, newStatesCount, liveStatesAverageFitness, liveStatesAverageSteps);
      
      liveStates = newStates;
    }
    
    printDataRow();
  }

  private void printDataHeader() {
    Log.printf(" iter  |  score  |  live   |  dead   |  iter/sec  \n");
    Log.printf(" ------+---------+---------+---------+------------\n");

    lastPrintInfoIter = iterations;
    lastPrintInfoTime = System.currentTimeMillis();
  }

  private void printDataRow() {
    long now = System.currentTimeMillis();
    int iterPerSec = 1000 * (iterations - lastPrintInfoIter) / (int) (now - lastPrintInfoTime+1);

    Log.printf("%4d  |  %5d  |  %4d  |  %4dk  |  %7d  \n",
        iterations,
        bestState.score,
        liveStates.size(),
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
      State st = initialState;
      st.fitness = fitness.evaluate(st);
      Log.println(st);
      Log.println();

      List<Solution> sols = new LinkedList<Solution>();
      Solution sol = bestState.solution;
      while (sol != null) {
        sols.add(0, sol);
        sol = sol.prefix;
      }
      
      for (Solution s : sols) {
        Log.println(s.strategy + ": " + Arrays.toString(s.commands));
        for (Command command : s.commands) {
          st = stepper.step(st, command);
          st.fitness = fitness.evaluate(st);
        }
      }
      
      Log.println(st);
      Log.println();
    }
  }
  
  public void simulationWindow() {
    State st = initialState;
    st.fitness = fitness.evaluate(st);
    SimulateWindow win = new SimulateWindow(name, fitness, stepper);
    win.addState(st);
    if (bestState.solution != null) {
	    Command[] commands = bestState.solution.allCommands();
	
	    for (Command command : commands) {
	      st = stepper.step(st, command);
	      st.fitness = fitness.evaluate(st);
	      win.addState(st);
	    }
    }
    win.setVisible(true);
  }

  // kill states that have no strategies left
  private void killState(State state) {
    liveStates.remove(state);
  }

  private State computeNextState(State state, List<Command> commands, Strategy strategy) {
    State result = stepper.multistep(state, commands);
    int stepsPerformed = result.steps - state.steps;
    Command[] usedCommands = new Command[stepsPerformed];
    for (int i = 0; i < stepsPerformed; i++)
      usedCommands[i] = commands.get(i);
    result.solution = new Solution(state.solution, usedCommands, strategy);
    return result;
  }

  public void finished() {
    this.finished = true;
    printSolution();
    simulateSolution();
  }

  public static Driver create(String name, IDriverConfig dconfig, StaticConfig sconfig, State state) {
    Selector selector = dconfig.strategySelector(sconfig, state);
    Fitness fitness = dconfig.fitnessFunction(sconfig, state);
    return new Driver(name, sconfig, state, selector, fitness);
  }

  public static Driver create(String name, IDriverConfig dconfig, StaticConfig sconfig, State state, int lifetime) {
    Selector selector = dconfig.strategySelector(sconfig, state);
    Fitness fitness = dconfig.fitnessFunction(sconfig, state);
    return new Driver(name, sconfig, state, selector, fitness, lifetime);
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
  public static void main(String[] args) throws IOException {
    if (args.length > 0) {
      LinkedList<File> files = new LinkedList<File>();
      files.add(new File(args[0]));
      
      while (!files.isEmpty()) {
        File file = files.poll();
        if (file.isDirectory())
          for (File other : file.listFiles())
            files.add(other);
        else
          main(file.getName(), FileCommands.readFileAsString(file.getAbsolutePath()));
      }
    } else {
      String text = FileCommands.readAsString(new InputStreamReader(System.in));
      main("stdin", text);
    }
  }
  
  public static void main(String name, String text) {
    Pair<StaticConfig, State> p = State.parse(text);
    StaticConfig sconfig = p.a;
    State state = p.b;
    
    IDriverConfig stdConfig = new SimpleSelectorConfig();

    Driver d = Driver.create(name, stdConfig, sconfig, state, 20);
    d.run();
    d.simulationWindow();    
  }
}
