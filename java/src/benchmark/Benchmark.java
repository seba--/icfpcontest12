package benchmark;

import game.ai.Fitness;
import game.ai.Selector;

/**
 * A superclass for benchmarks.
 *
 * @author Tillmann Rendel
 */
public abstract class Benchmark {
  /**
   * Returns the fitness function to be used in this benchmark.
   */
  public abstract Fitness fitness();

  /**
   * Returns the strategy selector to be used in this benchmark.
   */
  public abstract Selector selector();

  /**
   * A default main method.
   *
   * <p>
   * A subclass called<code>SpecificBenchmark</code> should include the
   * following code to actually call this main method:
   *
   * <pre>
   * public static void main(String[] args) {
   *   new SpecificBenchmark().main(args);
   * }
   * </pre>
   */
  public void main(String[] args) {

  }
}
