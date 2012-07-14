package benchmark;

import game.config.IDriverConfig;

/**
 * A superclass for benchmarks.
 *
 * @author Tillmann Rendel
 */
public abstract class Benchmark {
  /**
   * Returns the driver configuration to be used in this benchmark.
   */
  public abstract IDriverConfig config();

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
